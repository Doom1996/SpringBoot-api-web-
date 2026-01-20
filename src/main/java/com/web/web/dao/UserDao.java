package com.web.web.dao;
import com.web.web.models.User;

import java.util.List;

//debemos aplicar un patron de diseño DAO
public interface UserDao {//funciones que deberia tener esta clase
    List<User> getUsers();
    void deleteUser(Long id);
    void registerUser(User user);
}
