package com.xuwei.chatroom.service.impl;

import com.xuwei.chatroom.entity.User;
import com.xuwei.chatroom.mapper.UserMapper;
import com.xuwei.chatroom.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuwei
 * @since 2020-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User findByUsername(String username) {
        return this.baseMapper.findByUsername(username);
    }
}
