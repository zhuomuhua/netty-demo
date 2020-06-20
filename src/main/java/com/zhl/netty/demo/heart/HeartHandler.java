package com.zhl.netty.demo.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/20 15:23
 */
@Slf4j
public class HeartHandler extends ChannelInboundHandlerAdapter {
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
        }
    }
}
