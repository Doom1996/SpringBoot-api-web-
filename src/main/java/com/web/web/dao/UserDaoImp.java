package com.web.web.dao;

import com.web.web.models.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
    public User getUserByCredentials(User user) {//existe algun email en la BD que ingreso el usuario,y la contraseña coincide?se usa para login
        String query = "FROM User WHERE email = :email";//ataque de inyeccion SQL,previsto
       List<User> lista =  entityManager
                .createQuery(query, User.class)
                .setParameter("email", user.getEmail())//buscar por email
                .getResultList();//lista con el usuario
       
       if(lista.isEmpty()){
           return null;
       }
        //user se contruye con los datos del POST
       String passwordHashed = lista.get(0).getPassword();

       Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
      if(argon2.verify(passwordHashed, user.getPassword())){//Argon2 necesita la contraseña original(el usuario ingreso) para volver a hashearla y compararla contra el hash almacenado.
            return lista.get(0);//si la verificacion fue correcta devuelve el user
      };
      return null;
    }
}
/*
 @Override
    public boolean verifyUserEmailPassword(User user) {//existe algun email en la BD que ingreso el usuario,y la contraseña coincide?
        String query = "FROM User WHERE email = :email AND password = :password ";//ataque de inyeccion SQL,previsto
       List<User> lista =  entityManager
                .createQuery(query, User.class)
                .setParameter("email", user.getEmail())//vincular valores a parámetros en consultas, aumentando la seguridad contra inyecciones SQL
                .setParameter("password", user.getPassword())
                .getResultList();
       return !lista.isEmpty();
}*/
