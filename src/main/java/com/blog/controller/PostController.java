package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping
    public List<PostDto> getALlPost(){
        List<PostDto> aLlPost = postService.getALlPost();
        return aLlPost;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updateByPost(@RequestBody PostDto postDto , @PathVariable("id") long id){
        PostDto dto = postService.updateByPost(postDto, id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
    return new ResponseEntity<>("Post Deleted", HttpStatus.OK);
    }
}
