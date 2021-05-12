package Facades;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Board;
import Models.Category;
import Models.User;
import Persistence.BoardsDaoImpl;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import Facades.Interfaces.IBoardFacade;
import Facades.Interfaces.IForumFacade;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.List;

import static utils.ValidationUtils.*;

public class BoardFacade implements IBoardFacade {
    @Override
    public void createBoard(String boardName, String description, String catIdString, String createdByUsername) throws DBErrorException, UserNotFoundException, InvalidInputException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        boardName = StringEscapeUtils.escapeHtml4(boardName);
        description = StringEscapeUtils.escapeHtml4(description);
        catIdString = StringEscapeUtils.escapeHtml4(catIdString);
        createdByUsername = StringEscapeUtils.escapeHtml4(createdByUsername);

        //categoryId Validation
        CategoryDTO category = categoryIdStringValidation(catIdString);

        //Board name validation
        boardNameValidation(boardName);

        //description validation
        boardDescriptionValidation(description);

        //Get user
        UserDaoImpl userDaoImpl  = new UserDaoImpl();
        User user = userDaoImpl.getUserFromUsername(createdByUsername, em);

        //Create
        BoardsDaoImpl boardsDaoImpl = new BoardsDaoImpl();
        boardsDaoImpl.createBoard(boardName,description, category, user, em);
    }

    @Override
    public Board getBoardFromName(String name) throws DBErrorException {
        return null;
    }

    @Override
    public BoardDTO getBoardFromID(int boardId) throws DBErrorException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Get category
        BoardsDaoImpl boardsDaoImpl  = new BoardsDaoImpl();
        BoardDTO boardDTO = boardsDaoImpl.getBoardFromID(boardId, em);


        return boardDTO;
    }

    @Override
    public List<Board> getBoardsForCategory(String catId) throws DBErrorException {
        return null;
    }
}
