package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findUserByUsername(String username);

    /**
     * 添加新的用户（注册）
     *
     * @param newUser 新用户
     */
    void appendUser(User newUser);

    /**
     * 根据激活码查找用户
     */
    User findUserByCode(String activeCode);

    /**
     * 更改用户激活状态
     */
    void activeUser(User user);

    /**
     * 用于登入操作
     *
     * @param loginUser 输入的用户
     * @return 数据库中有的用户
     */
    User findUserByUsernameAndPassword(User loginUser);
}
