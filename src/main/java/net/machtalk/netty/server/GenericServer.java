package net.machtalk.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.machtalk.netty.server.handler.ServerReadHandler;
import net.machtalk.netty.server.handler.ServerWriteHandler;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class GenericServer {

    private final int port;

    public GenericServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + GenericServer.class.getSimpleName() +
                            " ");
        }
        //设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
        int port = Integer.parseInt(args[0]);
        //调用服务器的start()方法
        new GenericServer(port).start();
    }

    public void start() throws Exception {
        final ServerReadHandler serverReadHandler = new ServerReadHandler();
        final ServerWriteHandler serverWriteHandler = new ServerWriteHandler();
        //创建Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class) //指定所使用的NIO传输Channel
                    .localAddress(new InetSocketAddress(port)) //使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer() {
                        //添加一个ServerReadHandler到子Channel的ChannelPipeline
                        @Override
                        public void initChannel(Channel ch)
                                throws Exception {
                            // ServerReadHandler被标注为@Shareable，所以我们可以总是使用同样的实例
                            // ChannelOutboundHandler，逆序执行
                            ch.pipeline().addLast(serverWriteHandler);
                            // ChannelIntboundHandler，按照顺序执行
                            ch.pipeline().addLast(serverReadHandler);
                        }
                    });
            //异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            //获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            //关闭EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

}
