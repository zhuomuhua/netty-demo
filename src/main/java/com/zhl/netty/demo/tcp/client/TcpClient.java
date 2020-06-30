package com.zhl.netty.demo.tcp.client;

import com.zhl.netty.demo.tcp.protocol.MessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author zhuhaolin
 * @description
 * @date 2020/6/30 21:55
 */
@Slf4j
public class TcpClient {
    private String host;
    private int port;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new TcpClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            Scanner scan = new Scanner(System.in);
            while (scan.hasNextLine()) {
                String msg = scan.nextLine();
                //通过channel发送到服务器
                byte[] content = msg.getBytes(Charset.forName("utf-8"));
                int length = content.length;
                MessageProtocol message = MessageProtocol.builder()
                        .length(length)
                        .content(content)
                        .build();
                channel.writeAndFlush(message);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        new TcpClient("127.0.0.1", 7000).run();
    }
}
