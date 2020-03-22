package cn.itcast.travel.web.servlet.userservlet_nouse;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
