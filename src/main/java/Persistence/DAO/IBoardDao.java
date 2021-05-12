package Persistence.DAO;
import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.Board;
import Models.Category;
import Models.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Oliver
 */
public interface IBoardDao {
    public void createBoard(String name, String description, CategoryDTO categoryDTO, User createdBy, EntityManager em) throws DBErrorException, UserNotFoundException;

    public BoardDTO getBoardFromName(String name, EntityManager em) throws DBErrorException;

    public BoardDTO getBoardFromID(int boardId, EntityManager em) throws DBErrorException;

    public List<BoardDTO> getBoardsForCategory(CategoryDTO categoryDTO, EntityManager em) throws DBErrorException;
}
