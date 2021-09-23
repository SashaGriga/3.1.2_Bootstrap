package com.example.crudsecurityboot.dao;

import com.example.crudsecurityboot.model.Role;
import com.example.crudsecurityboot.model.User;

import java.util.List;

public interface UserDao {

    User getByName(String name);

    Role getByRole(String name);

    List<Role> getAllRoles();

}
