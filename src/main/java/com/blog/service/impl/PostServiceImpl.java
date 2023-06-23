package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper= modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post save = postRepository.save(post);
        PostDto dto = new PostDto();
        dto.setId(save.getId());
        dto.setTitle(save.getTitle());
        dto.setDescription(save.getDescription());
        dto.setContent(save.getContent());
        return dto;
    }

    @Override
    public List<PostDto> getALlPost() {
        List<PostDto> collect = postRepository.findAll().stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post NOt Found By ID" + id));
       return mapToDto(post);
    }

    @Override
    public PostDto updateByPost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post NOt Found By ID" + id));
        Post posts = mapToEntity(postDto);
        posts.setId(id);
        Post save = postRepository.save(posts);
        PostDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post NOt Found By ID" + id));
        postRepository.deleteById(id);
    }

    PostDto mapToDto (Post post){
        PostDto map = modelMapper.map(post, PostDto.class);
        return map;
    }

    Post mapToEntity(PostDto postDto){
        Post map = modelMapper.map(postDto, Post.class);
        return map;
    }
}
