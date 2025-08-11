package com.dawood.e_commerce.repository;

import com.dawood.e_commerce.entities.User;
import com.dawood.e_commerce.enums.AccountStatus;
import com.dawood.e_commerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndRole(String email, UserRole role);

    List<User> findByRole(UserRole role);

    List<User> findByAccountStatus(AccountStatus status);
}
