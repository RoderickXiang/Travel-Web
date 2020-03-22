package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 查询记录总数
     *
     * @return 记录总数
     */
    int getTotalCount(int cid);

    /**
     * 获取数据集合
     *
     * @param start    开始的条数
     * @param pageSize 查询的条数
     * @return 路线对象
     */
    List<Route> findRouteByPage(int cid, int start, int pageSize);
}
