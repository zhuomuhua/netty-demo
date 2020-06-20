package com.zhl.netty.demo.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author MK519030
 * @Date 2020/6/15 23:21
 */
@Slf4j
public class NettyServer {
    public static void main(String[] args) {
        //bossGroup只是处理连接请求，真正和客户端业务处理，会交给workerGroup处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置2个线程组
            serverBootstrap.group(bossGroup, workerGroup);
            //使用NioServerSocketChannel作为服务器的通道实现
            serverBootstrap.channel(NioServerSocketChannel.class);
            //设置线程队列得到连接的个数
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            //设置保持活动连接状态
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //给我们的workerGroup的EventLoop对应的管道设置处理器
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                //给pipeline 设置处理器
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast("handler", new NettyServerHandler());
                }
            });

            //绑定端口并且同步
            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();

            //添加监听器
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("绑定端口-->成功");
                    } else {
                        log.warn("绑定端口-->失败");
                    }
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
