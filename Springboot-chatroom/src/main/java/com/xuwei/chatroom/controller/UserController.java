package com.xuwei.chatroom.controller;


import com.xuwei.chatroom.entity.User;
import com.xuwei.chatroom.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuwei
 * @since 2020-08-06
 */
@Controller
@Slf4j
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 登录页跳转
     */
    @GetMapping({"/toLogin", "/login"})
    public String toLogin(HttpSession session) {
        User user = (User) session.getAttribute("USER");
        if (user != null) {
            return "/main";
        }
        return "/login";
    }

    /**
     * 用户登录
     *
     * @param user 用户
     * @return 处理信息
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpSession session) {
        if (user == null) {
            return ResponseEntity.badRequest().body("用户不能为空");
        }
        if (Strings.isBlank(user.getUsername()) || Strings.isBlank(user.getPassword())) {
            return ResponseEntity.badRequest().body("用户名或密码不能为空");
        }
        log.info("用户:{}校验登录", user);

        //根据用户名获取用户
        User byUsername = userService.findByUsername(user.getUsername());

        if (byUsername == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        if (!byUsername.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("用户名或密码错误");
        }
        //清除密码
        byUsername.setPassword("");

        log.info("用户:{}登录", byUsername.getUsername());

        session.setAttribute("USER", byUsername);

        return ResponseEntity.ok("登录成功");
    }

    /**
     * 注册页跳转
     */
    @GetMapping({"/toRegister", "/register"})
    public String toRegister() {
        return "/register";
    }

    /**
     * 用户注册
     *
     * @param user 用户
     * @return 处理信息
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body("用户不能为空");
        }
        if (Strings.isBlank(user.getUsername()) || Strings.isBlank(user.getPassword())) {
            return ResponseEntity.badRequest().body("用户名或密码不能为空");
        }
        log.info("用户：{}校验注册", user.getUsername());

        //保存用户
        boolean save = userService.save(user);
        if (!save) {
            return ResponseEntity.badRequest().body("用户注册失败，请稍后再试");
        }
        log.info("用户注册成功:{}", user.getUsername());
        return ResponseEntity.ok("注册成功");
    }

    /**
     * 跳转到用户信息页面
     */
    @GetMapping("/userInfo")
    public String userInfo(HttpSession session, Model model) {
        User user = (User) session.getAttribute("USER");
        User byId = userService.getById(user.getId());
        byId.setPassword("");
        model.addAttribute("user", byId);
        return "/user_info";
    }

    /**
     * 修改用户信息
     *
     * @param user 用户
     * @return 修改结果
     */
    @PostMapping("/user")
    public ResponseEntity<String> editUserInfo(@RequestBody User user, HttpSession session) {
        if (user == null) {
            return ResponseEntity.badRequest().body("用户不能为空");
        }
        if (Strings.isBlank(user.getUsername())) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        log.info("用户：{}编辑信息校验", user.getUsername());
        boolean update = userService.updateById(user);
        if (!update) {
            return ResponseEntity.badRequest().body("用户名信息修改失败，请稍后再试");
        }
        //获取修改后的用户
        User newUser = userService.getById(user.getId());
        newUser.setPassword("");

        log.info("用户信息修改成功:{}", newUser.getUsername());

        session.setAttribute("user", newUser);

        session.setAttribute("USER", newUser);

        return ResponseEntity.ok("修改成功");
    }

    /**
     * 退出登录
     */
    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        // 获得session对象
        HttpSession session = request.getSession();
        // 销毁session
        session.invalidate();
        return "redirect:/login";
    }
}

