package ru.mytracky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mytracky.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);
}
