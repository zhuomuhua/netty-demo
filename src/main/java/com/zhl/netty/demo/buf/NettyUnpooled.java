package com.zhl.netty.demo.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/18 22:25
 */
public class NettyUnpooled {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);
        System.out.println(buf.capacity());
        System.out.println(buf.maxCapacity());
        byte[] bytes = "朱浩霖灼目华".getBytes();
        buf.writeBytes(bytes);
        System.out.println(buf.capacity());
        System.out.println(buf.maxCapacity());

        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        //initCapacity 256  maxCapacity  Integer.MAX_VALUE
        ByteBuf buffer = allocator.buffer();
    }
}
