package DTO;

import Models.Post;

import java.util.Date;

public class PostDTO {
    private int id;
    private String text;
    private ThreadDTO thread;
    private UserDTO createdBy;
    private UserDTO updatedBy;
    private Date createdAt;
    private Date updatedAt;

    public PostDTO(Post post, ThreadDTO threadDTO) {
        this.id = post.getId();
        this.text = post.getText();
        this.thread = threadDTO;
        this.createdBy = new UserDTO(post.getCreatedBy());
        this.updatedBy = new UserDTO(post.getCreatedBy());
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
    public PostDTO(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.createdBy = new UserDTO(post.getCreatedBy());
        this.updatedBy = new UserDTO(post.getCreatedBy());
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ThreadDTO getThread() {
        return thread;
    }

    public void setThread(ThreadDTO thread) {
        this.thread = thread;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public UserDTO getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserDTO updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}