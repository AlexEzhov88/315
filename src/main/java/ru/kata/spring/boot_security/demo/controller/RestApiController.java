package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;

    public RestApiController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> users = userService.getAllUsers();
        return (users != null && !users.isEmpty())
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public User getUserById(@PathVariable Integer id) {

        return userService.getUserById(id);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByEmail(Principal principal) {
        System.out.println(principal.getName());
        return new ResponseEntity<>(userService.findByEmail(principal.getName()), HttpStatus.OK);
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
    }

}



// Этот код определяет контроллер RestApiController для обработки REST API запросов.
// Класс RestApiController помечен аннотацией @RestController, которая говорит Spring,
// что это RESTful контроллер. Он обрабатывает запросы по определенным адресам и выполняет определенные действия.
//
//Например, метод showAllUsers() обрабатывает GET запросы на адрес /api/admin и возвращает список всех пользователей,
// хранящихся в системе. Если список пользователей пуст, то возвращается HTTP код ответа NOT_FOUND (404).
//
//Метод newUser() обрабатывает POST запросы на адрес /api/admin и добавляет нового пользователя в систему.
// Он получает объект пользователя из тела запроса (переданного в JSON формате) и сохраняет его в базе данных.
//
//Метод deleteUser() обрабатывает DELETE запросы на адрес /api/admin/{id} и удаляет пользователя
// с указанным идентификатором.
//
//Метод getUserById() обрабатывает GET запросы на адрес /api/admin/{id} и возвращает пользователя
// с указанным идентификатором.
//
//Метод getUserByEmail() обрабатывает GET запросы на адрес /api/user и возвращает информацию о
// пользователе, авторизованном в текущей сессии. Для этого метод получает информацию об авторизованном
// пользователе из объекта Principal, который Spring автоматически инжектирует в метод.
//
//Метод updateUser() обрабатывает PATCH запросы на адрес /api/admin/{id} и обновляет информацию о
// пользователе. Он получает объект пользователя из тела запроса и обновляет соответствующую запись в базе данных.
//
//Метод getAllRoles() обрабатывает GET запросы на адрес /api/roles и возвращает список всех ролей,
// доступных в системе.
