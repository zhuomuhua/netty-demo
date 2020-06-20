package com.zhl.netty.demo.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/18 22:25
 */
public class NettyUnpooled {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);
        buf.capacity();
    }
}
