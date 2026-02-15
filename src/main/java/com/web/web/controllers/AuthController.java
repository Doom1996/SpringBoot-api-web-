package com.web.web.controllers;
//Aqui se crea el Token
import com.web.web.dao.UserDao;
import com.web.web.models.User;
import com.web.web.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JWTUtil jwtUtil;
    @RequestMapping(value = "api/login", method = RequestMethod.POST)//la url enlaza con el archivo login.js
    public String login(@RequestBody User user){//@RequestBody lee el cuerpo de la request HTTP(por ejemplo un JSON enviado en un POST y lo mapea a un objeto Java (User en tu caso)

        User userLog = userDao.getUserByCredentials(user);//buscar el usuario en BD
        if(userLog != null){
          String tokenJWT = jwtUtil.create(String.valueOf(userLog.getId()), userLog.getEmail());//genera el token en base a estos dos datos
            return tokenJWT;
         }
        return "FAIL";
    }

}
