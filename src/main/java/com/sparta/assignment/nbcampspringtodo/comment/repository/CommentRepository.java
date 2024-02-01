package com.sparta.assignment.nbcampspringtodo.comment.repository;

import com.sparta.assignment.nbcampspringtodo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
