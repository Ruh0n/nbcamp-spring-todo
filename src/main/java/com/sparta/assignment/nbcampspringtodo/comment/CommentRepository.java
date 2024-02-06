package com.sparta.assignment.nbcampspringtodo.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByTodo_TodoId(Object unknownAttr1);

  List<Comment> findByTodo_Id(Long id);

}
