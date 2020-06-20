package com.zhl.netty.demo.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/18 23:26
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 建立连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    /**
     * 处于活跃状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(ctx.channel().remoteAddress() + "上线了\n");
    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        channels.writeAndFlush(ctx.channel().remoteAddress() + "下线了\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause.getCause());
        channels.remove(ctx.channel());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        channels.writeAndFlush(ctx.channel().remoteAddress() + ": " + msg + "\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!ObjectUtils.isEmpty(evt) && evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventDescription;
            switch (event.state()) {
                case READER_IDLE:
                    eventDescription = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventDescription = "写空闲";
                    break;
                case ALL_IDLE:
                    eventDescription = "读写空闲";
                default:
                    eventDescription = "";
                    break;
            }
            log.info(ctx.channel().remoteAddress() + eventDescription);
            //服务做相应的处理
            ctx.channel().close();
        }
    }
}
