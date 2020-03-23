package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int getTotalCount(int cid, String rname) {   //这里cid和rname都是不定的参数，有可能有或没有
        StringBuilder stringBuilder = new StringBuilder();  //拼接sql的字符串
        List<Object> param_list = new ArrayList<>();    //参数列表
        String sql = "select count(*) from tab_route where 1 = 1 ";
        if (cid != 0) {
            stringBuilder.append("and cid = ? ");
            param_list.add(cid);
        }
        if (rname != null && rname.length() != 0) {
            stringBuilder.append("and rname like ? ");
            param_list.add("%" + rname + "%");
        }
        sql += stringBuilder.toString();
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, Integer.class, param_list.toArray());
    }

    @Override
    public List<Route> findRouteByPage(int cid, int start, int pageSize, String rname) {
        //String sql = "select * from tab_route where 1 = 1 and cid = ? and rname like ? limit ? , ?";
        StringBuilder stringBuilder = new StringBuilder();  //拼接sql的字符串
        List<Object> param_list = new ArrayList<>();    //参数列表
        String sql = "select * from tab_route where 1 = 1 ";
        if (cid != 0) {
            stringBuilder.append("and cid = ? ");
            param_list.add(cid);
        }
        if (rname != null && rname.length() != 0) {
            stringBuilder.append("and rname like ? ");
            param_list.add("%" + rname + "%");
        }
        stringBuilder.append("limit ? , ? ");
        param_list.add(start);
        param_list.add(pageSize);
        sql += stringBuilder.toString();
        System.out.println(sql);

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), param_list.toArray());
    }
}
