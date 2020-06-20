package com.zhl.netty.demo.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/17 22:35
 */
@Slf4j
public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //netty提供的处理http的编解码器
        pipeline.addLast("codec", new HttpServerCodec());
        pipeline.addLast("handler", new NettyHttpServerHandler());
    }
}
