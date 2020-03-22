package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 使用redis进行缓存操作
     */
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        List<Category> categoriesList = new ArrayList<>();
        Set<Tuple> categories_redis = jedis.zrangeWithScores("categories", 0, -1);
        if (categories_redis != null && categories_redis.size() != 0) {
            //缓存中有数据
            System.out.println("使用缓存");
            for (Tuple cname : categories_redis) {
                Category newCategory = new Category();
                newCategory.setCname(cname.getElement());
                newCategory.setCid((int) cname.getScore());
                categoriesList.add(newCategory);
            }
        } else {
            //缓存中没有数据
            System.out.println("使用数据库");
            categoriesList = categoryDao.findAll();
            //遍历来自数据库的列表
            for (Category category : categoriesList) {
                jedis.zadd("categories", category.getCid(), category.getCname());
            }
        }
        return categoriesList;
    }
}
