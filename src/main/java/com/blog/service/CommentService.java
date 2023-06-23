package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getByCommentsByPost(long postId);

    CommentDto getByCommentId(long postId, long commentId);

    CommentDto updateByCommentId(long postId, long commentId, CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}
