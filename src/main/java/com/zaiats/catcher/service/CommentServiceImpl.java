package com.zaiats.catcher.service;

import com.zaiats.catcher.exception.ResourceNotFoundException;
import com.zaiats.catcher.model.CommentModel;
import com.zaiats.catcher.repository.CommentRepository;
import com.zaiats.catcher.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentModel getCommentById(Long id) {
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID: " + id + " not found!"));
        return CommentModel.fromEntity(foundComment);
    }

    @Override
    public CommentModel saveComment(CommentModel commentModel) {
        Comment comment = CommentModel.toEntity(commentModel);
        return CommentModel.fromEntity(commentRepository.save(comment));
    }

}
