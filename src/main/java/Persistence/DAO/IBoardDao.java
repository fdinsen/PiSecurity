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

    public BoardDTO getBoardFromName(String name, Boolean closeEM, EntityManager em) throws DBErrorException;

    public BoardDTO getBoardDTOFromID(int boardId,Boolean closeEM, EntityManager em) throws DBErrorException;

    public Board getBoardFromID(int boardId,Boolean closeEM, EntityManager em) throws DBErrorException;

    public BoardDTO getBoardWithThreads(int boardId, EntityManager em) throws DBErrorException;

    public List<BoardDTO> getBoardsForCategory(CategoryDTO categoryDTO, EntityManager em) throws DBErrorException;

    void editBoard(BoardDTO boardDTO, String boardName, String description, User user, EntityManager em) throws DBErrorException;

    void deleteBoard(BoardDTO boardDTO, EntityManager em) throws DBErrorException;
}
