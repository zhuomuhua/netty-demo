package com.zhl.netty.demo.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/18 23:24
 */
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("encode", new StringEncoder());
        pipeline.addLast("decode", new StringDecoder());
        pipeline.addLast(new IdleStateHandler(10, 15, 20, TimeUnit.SECONDS));
        pipeline.addLast("handler", new ChatServerHandler());
    }
}
