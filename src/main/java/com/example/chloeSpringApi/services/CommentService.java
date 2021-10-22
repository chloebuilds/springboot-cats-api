package com.example.chloeSpringApi.services;

import com.example.chloeSpringApi.models.Comment;
import com.example.chloeSpringApi.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public Comment createComment(Comment comment) {
//        if (comment.getBody() == null) {
//            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Your comment needs a body");
//        } //not needed due to @Valid annotation
        return commentRepo.save(comment);
    }

    public Iterable<Comment> getComments() {
        return commentRepo.findAll();
    }
}
