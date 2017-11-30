package com.sealinetech.nettytest;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.time.LocalDateTime;

/**
 * Created by Administrator on 2017/7/29.
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    private String tempString;

    public MyClientHandler() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1024 * 1024; i++) {
            builder.append("abcdefghijklmnopqrstuvwxyz");
        }
        tempString = builder.toString();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(LocalDateTime.now().toString() + "----" + ctx.channel().remoteAddress().toString() + "----" + msg.length());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //ctx.writeAndFlush("starting......");
//        for (int i=0; i<1000; i++) {
//            ctx.writeAndFlush(tempString);
//            Thread.sleep(2000);
//        }
        sendData(ctx);
    }

    private void sendData(ChannelHandlerContext ctx) {
        if (!ctx.channel().isActive())
        {
            System.out.println("channel inactive...");
            ctx.close();
            return;
        }

        System.out.println("send a pack of data ...");

        long tickCount = System.currentTimeMillis();
        ChannelFuture future = ctx.writeAndFlush(tempString);
        ChannelPromise promise = (ChannelPromise)future;
        //System.out.println(future.isSuccess());
        promise.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                //sendData(ctx);
                System.out.println("send completed");
                sendData(ctx);
            }
        });
        System.out.println("Time elapse:" + (System.currentTimeMillis() - tickCount));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        //super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //System.out.println(LocalDateTime.now().toString());
        if (evt == IdleStateEvent.READER_IDLE_STATE_EVENT) {
            System.out.println("READER_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.WRITER_IDLE_STATE_EVENT){
            // for heartbit
            System.out.println("WRITER_IDLE_STATE_EVENT----" + LocalDateTime.now().toString());
            //ctx.writeAndFlush("ACK");
        } else if (evt == IdleStateEvent.ALL_IDLE_STATE_EVENT) {
            //System.out.println("ALL_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            System.out.println("FIRST_READER_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {
            //System.out.println("FIRST_WRITER_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT) {
            //System.out.println("FIRST_ALL_IDLE_STATE_EVENT");
        }
        //super.userEventTriggered(ctx, evt);
    }
}
