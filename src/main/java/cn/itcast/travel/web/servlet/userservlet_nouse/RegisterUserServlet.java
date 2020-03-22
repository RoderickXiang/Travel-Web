package cn.itcast.travel.web.servlet.userservlet_nouse;

import cn.itcast.travel.domain.ResultInfo;
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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
