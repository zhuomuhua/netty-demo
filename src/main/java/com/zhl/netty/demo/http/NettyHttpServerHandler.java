package com.zhl.netty.demo.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 1、SimpleChannelInBoundHandler 是 ChannelInboundHandlerAdapter子类
 * 2、HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 *
 * @Description
 * @Author MK519030
 * @Date 2020/6/17 22:34
 */
@Slf4j
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        ctx.channel();
        ctx.pipeline();
        ctx.pipeline().channel();
        ctx.handler();
        if (msg instanceof HttpRequest) {
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是小爱!".getBytes());
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
