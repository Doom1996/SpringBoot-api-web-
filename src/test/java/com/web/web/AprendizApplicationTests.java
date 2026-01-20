package com.web.web;
//Test de repositorio / DAO con Spring Boot: para probar si me trae los usuarios de la base de datos(validamos backen)
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.web.web.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.web.web.dao.UserDao;
import java.util.List;

@SpringBootTest
class AprendizApplicationTests {
    @Autowired//crea una instancia de UserDao,no hace falta hacer un new
    private UserDao userDao;

    @Test
    void debeTraerUsuariosDeLaBaseDeDatos() {

        List<User> users = userDao.getUsers();

        // Imprime en consola
        for (User user : users) {
            System.out.println("Nombre: " + user.getName());
            System.out.println("Apellido: " + user.getLastName());
        }

        // Aserción básica
        assert !users.isEmpty();
    }
}

