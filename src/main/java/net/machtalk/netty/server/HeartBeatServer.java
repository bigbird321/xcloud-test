package net.machtalk.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import net.machtalk.netty.server.handler.AcceptorIdleStateTrigger;
import net.machtalk.netty.server.handler.HeartBeatServerHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop on 2018/4/9
 */
public class HeartBeatServer {
    private final AcceptorIdleStateTrigger idleStateTrigger = new AcceptorIdleStateTrigger();

    private int port;

    public HeartBeatServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        /**
         * NioEventLoopGroup是一个Reactor线程组，包含一组NIO线程，专门用于网络事件的处理
         */
        //mainReactor 一个主线程，接收连接，转发给worker处理
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //subReactor 默认CPU *2 线程执行器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ServerChannelInitializer())
                    //服务端将不能处理的客户端连接请求放在队列中等待处理，backlog队列大小
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //禁用Nagle算法，小数据即时传输
                    .option(ChannelOption.TCP_NODELAY, true)
                    //发送缓冲区大小
                    .option(ChannelOption.SO_SNDBUF, 1024*64)
                    //接收缓冲区大小
                    .option(ChannelOption.SO_RCVBUF, 1024*64)
                    //测试链接的状态，两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定端口，开始接收进来的连接
            ChannelFuture future = sbs.bind(port).sync();

            System.out.println("Server start listen at " + port);
            //获取Channel的CloseFuture，等待服务端链路关闭之后main函数再退出
            future.channel().closeFuture().sync();
        } finally{
            //优雅的退出，释放线程资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ServerChannelInitializer extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
            ch.pipeline().addLast(idleStateTrigger);
            ch.pipeline().addLast("decoder", new StringDecoder());
            ch.pipeline().addLast("encoder", new StringEncoder());
            ch.pipeline().addLast(new HeartBeatServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new HeartBeatServer(port).start();
    }

}
