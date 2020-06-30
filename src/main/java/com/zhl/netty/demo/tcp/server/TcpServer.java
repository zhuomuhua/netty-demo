package com.zhl.netty.demo.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 21:41
 */
@Slf4j
public class TcpServer {
    private int port;

    public TcpServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
            bootstrap.childHandler(new TcpServerInitializer());
            ChannelFuture future = bootstrap.bind(port).sync();
            future.addListener(channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("服务器绑定端口成功！");
                } else {
                    log.warn("服务器绑定端口失败！");
                }
            });
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        //执行Tcp服务器
        new TcpServer(7000).run();
    }
}
