package com.zaiats.catcher.controller;

import com.zaiats.catcher.model.CommentModel;
import com.zaiats.catcher.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentModel getComment(@PathVariable @Min(1) Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentModel createComment(@RequestBody @Valid CommentModel commentModel) {
        return commentService.saveComment(commentModel);
    }

}
