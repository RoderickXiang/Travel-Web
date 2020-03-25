package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();

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

    @Override
    public Route getRouteDetails(int rid) {
        //添加Route对象的属性 图片列表 商家 被收藏的次数
        Route route = routeDao.findRouteById(rid);
        route.setRouteImgList(routeImageDao.getRouteImageList(rid));    //设置图片
        route.setSeller(sellerDao.getSellerById(route.getSid()));   //设置商家
        route.setCount(routeDao.routeFavoritedCount(rid));  //设置被收藏的次数
        return route;
    }

    @Override
    public boolean isFavoriteRoute(int rid, int uid) {
        Favorite favoriteRoute = routeDao.isFavoriteRoute(rid, uid);
        return favoriteRoute != null;
    }

    @Override
    public int routeFavoritedCount(int rid) {
        return routeDao.routeFavoritedCount(rid);
    }

    @Override
    public void addFavoriteRoute(int rid, int uid) {
        routeDao.addFavoriteRoute(rid, uid);
    }
}
