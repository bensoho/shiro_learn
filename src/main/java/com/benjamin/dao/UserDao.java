package com.benjamin.dao;

import com.benjamin.pojo.User;

import java.util.List;

public interface UserDao {
    User getUserByUserName(String userName);


    List<String> queryRolesByUserName(String userName);

    List<String> queryPermissionsByUserName(String userName);
}
