package com.web.web.controllers;

import com.web.web.models.User;

import java.util.ArrayList;
import java.util.List;

import com.web.web.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.web.web.dao.UserDao;
import com.web.web.models.User;

@RestController//El controlador define las URL y los métodos HTTP que el cliente puede utilizar.
public class UserController {
    @Autowired//se usa para la inyeccion automaticas de dependencias,automatiza la creacion de objetos, desacopla componentes.Aqui hace que la clase UserDaoImp se cree como objeto
    private UserDao userDao;
    @Autowired
    private JWTUtil jwtUtil;
    //@RequestMapping(value = "datos_usuarios1111")Dinamico {id}:::Sirve para mapear cualquier tipo de petición HTTP.GET,POST,PUT,DELETE(GetMapping es mas limpio)
    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> getUsers(@RequestHeader(value="Authorization") String token) {//leemos/guardamos el token que viene del header que le damos en user.js

        if(!validateToken(token)){ return null; }//verifica que el token sea correcto
        return userDao.getUsers();
    }//programa de java que recibe una peticion,la peticion se hace en el navegador

  private boolean validateToken(String token){
      //verificar que el token sea correcto, extraemos el ID(falta verificar si el user está en BD)
      String userID = jwtUtil.getKey(token);
      return userID != null;


  }



    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public void registerUser(@RequestBody @NonNull User user){//@RequestBody lee el cuerpo de la request HTTP(por ejemplo un JSON enviado en un POST y lo mapea a un objeto Java (User en tu caso)

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);//para contraseña cifrada
        String hash = argon2.hash(1, 1024,1, user.getPassword());
        user.setPassword(hash);

        userDao.registerUser(user);
    }//lee body del POST en login.js


    @RequestMapping(value = "api/users/{id}" , method = RequestMethod.DELETE)//no olvidar el {id} dinamico
    public void deleteUser(@RequestHeader(value="Authorization") String token, @PathVariable Long id) {//@PathVariable Long ID: anotación que permite extraer valores dinámicos directamente de la URL de una petición HTTP

        if(!validateToken(token)){ return; }//verifica que tiene la sesion iniciada
        userDao.deleteUser(id);
    }

}
