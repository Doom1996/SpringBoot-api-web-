package com.web.web.controllers;

import com.web.web.models.User;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.web.web.dao.UserDao;
import com.web.web.models.User;

@RestController//El controlador define las URL y los métodos HTTP que el cliente puede utilizar.
public class UserController {


    @Autowired//se usa para la inyeccion automaticas de dependencias,automatiza la creacion de objetos, desacopla componentes.Aqui hace que la clase UserDaoImp se cree como objeto
    private UserDao userDao;

    //@RequestMapping(value = "datos_usuarios1111")Dinamico {id}:::Sirve para mapear cualquier tipo de petición HTTP.GET,POST,PUT,DELETE(GetMapping es mas limpio)
    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> getUsers() {//@PathVariable Long ID: anotación que permite extraer valores dinámicos directamente de la URL de una petición HTTP
        return userDao.getUsers();//devuelve un  JSON.Se ve en Fletch/XHR,response
    }//programa de java que recibe una peticion,la peticion se hace en el navegador
    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public void registerUser(@RequestBody User user){//@RequestBody lee el cuerpo de la request HTTP(por ejemplo un JSON enviado en un POST y lo mapea a un objeto Java (User en tu caso)
        userDao.registerUser(user);
    }//lee body del POST en login.js


    @RequestMapping(value = "api/users/{id}" , method = RequestMethod.DELETE)//no olvidar el {id} dinamico
    public String deleteUser(@PathVariable Long id) {
        userDao.deleteUser(id);
        return "Usuario eliminado correctamente";
    }

}
