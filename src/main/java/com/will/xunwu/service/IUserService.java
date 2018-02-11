package com.will.xunwu.service;

import com.will.xunwu.entity.User;

/**
 * 用户服务
 */
public interface IUserService {

    User findUserByName(String userName);

}
