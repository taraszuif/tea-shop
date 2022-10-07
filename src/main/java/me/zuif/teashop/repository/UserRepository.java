package me.zuif.teashop.repository;

import me.zuif.teashop.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);

    Optional<User> findById(String id);
}
