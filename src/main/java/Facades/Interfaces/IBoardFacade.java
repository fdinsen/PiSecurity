package Facades.Interfaces;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Board;
import java.util.List;

public interface IBoardFacade {
    public void createBoard(String name, String Description, String catId, String createdByUsername) throws DBErrorException, UserNotFoundException, InvalidInputException;

    public Board getBoardFromName(String name) throws DBErrorException;

    public Board getBoardFromID(int boardId) throws DBErrorException;

    public List<Board> getBoardsForCategory(String catId) throws DBErrorException;
}
