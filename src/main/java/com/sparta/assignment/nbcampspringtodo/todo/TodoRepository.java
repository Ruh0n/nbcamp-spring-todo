package com.sparta.assignment.nbcampspringtodo.todo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByUserId(Long userId);

  List<Todo> findAllByHiddenIsFalse();

  List<Todo> findAllByUserIdAndHiddenIsTrue(Long userId);

  List<Todo> findAllByTitleContainsAndHiddenIsFalse(String search);

}
