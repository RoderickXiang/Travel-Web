package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
     * 分页展示旅游路线
     *
     * @param cid 类型
     * @return 分页对象
     */
    PageBean<Route> listRouteByPage(int cid, int currentPage, int pageSize, String rname);

    /**
     * 获取路线的详情
     *
     * @param rid 路线的id routeId
     * @return 路线对象
     */
    Route getRouteDetails(int rid);


    /**
     * 查询路线是否被收藏
     *
     * @param rid 路线的id routeId
     * @param uid 用户的id userId
     */
    boolean isFavoriteRoute(int rid, int uid);


    /**
     * 线路被收藏的次数
     */
    int routeFavoritedCount(int rid);
}
