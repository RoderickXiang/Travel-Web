package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")  // user/方法名
public class UserServlet extends BaseServlet {
    /**
     * 用户注册
     */
    public void registerUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.removeAttribute("CHECKCODE_SERVER");    //防止验证码二次使用
        Map<String, String[]> userMap = request.getParameterMap();
        ObjectMapper mapper = new ObjectMapper();   //封装json
        ResultInfo resultInfo = new ResultInfo();   //返回的信息对象

        if (checkcode_server != null && checkcode_server.equalsIgnoreCase(userMap.get("check")[0])) {
            //验证码通过
            UserService userService = new UserServiceImpl();
            User newUser = new User();
            try {
                BeanUtils.populate(newUser, userMap);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            boolean flag = userService.registerUser(newUser);
            if (flag) {
                resultInfo.setFlag(true);
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败！");
            }
        } else {
            //验证码未通过
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
        }


        //返回消息
        String json = mapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 用户通过邮箱激活
     */
    public void activeUserServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String activeCode = request.getParameter("code");
        if (activeCode != null && activeCode.length() != 0) {
            UserService userService = new UserServiceImpl();
            boolean result = userService.activeUser(activeCode);
            response.setContentType("text/html;charset=utf-8");
            if (result) {
                //成功
                response.getWriter().write("激活成功，请<a href='login.html'>登入</a>");
                System.out.println("激活成功");
            } else {
                //失败
                response.getWriter().write("激活失败");
                System.out.println("激活失败");
            }
        }
    }

    /**
     * 用户登入
     */
    public void userLoginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                request.getSession().setAttribute("user", user);    //设置session
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

    /**
     * 在头部信息中回显现在已经登入的用户
     */
    public void findUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        User user = (User) request.getSession().getAttribute("user");

        ObjectMapper objectMapper = new ObjectMapper();
        String user_json = objectMapper.writeValueAsString(user);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(user_json);
    }

    /**
     * 用户退出操作
     */
    public void exitServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();  //销毁session
        //重定向（页面再次加载）
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}
