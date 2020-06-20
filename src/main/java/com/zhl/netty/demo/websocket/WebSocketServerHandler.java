package com.zhl.netty.demo.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/20 20:32
 */
@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到消息：", msg.text());
        String content = "服务器时间：" + LocalDateTime.now();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(content));
    }

    /**
     * Web客户端连接，触发方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ChannelId channelId = ctx.channel().id();
        log.info("Web客户端连接成功：", channelId.asLongText());
    }

    /**
     * Web客户端断开，触发方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ChannelId channelId = ctx.channel().id();
        log.info("Web客户端断开：", channelId.asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
