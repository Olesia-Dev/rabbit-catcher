package com.zaiats.catcher.repository;

import com.zaiats.catcher.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
