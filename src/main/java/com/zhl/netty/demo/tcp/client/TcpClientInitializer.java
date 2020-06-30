package com.zhl.netty.demo.tcp.client;

import com.zhl.netty.demo.tcp.codec.MessageProtocolDecoder;
import com.zhl.netty.demo.tcp.codec.MessageProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 21:58
 */
@Slf4j
public class TcpClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加编码器
        pipeline.addLast(new MessageProtocolEncoder());
        //添加解码器
        pipeline.addLast(new MessageProtocolDecoder());
        //添加业务channelHandler
        pipeline.addLast(new TcpClientHandler());
    }
}