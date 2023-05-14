package com.zaiats.catcher.service;

import com.zaiats.catcher.model.CommentModel;

public interface CommentService {

    CommentModel getCommentById(Long id);

    CommentModel saveComment(CommentModel commentModel);

}
