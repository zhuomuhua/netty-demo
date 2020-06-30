package com.zhl.netty.demo.tcp.codec;

import com.zhl.netty.demo.tcp.protocol.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 22:09
 */
@Slf4j
public class MessageProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf out, List<Object> list) throws Exception {
        log.info("protocol decoder 调用！");
        int length = out.readInt();
        byte[] content = new byte[length];
        out.readBytes(content);
        MessageProtocol message = MessageProtocol.builder().length(length).content(content).build();
        list.add(message);
    }
}
