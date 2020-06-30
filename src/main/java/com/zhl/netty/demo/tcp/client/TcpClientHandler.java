package com.zhl.netty.demo.tcp.client;

import com.zhl.netty.demo.tcp.protocol.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 22:02
 */
@Slf4j
public class TcpClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        byte[] contentBytes = msg.getContent();
        String content = new String(contentBytes, Charset.forName("utf-8"));
        log.info("服务器：" + content);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
