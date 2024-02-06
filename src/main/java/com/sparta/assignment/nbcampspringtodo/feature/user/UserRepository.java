package com.sparta.assignment.nbcampspringtodo.feature.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  default User findByUsernameOrElseThrow(String username) {
    return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User를 찾을 수 없음"));
  }

  default User findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(() -> new UsernameNotFoundException("User를 찾을 수 없음"));
  }

}
