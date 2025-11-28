package com.hongwei.factcheck.repository;

import com.hongwei.factcheck.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Implemented automatically by SpringDataJPA
    // Need to follow naming conventions: findBy, deleteBy, existsBy etc
    Optional<User> findByEmail(String email);
}
