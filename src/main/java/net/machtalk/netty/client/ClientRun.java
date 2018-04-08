package net.machtalk.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.machtalk.netty.client.handler.ClientHandler;

import java.net.InetSocketAddress;

public class ClientRun {
    private static final String host = "127.0.0.1";
    private static final int port = 5000;

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建Bootstrap,指定EventLoopGroup以处理客户端事件；需要适用于NIO的实现
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)  //适用于NIO传输的Channel类型
                    .remoteAddress(new InetSocketAddress(host, port)) //设置服务器的InetSocketAddr-ess
                    .handler(new ChannelInitializer<Channel>() {  //在创建Channel时，向ChannelPipeline中添加一个ClientHandler实例
                        @Override
                        public void initChannel(Channel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new ClientHandler());
                        }
                    });
            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            //阻塞，直到Channel关闭
            f.channel().closeFuture().sync();
        } finally {
            //关闭线程池并且释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new ClientRun().start();
    }
}
