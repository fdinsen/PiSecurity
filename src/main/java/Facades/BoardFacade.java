package Facades;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.User;
import Persistence.BoardsDaoImpl;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import Facades.Interfaces.IBoardFacade;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.EntityManagerFactory;

import static utils.ValidationUtils.*;

public class BoardFacade implements IBoardFacade {
        private EntityManagerFactory EMF;
        
        public BoardFacade() {
            EMF = EMF_Creator.createEntityManagerFactory();
        }
    
    @Override
    public void createBoard(String boardName, String description, String catIdString, String createdByUsername) throws DBErrorException, UserNotFoundException, InvalidInputException {
        EntityManager em = EMF.createEntityManager();

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
    public BoardDTO getBoardFromID(int boardId) throws DBErrorException {
        EntityManager em = EMF.createEntityManager();

        //Get board
        BoardsDaoImpl boardsDaoImpl  = new BoardsDaoImpl();
        BoardDTO boardDTO = boardsDaoImpl.getBoardDTOFromID(boardId,true, em);


        return boardDTO;
    }

    @Override
    public List<BoardDTO> getBoardsForCategory(String catIdString) throws DBErrorException, InvalidInputException {
        EntityManager em = EMF.createEntityManager();

        //Escapes HTML tags
        catIdString = StringEscapeUtils.escapeHtml4(catIdString);

        //categoryId Validation
        CategoryDTO categoryDTO = categoryIdStringValidation(catIdString);

        //Create
        BoardsDaoImpl boardsDaoImpl = new BoardsDaoImpl();
        return boardsDaoImpl.getBoardsForCategory(categoryDTO, em);
    }

    @Override
    public Boolean editBoard(String boardIdString, String boardName, String description, String username, String beginEdit) throws InvalidInputException, UserNotFoundException, DBErrorException {
        EntityManager em = EMF.createEntityManager();

        //Escapes HTML
        beginEdit = StringEscapeUtils.escapeHtml4(beginEdit);
        boardIdString = StringEscapeUtils.escapeHtml4(boardIdString);
        boardName = StringEscapeUtils.escapeHtml4(boardName);
        description = StringEscapeUtils.escapeHtml4(description);
        username = StringEscapeUtils.escapeHtml4(username);

        //boardId Validation
        int boardId = idStringValidation(boardIdString);

        //Board name validation
        boardNameValidation(boardName);

        //Board description validation
        boardDescriptionValidation(description);

        //First click on edit
        if(beginEdit != null && beginEdit.equals("1")) {
            return true;
        }

        //Get user
        UserDaoImpl userDaoImpl  = new UserDaoImpl();
        User user = userDaoImpl.getUserFromUsername(username, em);

        //Create
        BoardsDaoImpl boardsDao = new BoardsDaoImpl();
        boardsDao.editBoard(boardId,boardName, description, user, em);

        return false;
    }

    @Override
    public void deleteBoard(String boardIdString) throws InvalidInputException, DBErrorException {
            EntityManager em = EMF.createEntityManager();

            //Escapes HTML
            boardIdString = StringEscapeUtils.escapeHtml4(boardIdString);

            //boardId Validation
            int boardId = idStringValidation(boardIdString);

            //Delete
            BoardsDaoImpl boardsDao = new BoardsDaoImpl();
            boardsDao.deleteBoard(boardId, em);
    }
}
