package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
 private PostRepository postRepository;
 private CommentRepository commentRepository;
 private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not found with id:" + postId));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(newComment);
        return dto;
    }

    @Override
    public List<CommentDto> getByCommentsByPost(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not found with id:" + postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getByCommentId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not found with id:" + postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("post Not Found With Comment id:" + commentId));

        return mapToDto(comment);
    }


    @Override
    public CommentDto updateByCommentId(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not found with id:" + postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("post Not Found With Comment id:" + commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment save = commentRepository.save(comment);
        return mapToDto(save);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not found with id:" + postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("post Not Found With Comment id:" + commentId));
        commentRepository.deleteById(commentId);
    }


    CommentDto mapToDto(Comment comment){
        CommentDto map = modelMapper.map(comment, CommentDto.class);
        return map;
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment map = modelMapper.map(commentDto, Comment.class);
        return map;
    }
}
