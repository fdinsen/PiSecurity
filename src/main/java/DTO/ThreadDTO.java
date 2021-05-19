package DTO;

import Models.Board;
import Models.Post;
import Models.Thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThreadDTO {
    private int id;
    private String name;
    private String text;
    private int views;
    private BoardDTO board;
    private List<PostDTO> posts;
    private UserDTO createdBy;
    private UserDTO updatedBy;
    private Date createdAt;
    private Date updatedAt;

    public ThreadDTO(Thread thread) {
        this.id = thread.getId();
        this.name = thread.getName();
        this.text = thread.getText();
        this.views = thread.getViews();
        this.posts = postToPostDTO(thread.getPosts(), this);
        this.createdBy = new UserDTO(thread.getCreatedBy());
        if(thread.getUpdatedBy() != null) {
            this.updatedBy = new UserDTO(thread.getUpdatedBy());
        }
        this.createdAt = thread.getCreatedAt();
        this.updatedAt = thread.getUpdatedAt();
    }

    public ThreadDTO(Thread thread, BoardDTO boardDTO) {
        this.id = thread.getId();
        this.name = thread.getName();
        this.text = thread.getText();
        this.views = thread.getViews();
        this.board = boardDTO;
        if(thread.getPosts() != null) {
            this.posts = postToPostDTO(thread.getPosts(), this);
        }
        this.createdBy = new UserDTO(thread.getCreatedBy());
        if(thread.getUpdatedBy() != null) {
            this.updatedBy = new UserDTO(thread.getUpdatedBy());
        }
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }


    private List<PostDTO> postToPostDTO(List<Post> posts, ThreadDTO threadDTO) {
        //Threads
        List<PostDTO> postDTOS = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            PostDTO postDTO = new PostDTO(posts.get(i), threadDTO);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}