package com.xuwei.chatroom.mapper;

import com.xuwei.chatroom.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xuwei
 * @since 2020-08-06
 */
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);
}
