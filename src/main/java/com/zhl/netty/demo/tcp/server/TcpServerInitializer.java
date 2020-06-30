package com.zhl.netty.demo.tcp.server;

import com.zhl.netty.demo.tcp.codec.MessageProtocolDecoder;
import com.zhl.netty.demo.tcp.codec.MessageProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 21:49
 */
public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加编码器
        pipeline.addLast(new MessageProtocolEncoder());
        //添加解码器
        pipeline.addLast(new MessageProtocolDecoder());
        //添加业务channelHandler
        pipeline.addLast(new TcpServerHandler());
    }
}
