package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.PostRequest;
import org.sopt.dto.response.PostDetailResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.BadRequestException;
import org.sopt.exception.ConflictException;
import org.sopt.exception.ForbiddenException;
import org.sopt.exception.ResourceNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository=postRepository;
        this.userRepository=userRepository;
    }

    @Transactional
    public Long createPost(Long userId, PostRequest request) {
        request.validate();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("해당 사용자가 존재하지 않습니다. id : " + userId));
        if (postRepository.existsByTitle(request.title())) {
            throw new ConflictException("이미 존재하는 제목입니다.");
        }
        Post post = new Post(request.title(), request.content(), user);
        return postRepository.save(post).getId();
    }


    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByIdDesc().stream()
                .map(post -> new PostResponse(post.getId(), post.getTitle(), post.getUser().getName()))
                .collect(Collectors.toList());
    }

    public PostDetailResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시글이 없습니다. id : " + id));
        return new PostDetailResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getName());
    }

    @Transactional
    public void updatePost(Long userId, Long postId, PostRequest request) {
        request.validate();
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시글이 없습니다. id : " + postId));
        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("작성자만 수정할 수 있습니다.");
        }
        if (postRepository.existsByTitle(request.title())) {
            throw new ConflictException("이미 존재하는 제목으로 수정할 수 없습니다.");
        }
        post.update(request.title(), request.content());
        postRepository.save(post);
    }

    @Transactional
    public void deletePostById(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시글이 없습니다. id : " + postId));
        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("작성자만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
    }

    public List<PostResponse> search(String keyword, String name) {
        boolean hasKw = keyword != null && !keyword.isBlank();
        boolean hasName = name  != null && !name.isBlank();

        if (!hasKw && !hasName) {
            throw new BadRequestException("검색어 또는 작성자를 하나 이상 입력해주세요.");
        }

        List<Post> found;
        if (hasKw && hasName) {
            found = postRepository
                    .findByTitleContainingIgnoreCaseAndUserNameContainingIgnoreCase(keyword, name);
        } else if (hasKw) {
            found = postRepository.findByTitleContainingIgnoreCase(keyword);
        } else {
            found = postRepository.findByUserNameContainingIgnoreCase(name);
        }

        if (found.isEmpty()) {
            throw new ResourceNotFoundException("검색 결과가 없습니다.");
        }

        return found.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getUser().getName()
                ))
                .collect(Collectors.toList());
    }
}
