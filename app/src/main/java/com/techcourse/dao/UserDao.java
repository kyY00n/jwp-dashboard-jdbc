package com.techcourse.dao;

import com.techcourse.config.DataSourceConfig;
import com.techcourse.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private final JdbcTemplate jdbcTemplate;


    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate(DataSourceConfig.getInstance());
    }

    public void insert(final User user) {
        final var sql = "insert into users (account, password, email) values (?, ?, ?)";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getEmail());
    }

    public void update(final User user) {
        final var sql = "update users set password = ?, email = ?, account = ? where id = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getEmail(), user.getAccount(), user.getId());
    }

    public List<User> findAll() {
        final var sql = "select id, account, password, email from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(rs.getLong("id"),
                rs.getString("account"),
                rs.getString("password"),
                rs.getString("email")));
    }

    public User findById(final Long id) {
        final var sql = "select id, account, password, email from users where id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(rs.getLong("id"),
                rs.getString("account"),
                rs.getString("password"),
                rs.getString("email")), id);
    }

    public User findByAccount(final String account) {
        final var sql = "select id, account, password, email from users where account = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(rs.getLong("id"),
                rs.getString("account"),
                rs.getString("password"),
                rs.getString("email")), account);
    }
}
