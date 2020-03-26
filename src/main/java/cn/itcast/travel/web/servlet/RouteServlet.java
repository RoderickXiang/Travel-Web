package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService routeService = new RouteServiceImpl();

    /**
     * 按类别分页展示路线数据
     */
    public void listRouteByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //类型转换
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        String cid_str = request.getParameter("cid");
        String rname = request.getParameter("rname");   //查找的线路名字

        int currentPage = 0;
        if (currentPage_str != null && currentPage_str.length() > 0) {  //判断长度
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            currentPage = 1;
        }

        int pageSize;
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


    /**
     * 获取选中的路线详情
     */
    public void getRouteDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String rid_str = request.getParameter("rid");   //rid不得为null

        int rid = 0;
        if (rid_str != null && !"null".equals(rid_str)) {
            rid = Integer.parseInt(rid_str);
        }

        Route route = routeService.getRouteDetails(rid);    //添加被收藏的次数
        //返回json
        response.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(response.getOutputStream(), route);
    }


    /**
     * 判断路线是否被特定用户收藏
     */
    public void isFavoriteRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String rid_str = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        int rid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        if (rid_str != null && !"null".equals(rid_str)) {
            rid = Integer.parseInt(rid_str);
        } else {
            rid = 0;
        }
        boolean flag = routeService.isFavoriteRoute(rid, uid);

        //回写数据
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), flag);
    }

    /**
     * 用户添加收藏的线路
     */
    public void addFavoriteRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String rid_str = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            //用户已登入
            if (rid_str != null && !"null".equals(rid_str)) {
                int rid = Integer.parseInt(rid_str);
                routeService.addFavoriteRoute(rid, user.getUid());
            }
        } else {
            //用户未登录
            response.sendRedirect(request.getContextPath() + "login.html");
        }
    }


    /**
     * 获取收藏排行榜
     */
    public void getFavoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        List<Route> routeList = routeService.getFavoriteRank();
        response.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(response.getOutputStream(), routeList);
    }
}