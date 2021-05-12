package DTO;

import Models.Board;
import Models.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryDTO {

    private int id;
    private String name;
    private List<BoardDTO> boards;
    private UserDTO createdBy;
    private UserDTO updatedBy;
    private Date createdAt;
    private Date updatedAt;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        //Boards
        List<Board> boards = category.getBoards();
        List<BoardDTO> boardDTOS = new ArrayList<>();
        for (int i = 0; i < boards.size(); i++) {
            BoardDTO boardDTO = new BoardDTO(boards.get(i), this);
            boardDTOS.add(boardDTO);
        }
        this.boards = boardDTOS;
        this.createdBy = new UserDTO(category.getCreatedBy());
        if(category.getUpdatedBy() != null) {
            this.updatedBy = new UserDTO(category.getUpdatedBy());
        }
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
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

    public List<BoardDTO> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardDTO> boards) {
        this.boards = boards;
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