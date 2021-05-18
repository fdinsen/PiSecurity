package DTO;

import Models.Board;
import Models.Thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoardDTO {
    private int id;
    private String name;
    private String description;
    private CategoryDTO category;
    private List<ThreadDTO> threads;
    private UserDTO createdBy;
    private UserDTO updatedBy;
    private Date createdAt;
    private Date updatedAt;


    //Used in categoryDTO
    BoardDTO(Board board, CategoryDTO category) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.category = category;
        this.threads = threadsToThreadsDTO(board.getThreads(), this);
        this.createdBy = new UserDTO(board.getCreatedBy());
        if(board.getUpdatedBy() != null) {
            this.updatedBy = new UserDTO(board.getUpdatedBy());
        }
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }


    public BoardDTO(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        if(board.getCategory() != null) {
            this.category = new CategoryDTO(board.getCategory(), this);
        }
        if(board.getThreads() != null) {
            this.threads = threadsToThreadsDTO(board.getThreads(), this);
        }
        this.createdBy = new UserDTO(board.getCreatedBy());
        if(board.getUpdatedBy() != null) {
            this.updatedBy = new UserDTO(board.getUpdatedBy());
        }
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

    private List<ThreadDTO> threadsToThreadsDTO(List<Thread> threads, BoardDTO boardDTO) {
        //Threads
        List<ThreadDTO> threadDTOS = new ArrayList<>();
        for (int i = 0; i < threads.size(); i++) {
            ThreadDTO threadDTO = new ThreadDTO(threads.get(i), boardDTO);
            threadDTOS.add(threadDTO);
        }
        return threadDTOS;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public List<ThreadDTO> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadDTO> threads) {
        this.threads = threads;
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