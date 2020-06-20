package com.zhl.netty.demo.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/17 22:34
 */
@Slf4j
public class NettyHttpServer {
    public static void main(String[] args) {
        int port = 9999;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(new NettyHttpServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    boolean success = channelFuture.isSuccess();
                    if (success) {
                        log.info("绑定端口-->成功");
                    } else {
                        log.warn("绑定端口-->失败");
                    }
                }
            });

            channelFuture.channel().closeFuture();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
