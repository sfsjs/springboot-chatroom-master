package com.xuwei.chatroom.listen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author xuwei
 * @Date 2020/9/6 20:22
 * @Description 用ServletRequest将HttpSession携带过去
 */
@Component //将监听器纳入到Spring容器中进行管理,相当于注册监听
@Slf4j
public class RequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();
//        log.info("将所有request请求都携带上httpSession {}",session.getId());
    }

    public RequestListener() {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequestListener.super.requestDestroyed(sre);
    }
}
