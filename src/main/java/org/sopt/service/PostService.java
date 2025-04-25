package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository=postRepository;
    }

    public void createPost(String title) {
        if (postRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다.");
        }
        Post post = new Post(title);
        postRepository.save(post);
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 없습니다."));
    }

    public void updatePostTitle(Long id, String newTitle) {
        Post post = getPostById(id);
        if (postRepository.existsByTitle(newTitle)) {
            throw new IllegalArgumentException("이미 존재하는 제목으로 수정할 수 없습니다.");
        }
        post.updateTitle(newTitle);
        postRepository.save(post);
    }

    public void deletePostById(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    public List<Post> searchByKeyword(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }
}
