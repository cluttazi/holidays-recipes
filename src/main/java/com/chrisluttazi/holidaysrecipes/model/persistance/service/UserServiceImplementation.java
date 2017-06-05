package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.auxiliary.Utilities;
import com.chrisluttazi.holidaysrecipes.model.Authority;
import com.chrisluttazi.holidaysrecipes.model.Role;
import com.chrisluttazi.holidaysrecipes.model.User;
import com.chrisluttazi.holidaysrecipes.model.UserId;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.AuthorityDAO;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.UserDAO;
import com.chrisluttazi.holidaysrecipes.model.persistance.dao.UserIdDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserIdDAO userIdDAO;

    @Autowired
    private AuthorityDAO userRoleDAO;

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User create(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User returnUser = userDAO.save(user);
        UserId id = new UserId(returnUser);
        id = userIdDAO.save(id);

        returnUser.setUserId(id.getId());
        return userDAO.save(returnUser);
    }

    @Override
    public void createAdmin(User admin) {
        if (!userDAO.findAll().isEmpty()) {
            return;
        }
        admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        User adminUser = userDAO.save(admin);

        UserId id = new UserId(adminUser);
        Authority auth = new Authority(adminUser, Role.ADMIN.toString());

        adminUser.setUserId(id.getId());
        adminUser.getAuthority().add(auth);
        userDAO.save(adminUser);
        userRoleDAO.save(auth);
        userIdDAO.save(id);
    }

    @Override
    public User update(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userDAO.save(user);
    }

    private User setStatus(Long id, Boolean status) {
        User user = userDAO.findByUserId(id);
        if (user == null) {
            return user;
        }
        user.setEnabled(status);
        return userDAO.save(user);
    }

    @Override
    public User disable(Long id) {
        return this.setStatus(id, false);
    }

    @Override
    public User enable(Long id) {
        return this.setStatus(id, true);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User register(User user) {
        return userDAO.save(user);
    }

    @Override
    public User changePassword(User user) {
        if (Utilities.currentUser().equalsIgnoreCase(user.getUsername())) {
            return userDAO.save(user);
        }
        return null;
    }

    @Override
    public User findByUserId(Long id) {
        return userDAO.findByUserId(id);
    }

    @Override
    public void addAuthority(Long id, String role) {
        User user = userDAO.findByUserId(id);
        if (user == null) {
            return;
        }
        Authority auth = new Authority(user, role.toUpperCase());
        user.getAuthority().add(auth);
        userDAO.save(user);
        userRoleDAO.save(auth);

    }

    @Override
    public void removeAuthority(Long id) {
        User user = userDAO.findByUserId(id);
        if (user == null) {
            return;
        }
        Set<Authority> list = user.getAuthority();
        for (Authority auth : list) {
            userRoleDAO.delete(auth);
        }
        user.setAuthority(new HashSet<Authority>(0));
        userDAO.save(user);
    }

}
