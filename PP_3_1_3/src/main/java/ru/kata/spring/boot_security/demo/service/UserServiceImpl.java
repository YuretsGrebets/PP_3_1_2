package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userDao;
    private final RoleRepository roleDao;
    private final ApplicationContext context;

    public UserServiceImpl(UserRepository userDao, RoleRepository roleDao, ApplicationContext context) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.context = context;
    }

    @Transactional
    @Override
    public void save(User user) {
        setEncryptedPassword(user);
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(User updatedUser) {
        setEncryptedPassword(updatedUser);
        userDao.save(updatedUser);
    }

    public List<User> index() {
        return userDao.findAll();
    }

    public User getUser(long id) {
        return userDao.getById(id);
    }

    public void delete(long id) {
        userDao.deleteById(id);
    }

    public User setRolesToUser(User user, long[] rolesIdArr) {
        List<Role> userRoles = new ArrayList<>();

        if (rolesIdArr != null) {
            for (long i : rolesIdArr) {
                userRoles.add(roleDao.getById(i));
            }
        }
        user.setRole(userRoles);
        return user;
    }

    @Override
    public void setEncryptedPassword(User user) {
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
