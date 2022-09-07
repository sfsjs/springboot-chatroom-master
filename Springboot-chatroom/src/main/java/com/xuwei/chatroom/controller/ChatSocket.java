package com.xuwei.chatroom.controller;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.xuwei.chatroom.entity.Message;
import com.xuwei.chatroom.entity.User;
import com.xuwei.chatroom.listen.WebsocketConfig;
import com.xuwei.chatroom.util.JSONChangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author xuwei
 * @Date 2020/9/6 16:32
 * @Description 聊天
 */
@RestController
@ServerEndpoint(value = "/chat", configurator = WebsocketConfig.class)
@Slf4j
public class ChatSocket {
    //定义一个全局变量集合sockets,用户存放每个登录用户的通信管道
    private static final Map<String, ChatSocket> sockets = new ConcurrentHashMap<>();
    //定义一个全局变量Session,用于存放登录用户
    private Session session;
    //存放在线用户
    private static final Map<String, User> userMap = new ConcurrentHashMap<>();
    //存放当前用户
    private User user;

    /**
     * 注解下的方法会在连接建立时运行
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws JsonProcessingException {
        log.info("建立了一个socket通道:{}", session.getId());

        // 整个会话
        user = (User) config.getUserProperties().get("USER");
        //获取httpSession
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        //将当前用户存入在线人员列表
        userMap.put(user.getUsername(), user);
        //在线人员列表
        httpSession.setAttribute("userMap", userMap);
        //在线人数
        httpSession.setAttribute("onlineNum", ObjectUtil.length(userMap));
        log.info("user:{}", user);

        this.session = session;
        //将当前连接上的用户session信息全部存到Sockets中
        sockets.put(user.getUsername(), this);
        sendMessageToAll(JSONChangeUtil.objToJson(new Message("连接", user.getUsername() + "上线了", DateUtil.now())));
    }

    /**
     * 注解下的方法会在前台传来消息时触发
     */
    @OnMessage
    public void onMessage(Session session, String msg) throws JsonProcessingException {
//        log.info(msg);
        Message message = JSONUtil.toBean(msg, Message.class);
        log.info("检验message：{}",message);
        if (message.getTo().equals("所有人")) {
            sendMessageToAll(msg);
        } else {
            sendMessageToUser(msg, message.getTo());
        }

    }

    /**
     * 注解下的方法会在连接关闭时运行
     */
    @OnClose
    public void OnClose(Session session) throws JsonProcessingException {
        //移除退出登录用户的通信管道
        sockets.remove(user.getUsername());
        //将当前用户移除在线列表
        userMap.remove(user.getUsername());
        log.info("{}退出了会话！", user.getUsername());

        sendMessageToAll(JSONChangeUtil.objToJson(new Message("连接", user.getUsername() + "下线了", DateUtil.now())));
    }

    /**
     * 注解下的方法会在通信异常时触发
     */
    @OnError
    public void OnError(Session session, Throwable error) {
        log.info("错误日志：{}", error.getMessage());
    }

    /**
     * 发送消息给所有人
     *
     * @param msg 消息
     */
    private void sendMessageToAll(String msg) {
        sockets.forEach((username, socket) -> {
            try {
                //通过session发送信息
                log.info("发送给用户：{}", username);
                socket.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                log.info("错误日志：{}", e.getMessage());
            }
        });
    }

    /**
     * 发送消息给指定用户
     *
     * @param msg        消息
     * @param toUsername 指定用户
     */
    private void sendMessageToUser(String msg, String toUsername) {
        sockets.forEach((username, socket) -> {
            try {
                if (username.equals(toUsername)||username.equals(user.getUsername())) {
                    //通过session发送信息
                    log.info("发送给用户：{}", toUsername);
                    socket.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                log.info("错误日志：{}", e.getMessage());
            }
        });
    }

}
