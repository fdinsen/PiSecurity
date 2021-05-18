package Facades;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Models.Category;
import Persistence.BoardsDaoImpl;
import Persistence.CategoryDaoImpl;
import Facades.Interfaces.IForumFacade;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import java.util.List;

import static utils.ValidationUtils.boardIdStringValidation;
import static utils.ValidationUtils.categoryIdStringValidation;

public class ForumFacade implements IForumFacade {
    @Override
    public List<CategoryDTO> getCategoriesWithBoards() throws DBErrorException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Get categories
        CategoryDaoImpl categoryDaoImpl  = new CategoryDaoImpl();
        return categoryDaoImpl.getCategoriesWithBoards(em);
    }

    @Override
    public BoardDTO getBoardWithThreads(String boardIdString) throws DBErrorException, InvalidInputException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        boardIdString = StringEscapeUtils.escapeHtml4(boardIdString);

        //categoryId Validation
        int boardId = boardIdStringValidation(boardIdString);

        //Get board
        BoardsDaoImpl boardsDaoImpl  = new BoardsDaoImpl();
        BoardDTO dto = boardsDaoImpl.getBoardWithThreads(boardId, em);
        if(dto == null) {
            throw new InvalidInputException("Board was not found in DB");
        }
        return dto;
    }
}
