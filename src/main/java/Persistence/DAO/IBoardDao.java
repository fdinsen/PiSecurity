package Persistence.DAO;
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
    public Board createBoard(String name, String Description, Category category, User createdBy, EntityManager em) throws DBErrorException, UserNotFoundException;

    public Board getBoardFromName(String name, EntityManager em) throws DBErrorException;

    public Board getBoardFromID(int boardId, EntityManager em) throws DBErrorException;

    public List<Board> getBoardsForCategory(Category category,EntityManager em) throws DBErrorException;
}
