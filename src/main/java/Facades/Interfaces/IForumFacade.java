package Facades.Interfaces;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Models.Board;
import Models.Category;

import java.util.List;

public interface IForumFacade {
    public List<CategoryDTO> getCategoriesWithBoards() throws DBErrorException;

    public BoardDTO getBoardWithThreads(String catIdString) throws DBErrorException, InvalidInputException;
}
