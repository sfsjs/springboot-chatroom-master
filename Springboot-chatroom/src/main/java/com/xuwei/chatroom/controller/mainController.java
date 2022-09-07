package com.xuwei.chatroom.controller;

import com.xuwei.chatroom.entity.Message;
import com.xuwei.chatroom.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @Author xuwei
 * @Date 2020/8/9 21:47
 * @Description 主页面
 */
@Controller
@Slf4j
public class mainController {

    /**
     * 首页跳转
     */
    @GetMapping({"/", "/main"})
    public String main() {
        return "/main";
    }

    /**
     * 获取在线人员列表
     */
    @GetMapping("/online")
    public String online() {
        log.info("获取在线人员列表！");
        return "common::online";
    }

}
