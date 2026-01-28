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
    public List<User> getUsers() {//habla con BD
        String query = "FROM User";//Trabaja con clases mapeadas como entidades,esto es JPQL.equivalente a SELECT * FROM users;
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

    @Override
    public boolean verifyUserEmailPassword(User user) {//existe algun email en la BD que ingreso el usuario?
        String query = "FROM User WHERE email = :email AND password = :password ";//ataque de inyeccion SQL,previsto
       List<User> lista =  entityManager
                .createQuery(query, User.class)
                .setParameter("email", user.getEmail())//vincular valores a parámetros en consultas, aumentando la seguridad contra inyecciones SQL
                .setParameter("password", user.getPassword())
                .getResultList();
       return !lista.isEmpty();
       }
    }

