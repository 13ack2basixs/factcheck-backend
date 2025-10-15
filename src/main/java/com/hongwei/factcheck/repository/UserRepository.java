package com.hongwei.factcheck.repository;

import com.hongwei.factcheck.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
