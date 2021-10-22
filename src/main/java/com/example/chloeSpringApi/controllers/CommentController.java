package com.example.chloeSpringApi.controllers;

import com.example.chloeSpringApi.models.Comment;
import com.example.chloeSpringApi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    // ! @Valid annotation only accepts a valid comment
    public Comment postComment(@RequestBody @Valid Comment comment) {

        return commentService.createComment(comment);
    }

    @GetMapping("/comments")
    public Iterable<Comment> getComments() {

        return commentService.getComments();
    }

}
