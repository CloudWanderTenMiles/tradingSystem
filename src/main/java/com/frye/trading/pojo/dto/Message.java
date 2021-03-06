package com.frye.trading.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * websocket在线聊天室  客户端---> 服务端 消息
 * author@frye
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String toUser;
    private Object message;
}
