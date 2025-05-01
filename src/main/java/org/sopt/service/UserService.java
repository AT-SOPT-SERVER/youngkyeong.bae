package org.sopt.service;

import org.sopt.domain.User;
import org.sopt.dto.request.UserCreateRequest;
import org.sopt.exception.ConflictException;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signup(UserCreateRequest request) {
        request.validate();
        if (userRepository.existsByName(request.name()))
            throw new ConflictException("이미 존재하는 닉네임입니다.");
        return userRepository.save(new User(request.name(), request.email())).getId();
    }
}
