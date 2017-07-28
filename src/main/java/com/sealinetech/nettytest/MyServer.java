package com.sealinetech.nettytest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Administrator on 2017/7/28.
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class).childHandler(new MyServerInitializer());

        ChannelFuture channelFuture = bootstrap.bind(8899).sync();
        channelFuture.channel().closeFuture().sync();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
