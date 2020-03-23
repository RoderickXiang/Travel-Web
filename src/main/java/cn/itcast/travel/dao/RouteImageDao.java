package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImageDao {
    /**
     * 获取路线的图片
     * @param rid 线路的id routeId
     * @return 路线图片对象列表
     */
    List<RouteImg> getRouteImageList(int rid);
}
