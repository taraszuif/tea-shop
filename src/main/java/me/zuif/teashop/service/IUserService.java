package me.zuif.teashop.service;

import me.zuif.teashop.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    void save(User user);

    void login(String username, String password);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(String id);
}
