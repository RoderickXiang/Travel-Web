package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean registerUser(User newUser) {
        User user = userDao.findUserByUsername(newUser.getUsername());
        if (user != null) {
            //用户已存在
            return false;
        } else {
            //设置激活码并发送邮件
            newUser.setCode(UuidUtil.getUuid());    //随机生成验证码
            newUser.setStatus("N");
            String content = "<a href='http://localhost/travel/activeUserServlet?code=" + newUser.getCode() + "'>激活旅游网站</a>";
            MailUtils.sendMail(newUser.getEmail(), content, "激活邮件");
            userDao.appendUser(newUser);
            return true;
        }
    }

    @Override
    public boolean activeUser(String activeCode) {
        User user = userDao.findUserByCode(activeCode);
        if (user != null) {
            userDao.activeUser(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User loginUser) {
        return userDao.findUserByUsernameAndPassword(loginUser);
    }
}
