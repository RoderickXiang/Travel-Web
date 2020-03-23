package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
     * 分页展示旅游路线
     *
     * @param cid 类型
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return 分页对象
     */
    PageBean<Route> listRouteByPage(int cid, int currentPage, int pageSize, String rname);
}
