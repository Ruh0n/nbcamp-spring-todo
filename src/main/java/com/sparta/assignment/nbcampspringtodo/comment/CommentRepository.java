package com.sparta.assignment.nbcampspringtodo.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("select c from Comment c where c.todo.todoId = ?1")
  List<Comment> findAllByTodo_TodoId(Long todo_id);

}
