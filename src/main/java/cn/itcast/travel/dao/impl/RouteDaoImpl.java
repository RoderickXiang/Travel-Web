package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int getTotalCount(int cid) {
        String sql = "select count(*) from tab_route where cid = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cid);
    }

    @Override
    public List<Route> findRouteByPage(int cid, int start, int pageSize) {
        String sql = "select * from tab_route where cid = ? limit ? , ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, start, pageSize);
    }
}
