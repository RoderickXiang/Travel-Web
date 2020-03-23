package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService routeService = new RouteServiceImpl();

    public void listRouteByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //类型转换
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        String cid_str = request.getParameter("cid");   //cid的值一定要有
        String rname = request.getParameter("rname");   //查找的线路名字

        int currentPage = 0;
        if (currentPage_str != null && currentPage_str.length() > 0) {  //判断长度
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSize_str != null && pageSize_str.length() > 0) {
            pageSize = Integer.parseInt(pageSize_str);
        } else {
            pageSize = 5;
        }

        int cid = 0;
        if (cid_str != null && cid_str.length() > 0 && !"null".equals(cid_str)) {
            cid = Integer.parseInt(cid_str);
        }

        if (rname == null || "null".equals(rname)) {
            rname = null;
        }

        //处理数据
        PageBean<Route> routePageBean = routeService.listRouteByPage(cid, currentPage, pageSize, rname);
        //返回json
        String routePageBean_json = this.writeValueAsString(routePageBean);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(routePageBean_json);
    }
}
