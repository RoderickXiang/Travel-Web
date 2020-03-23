package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {  //有service方法
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        //travel/user/方法名
        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);  //方法名

        //反射
        try {//谁调用谁就是this
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class); //获取方法对象
            method.invoke(this, req, resp);     //通过反射执行方法
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装Json对象
     *
     * @param object 需要被封装的对象
     * @return json字符串
     */
    public String writeValueAsString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
