package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 查询记录总数
     *
     * @return 记录总数
     */
    int getTotalCount(int cid, String rname);

    /**
     * 获取数据集合
     *
     * @param start    开始的条数
     * @param pageSize 查询的条数
     * @param rname    搜索的路线
     * @return 路线对象
     */
    List<Route> findRouteByPage(int cid, int start, int pageSize, String rname);

    /**
     * 查询单个路线的信息
     */
    Route findRouteById(int rid);

    Favorite isFavoriteRoute(int rid, int uid);

    /**
     * 查询线路被收藏的次数
     */
    int routeFavoritedCount(int rid);

    /**
     * 添加收藏路线
     */
    void addFavoriteRoute(int rid, int uid);
}
