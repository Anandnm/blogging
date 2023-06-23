package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getByCommentsByPost(@PathVariable(value = "postId") long postId){
        return   commentService.getByCommentsByPost(postId);

    }
    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getByCommentId(@PathVariable(value = "postId") long postId, @PathVariable(value = "id") long commentId){
        CommentDto dto = commentService.getByCommentId(postId, commentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1/comments/1
    @PutMapping(("/posts/{postId}/comments/{id}"))
    public ResponseEntity<CommentDto> updateByCommentId(@RequestBody CommentDto commentDto, @PathVariable(value = "postId") long postId, @PathVariable(value = "id") long commentId){
        CommentDto dto = commentService.updateByCommentId(postId, commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1/comments/1
    @DeleteMapping(("/posts/{postId}/comments/{id}"))
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId, @PathVariable(value = "id") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Delete The Record",HttpStatus.OK);
    }
}
