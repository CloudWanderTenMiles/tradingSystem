package com.frye.trading.ws;

import org.springframework.context.annotation.Configuration;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
@Configuration
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    //此方法用来获取httpssion对象
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if(httpSession != null) {
            sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
        }
    }
}
