package net.machtalk.netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

@ChannelHandler.Sharable
public class ServerWriteHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress,
                     ChannelPromise promise) throws Exception {
        super.bind(ctx, localAddress, promise);
        System.out.println("bind:" + localAddress);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                        SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
        System.out.println("connect:remoteAddress=" + remoteAddress + " localAddress=" + localAddress);

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //String id = new ChannelWrapper(ctx.channel()).getId();
        System.out.println("send:[" + msg + "] to:" + (ctx.channel()));
        super.write(ctx, msg, promise);
    }
    

}
