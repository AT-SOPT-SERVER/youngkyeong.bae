package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.dto.request.PostRequest;
import org.sopt.dto.response.PostDetailResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestHeader Long userId, @RequestBody PostRequest request) {
        Long id = postService.createPost(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, "/posts/" + id).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@RequestHeader Long userId, @PathVariable("id") Long id, @RequestBody PostRequest request) {
        postService.updatePost(userId, id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@RequestHeader Long userId, @PathVariable("id") Long id) {
        postService.deletePostById(userId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "name",  required = false) String name
    ) {
        return ResponseEntity.ok(postService.search(keyword, name));
    }
}
