package com.FitInfo.FitInfo.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsUserByEmail(String email);

  boolean existsUserByUsername(String username);
}
