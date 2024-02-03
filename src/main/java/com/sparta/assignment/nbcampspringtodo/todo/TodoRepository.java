package com.sparta.assignment.nbcampspringtodo.todo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByUserId(Long userId);

  List<Todo> findAllByHiddenIsFalse();

  List<Todo> findAllByUserIdAndHiddenIsTrue(Long userId);

  @Query("select t from Todo t where t.title like concat('%', ?1, '%') and t.hidden = false")
  List<Todo> findAllByTitleContainsAndHiddenIsFalse(String search);

}
