package cn.itcast.travel.web.servlet.userservlet_nouse;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userLoginServlet")
public class UserLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();  //username password
        UserService userService = new UserServiceImpl();
        User loginUser = new User();
        try {
            BeanUtils.populate(loginUser, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        Map<String, String> msg = new HashMap<>();
        User user = userService.login(loginUser);
        if (user != null) {
            if ("Y".equals(user.getStatus())) {
                //登入成功
                msg.put("msg", "登入成功");
                request.getSession().setAttribute("user", user);
            } else {
                //用户未激活
                msg.put("msg", "用户未激活，请使用邮箱激活");
            }
        } else {
            //用户信息错误
            msg.put("msg", "用户名或密码错误");
        }

        //发送结果
        ObjectMapper objectMapper = new ObjectMapper();
        String msg_json = objectMapper.writeValueAsString(msg);
        //System.out.println(msg_json);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(msg_json);    //json数据回传
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
