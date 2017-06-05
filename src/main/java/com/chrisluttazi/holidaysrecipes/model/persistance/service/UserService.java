package com.chrisluttazi.holidaysrecipes.model.persistance.service;

import com.chrisluttazi.holidaysrecipes.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

    public User findByUsername(String username);

    @PreAuthorize("hasAuthority('ADMIN')")
    public User update(User user);

    @PreAuthorize("hasAuthority('ADMIN')")
    public User create(User user);

    public User register(User user);

    public User changePassword(User user);

    @PreAuthorize("hasAuthority('ADMIN')")
    public User disable(Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    public User enable(Long id);

    public List<User> findAll();

    public void createAdmin(User user);

    public User findByUserId(Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    public void addAuthority(Long id, String role);

    @PreAuthorize("hasAuthority('ADMIN')")
    public void removeAuthority(Long id);

}
