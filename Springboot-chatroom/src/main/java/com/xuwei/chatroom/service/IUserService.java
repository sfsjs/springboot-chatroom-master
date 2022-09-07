package com.xuwei.chatroom.service;

import com.xuwei.chatroom.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuwei
 * @since 2020-08-06
 */
public interface IUserService extends IService<User> {
    User findByUsername(String username);
}
