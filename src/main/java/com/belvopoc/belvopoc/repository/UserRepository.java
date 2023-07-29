package com.belvopoc.belvopoc.repository;

import com.belvopoc.belvopoc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
