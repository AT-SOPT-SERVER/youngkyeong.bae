package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.IdGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private Instant lastCreatedAt = null;

    public void createPost(String title) {
        Instant now = Instant.now();
        if (postRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다.");
        }
        if (lastCreatedAt != null && Duration.between(lastCreatedAt, now).toMinutes() < 3) {
            throw new IllegalArgumentException("마지막 게시글 작성 이후 3분 이내에는 새로운 게시글을 작성할 수 없습니다.");
        }
        Post post = new Post(IdGenerator.generate(), title);
        postRepository.save(post);
        lastCreatedAt = now;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findPostById(id);
    }

    public boolean updatePostTitle(int id, String newTitle) {
        Post post = postRepository.findPostById(id);
        if(post == null) return false;
        if (postRepository.existsByTitle(newTitle)) {
            throw new IllegalArgumentException("이미 존재하는 제목으로 수정할 수 없습니다.");
        }
        post.updateTitle(newTitle);
        return true;
    }

    public boolean deletePostById(int id) {
        return postRepository.delete(id);
    }
}
