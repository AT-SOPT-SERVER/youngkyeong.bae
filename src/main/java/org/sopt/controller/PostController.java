package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.dto.PostRequest;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private int postId;



    @PostMapping("/post")
    public void createPost(@RequestBody final PostRequest postRequest) {
        postService.createPost(postRequest.getTitle());
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    public Post getPostById(int id) {
        return postService.getPostById(id);
    }

    public boolean updatePostTitle(int id, String newTitle) {
        return postService.updatePostTitle(id, newTitle);
    }

    public boolean deletePostById(int id) {
        return postService.deletePostById(id);
    }

    public List<Post> searchPostsByKeyword(String keyword) {
        return postService.searchByKeyword(keyword);
    }
}
