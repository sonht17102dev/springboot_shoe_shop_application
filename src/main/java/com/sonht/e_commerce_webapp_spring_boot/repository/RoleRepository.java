package com.sonht.e_commerce_webapp_spring_boot.repository;

import com.sonht.e_commerce_webapp_spring_boot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
