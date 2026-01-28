package com.web.web.controllers;
//esta clase se usa para verificar que el usuario y contraseña sean correctos.Esta clase se llama cuando se haga un POST en login
import com.web.web.dao.UserDao;
import com.web.web.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody User user){//@RequestBody lee el cuerpo de la request HTTP(por ejemplo un JSON enviado en un POST y lo mapea a un objeto Java (User en tu caso)

        if(userDao.verifyUserEmailPassword(user)){
            return "OK";
        }
        return "FAIL";
    }

}
