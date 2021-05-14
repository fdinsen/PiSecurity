package Facades.Interfaces;

import DTO.BoardDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Board;
import java.util.List;

public interface IBoardFacade {
    public void createBoard(String name, String Description, String catId, String createdByUsername) throws DBErrorException, UserNotFoundException, InvalidInputException;

    public BoardDTO getBoardFromID(int boardId) throws DBErrorException;

    public List<BoardDTO> getBoardsForCategory(String catId) throws DBErrorException, InvalidInputException;

    Boolean editBoard(String catIdString, String boardName, String description, String username, String beginEditString) throws InvalidInputException, UserNotFoundException, DBErrorException;

    public void deleteBoard(String boardIdString) throws InvalidInputException, DBErrorException;
}
