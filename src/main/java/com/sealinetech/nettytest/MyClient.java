package com.sealinetech.nettytest;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.BootstrapConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by Administrator on 2017/7/28.
 */
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new MyClientInitializer());
//        BootstrapConfig config = bootstrap.config();
//        config.options().put(ChannelOption.SO_SNDBUF, 1024);
        SocketAddress address = new InetSocketAddress("118.89.229.31", 8899);
        //SocketAddress address = new InetSocketAddress("localhost", 8899);

        ChannelFuture future = bootstrap.connect(address);
        //future.channel().closeFuture().sync();
        //group.shutdownGracefully();
    }
}
