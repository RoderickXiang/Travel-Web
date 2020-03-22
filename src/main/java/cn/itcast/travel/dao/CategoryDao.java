package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 *
 */
public interface CategoryDao {
    /**
     * 查询导航栏中所有类目
     *
     * @return 项目的列表
     */
    public List<Category> findAll();
}
