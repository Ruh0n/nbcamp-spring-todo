package com.sparta.assignment.nbcampspringtodo.todo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByUserIdOrderByLastModifiedDateDesc(Long userId);

  List<Todo> findAllByUserIdAndHiddenIsFalseOrderByLastModifiedDateDesc(Long userId);

  List<Todo> findAllByTitleContainsAndHiddenIsFalseOrderByLastModifiedDateDesc(String search);

  List<Todo> findAllByHiddenIsFalseOrUserIdOrderByLastModifiedDateDesc(Long userId);

}
