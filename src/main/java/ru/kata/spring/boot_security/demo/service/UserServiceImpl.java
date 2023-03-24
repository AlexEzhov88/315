package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void removeUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User updateUser) {
        User user = userRepository.getById(updateUser.getId());
        if (!user.getPassword().equals(updateUser.getPassword())) {
            updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
        }
        userRepository.save(updateUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseGet(User::new);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email not found!!"));
    }
}

// В этом классе мы используем UserRepository, RoleService и BCryptPasswordEncoder.
// UserRepository используется для доступа к базе данных, RoleService используется для получения
// списка всех ролей пользователей, а BCryptPasswordEncoder используется для шифрования паролей пользователей.
//
//Метод getAllUsers() возвращает список всех пользователей, найденных в базе данных.
// Метод getUserById() возвращает пользователя по заданному идентификатору.
// Метод removeUserById() удаляет пользователя из базы данных по заданному идентификатору.
// Метод saveUser() сохраняет нового пользователя в базе данных.
// Метод updateUser() обновляет данные существующего пользователя в базе данных.
//
//Метод findByEmail() используется для поиска пользователя по его электронной почте.
//
//Метод getAllRoles() возвращает список всех ролей пользователей.
//
//Метод loadUserByUsername() идентифицирует пользователя по его электронной почте.
// Если пользователь не найден, выбрасывается исключение UsernameNotFoundException.

