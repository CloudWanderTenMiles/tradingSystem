package com.frye.trading.ws;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frye.trading.pojo.dto.Message;
import com.frye.trading.pojo.model.Admin;
import com.frye.trading.pojo.model.Customer;
import com.frye.trading.utils.MessageUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {

    /**
     * 用来存储每一个客户端对象对应的ChatEndpoint对象
     */
    private static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    // 声明session对象，发送消息
    private Session session;
    // 声明一个String对象，用以存储name
    private String name;
    // 存储登陆的httpsession
    private HttpSession httpSession;


    /**
     * 链接建立时响应
     *
     * @param session        会话
     * @param endpointConfig
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // 赋值session
        this.session = session;
        this.httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());

        String type = (String) httpSession.getAttribute("type");
        if (type.equals("customer")) {
            Customer customer = (Customer) httpSession.getAttribute("customer");
            this.name = customer.getCustomerName();
        } else {
            Admin admin = (Admin) httpSession.getAttribute("admin");
            this.name = admin.getAdminName();
        }
        onlineUsers.put(name, this);
        System.out.println(name + "已上线");
    }

    /**
     * 接收客户端消息时响应
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            // 将message json字符串转换为message对象
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message,Message.class);
            String toUser = mess.getToUser();
            System.out.println(mess);
            String data = (String) mess.getMessage();
            String resultMessage = MessageUtils.getMessage(false, this.name, data);
            // 发送对象
            onlineUsers.get(toUser).session.getBasicRemote().sendText(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 链接关闭时响应
     *
     * @param session 会话
     */
    @OnClose
    public void onClose(Session session) {
        if (name != null) {
            onlineUsers.remove(name);
        }
    }

    /**
     * 广播消息
     *
     * @param message 消息
     */
    private void broadcast(String message) {
        try {
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                ChatEndpoint chatEndpoint = onlineUsers.get(name);
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
