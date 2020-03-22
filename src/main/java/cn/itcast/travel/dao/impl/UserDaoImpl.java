package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDaoImpl implements UserDao {
    private DataSource dataSource = JDBCUtils.getDataSource();
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException ignored) {
        }
        return user;
    }

    @Override
    public void appendUser(User newUser) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) value (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                newUser.getUsername(),
                newUser.getPassword(),
                newUser.getName(),
                newUser.getBirthday(),
                newUser.getSex(),
                newUser.getTelephone(),
                newUser.getEmail(),
                newUser.getStatus(),
                newUser.getCode());
    }

    @Override
    public User findUserByCode(String activeCode) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), activeCode);
        } catch (DataAccessException ignored) {
        }
        return user;
    }

    @Override
    public void activeUser(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        jdbcTemplate.update(sql, user.getUid());
    }

    @Override
    public User findUserByUsernameAndPassword(User loginUser) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), loginUser.getUsername(), loginUser.getPassword());
        } catch (DataAccessException ignored) {
        }
        return user;
    }
}
