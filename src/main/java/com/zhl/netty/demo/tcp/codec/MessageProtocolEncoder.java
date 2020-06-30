package com.zhl.netty.demo.tcp.codec;

import com.zhl.netty.demo.tcp.protocol.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 22:09
 */
@Slf4j
public class MessageProtocolEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol msg, ByteBuf out) throws Exception {
        log.info("protocol encoder 调用！");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
