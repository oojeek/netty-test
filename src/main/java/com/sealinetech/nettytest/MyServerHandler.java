package com.sealinetech.nettytest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/28.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    private String tempString;

    public MyServerHandler() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1024 * 1024; i++) {
            builder.append("abcdefghijklmnopqrstuvwxyz");
        }
        tempString = builder.toString();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(LocalDateTime.now().toString() + "----" + ctx.channel().remoteAddress() + "----" + msg.length());
        //ctx.channel().writeAndFlush("服务器说：" + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //System.out.println(LocalDateTime.now().toString());
        if (evt == IdleStateEvent.READER_IDLE_STATE_EVENT) {
            System.out.println("READER_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.WRITER_IDLE_STATE_EVENT){
            System.out.println("WRITER_IDLE_STATE_EVENT----" + LocalDateTime.now().toString());
            ctx.writeAndFlush(tempString);
        } else if (evt == IdleStateEvent.ALL_IDLE_STATE_EVENT) {
            //System.out.println("ALL_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            System.out.println("FIRST_READER_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {
            //System.out.println("FIRST_WRITER_IDLE_STATE_EVENT");
        } else if (evt == IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT) {
            //System.out.println("FIRST_ALL_IDLE_STATE_EVENT");
        }
        super.userEventTriggered(ctx, evt);
    }
}
