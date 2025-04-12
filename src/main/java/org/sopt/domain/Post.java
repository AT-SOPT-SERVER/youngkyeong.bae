package org.sopt.domain;

public class Post {
    private final int id;
    private String title;

    public Post(int id, String title) {
        validateTitle(title);
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void updateTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("!!제목은 비어 있을 수 없습니다!!");
        }
        if (title.length() > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘지 않게 해주세요.");
        }
    }
}
