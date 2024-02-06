package com.sparta.assignment.nbcampspringtodo.todo;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByUserIdOrderByLastModifiedDateDesc(Long userId);

  List<Todo> findAllByUserIdAndHiddenIsFalseOrderByLastModifiedDateDesc(Long userId);

  List<Todo> findAllByHiddenIsFalseOrUserIdOrderByLastModifiedDateDesc(Long userId);

  @Query("""
      select t from Todo t
      where t.title like concat('%', ?1, '%')
        and (t.hidden = false or (t.user.id = ?2 and t.hidden = true))
      order by t.lastModifiedDate DESC""")
  List<Todo> findTodoByTitle(String search, Long userId);

  default Todo findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(
        () -> new EntityNotFoundException("todo를 찾을 수 없음")
    );
  }

}
