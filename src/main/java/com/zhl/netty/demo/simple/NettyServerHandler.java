package com.zhl.netty.demo.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/15 23:39
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据
     * channelHandlerContext：上下文对象
     * 包含 管道pipeline 通道channel 地址
     * Object msg：客户端发送的数据 默认Object
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //一、异步执行
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                //处理业务
            }
        });

        //二、异步执行(定时任务)
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 10L, TimeUnit.SECONDS);


        ByteBuf buf = (ByteBuf) msg;
        System.out.println(ctx.channel().remoteAddress() + "：" + buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完成
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello!", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
