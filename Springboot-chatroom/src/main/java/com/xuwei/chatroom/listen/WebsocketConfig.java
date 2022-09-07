package com.xuwei.chatroom.listen;

import com.xuwei.chatroom.entity.User;
import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * @Author xuwei
 * @Date 2020/9/6 20:12
 * @Description 获取HttpSession
 */
@Configuration
public class WebsocketConfig extends Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
                                HandshakeRequest request, HandshakeResponse response) {
        /*如果没有监听器,那么这里获取到的HttpSession是null*/
        StandardSessionFacade ssf = (StandardSessionFacade) request.getHttpSession();
        if (ssf != null) {
            HttpSession session = (HttpSession) request.getHttpSession();
            sec.getUserProperties().put("USER", session.getAttribute("USER"));
            sec.getUserProperties().put(HttpSession.class.getName(), session);
        }
        super.modifyHandshake(sec, request, response);
    }

}
