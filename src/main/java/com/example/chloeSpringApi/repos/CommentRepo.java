package com.example.chloeSpringApi.repos;


import com.example.chloeSpringApi.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<Comment, Integer> {}

