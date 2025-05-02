package org.sopt.dto.request;

import org.sopt.domain.PostTag;
import org.sopt.exception.BadRequestException;

public record PostRequest(String title, String content, PostTag tag) {
    public void validate() {
        if (title == null || title.isBlank())
            throw new BadRequestException("제목은 비어 있을 수 없습니다.");
        if (content == null || content.isBlank())
            throw new BadRequestException("내용은 비어 있을 수 없습니다.");
        if (title.length() > 30)
            throw new BadRequestException("제목은 30자를 넘을 수 없습니다.");
        if (content.length() > 1000)
            throw new BadRequestException("내용은 1000자를 넘을 수 없습니다.");
        if (tag == null)
            throw new BadRequestException("태그는 필수입니다.");
    }
}