package org.sopt.domain;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private PostTag tag;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Post() {
    }

    public Post(String title, String content, PostTag tag, User user){
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public PostTag getTag() {
        return tag;
    }

    public void update(String newTitle, String newContent, PostTag newTag) {
        this.title = newTitle;
        this.content = newContent;
        this.tag = newTag;
    }
}
