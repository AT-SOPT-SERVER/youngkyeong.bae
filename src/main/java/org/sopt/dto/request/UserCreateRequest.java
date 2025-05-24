package org.sopt.dto.request;

import org.sopt.exception.BadRequestException;

public record UserCreateRequest (String name, String email) {
    public void validate() {
        if (name == null || name.isBlank())
            throw new BadRequestException("닉네임은 비어 있을 수 없습니다.");
        if (name.length() > 10)
            throw new BadRequestException("닉네임은 10자를 넘을 수 없습니다.");
    }
}
