package com.web.web.dao;

import com.web.web.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository//accede a la base de datos
@Transactional//trata las consultas como transacciones en SQL
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager entityManager;//se usa para acceder a la base de datos

    @Override
    public List<User> getUsers() {
        String query = "FROM User";//Se ingresa nombre de la clase
        return entityManager
                .createQuery(query, User.class)//createNativeQuery("SELECT * FROM usuarios", User.class)
                .getResultList();//devuelve una lista sin tipos raw,tipamos con User.class
    }

    @Override//codigo backend
    public void deleteUser(Long id) {
        User usuario = entityManager.find(User.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registerUser(User user) {
        entityManager.merge(user);
    }
}
