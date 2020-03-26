package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    boolean registerUser(User newUser);

    /**
     * 激活用户
     *
     * @param activeCode 传入的激活码
     */
    boolean activeUser(String activeCode);

    /**
     * 用户登入
     *
     * @param loginUser 输入的用户
     * @return 数据库中有的用户
     */
    User login(User loginUser);
}
