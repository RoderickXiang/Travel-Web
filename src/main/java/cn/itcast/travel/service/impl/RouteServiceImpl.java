package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> listRouteByPage(int cid, int currentPage, int pageSize, String rname) {
        List<Route> routeByPage_list = routeDao.findRouteByPage(cid, (currentPage - 1) * pageSize, pageSize, rname);
        int totalCount = routeDao.getTotalCount(cid, rname);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        //封装pageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setList(routeByPage_list);
        return pageBean;
    }
}
