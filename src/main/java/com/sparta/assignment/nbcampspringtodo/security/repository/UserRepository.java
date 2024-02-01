package com.sparta.assignment.nbcampspringtodo.security.repository;

import com.sparta.assignment.nbcampspringtodo.security.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}
