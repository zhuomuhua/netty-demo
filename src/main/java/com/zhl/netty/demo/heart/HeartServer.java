package com.zhl.netty.demo.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/20 14:57
 */
@Slf4j
public class HeartServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    /*
                        说明：
                            1、IdleStateHandler 是netty 提供的处理空闲状态的处理器
                            2、readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                            3、writerIdleTime：表示多长时间没有写，就会发送一个心跳检测包是否连接
                            4、allIdleTime：表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
                            5、当IdleStateEvent触发后，就会传递给 管道 的下一个handler去处理
                            通过调用(触发)下一个handler的 userEventTriggered，在该方法中去处理
                            IdleStateEvent(读空闲、写空闲、读写空闲)
                     */
                    pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                    pipeline.addLast(new HeartHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(9000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
