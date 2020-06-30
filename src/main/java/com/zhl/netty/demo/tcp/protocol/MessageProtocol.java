package com.zhl.netty.demo.tcp.protocol;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuhaolin
 * @description 自定义协议包
 * @date 2020/6/29 22:02
 */
@Data
@Builder
public class MessageProtocol implements Serializable {
    private static final long serialVersionUID = -1663437538245127945L;
    /**
     * 数据包长度
     */
    private int length;
    /**
     * 传输的数据内容
     */
    private byte[] content;
}
