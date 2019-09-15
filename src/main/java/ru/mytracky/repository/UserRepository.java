package ru.mytracky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mytracky.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String name);
    User findByEmail(String email);
}