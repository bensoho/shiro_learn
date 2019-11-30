package com.benjamin.dao.impl;

import com.benjamin.dao.UserDao;
import com.benjamin.pojo.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public User getUserByUserName(String userName) {
//        User user = new User();
//        user.setUsername(userName);
//        user.setPassword("3285541c519ec7cef7077b06baae58d5");
        String sql="select username,password from users where username = ?";
        List<User>list = jdbcTemplate.query(sql, new String[]{userName},new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));

                return user;
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> queryRolesByUserName(String userName) {
        String sql = "select rolename from user_roles where username = ?";

        return jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("rolename");
            }
        });
    }

    @Override
    public List<String> queryPermissionsByUserName(String userName) {

        String sql = "select permission from roles_permissions rp " +
                "left join user_roles ur on rp.role_name=ur.rolename where ur.username = ?";



        return jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");
            }
        });
    }
}
