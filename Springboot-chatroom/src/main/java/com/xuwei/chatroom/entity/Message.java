package com.xuwei.chatroom.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Data;

/**
 * @Author xuwei
 * @Date 2020/8/11 20:49
 * @Description
 */
@Data
public class Message {
    private String type;
    private String from;
    private String face;
    private String to;
    private String color;
    private String content;
    private String sendTime;

    public Message(String type,String content, String sendTime) {
        this.type = type;
        this.content = content;
        this.sendTime = sendTime;
    }
}
