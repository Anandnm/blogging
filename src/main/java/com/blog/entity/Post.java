package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "posts", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     @Column(name ="title", nullable = false)
    private String title;
     @Column(name="description", nullable = false)
    private String description;
     @Column(name="content", nullable = false)
    private  String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
     private List<Comment> comments;
}
