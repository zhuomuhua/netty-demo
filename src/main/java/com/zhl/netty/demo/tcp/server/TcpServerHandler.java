package com.zhl.netty.demo.tcp.server;

import com.zhl.netty.demo.tcp.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 21:54
 */
@Slf4j
public class TcpServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        byte[] messageBytes = msg.getContent();
        String message = new String(messageBytes, Charset.forName("utf-8"));
        String ip = ctx.channel().remoteAddress().toString();
        log.info(ip + ": " + message);

        String replyContent = "收到";
        byte[] contentBytes = replyContent.getBytes("utf-8");
        MessageProtocol protocol = MessageProtocol.builder().length(contentBytes.length).content(contentBytes).build();
        ctx.channel().writeAndFlush(protocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
