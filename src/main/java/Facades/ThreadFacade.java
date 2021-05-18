package Facades;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Facades.Interfaces.IThreadFacade;
import Models.User;
import Persistence.BoardsDaoImpl;
import Persistence.ThreadDaoImpl;
import Persistence.UserDaoImpl;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;

import static utils.ValidationUtils.*;

public class ThreadFacade implements IThreadFacade {
    @Override
    public int createThread(String threadName, String text, String boardId, String createdByUsername) throws DBErrorException, UserNotFoundException, InvalidInputException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        threadName = StringEscapeUtils.escapeHtml4(threadName);
        text = StringEscapeUtils.escapeHtml4(text);
        boardId = StringEscapeUtils.escapeHtml4(boardId);
        createdByUsername = StringEscapeUtils.escapeHtml4(createdByUsername);

        //boardId Validation
        BoardDTO boardDTO = boardIdStringValidation(boardId);

        //threadName name validation
        threadNameValidation(threadName);

        //text validation
        String cleanedText = threadTextValidation(text);

        //Get user
        UserDaoImpl userDaoImpl  = new UserDaoImpl();
        User user = userDaoImpl.getUserFromUsername(createdByUsername, em);

        //Create
        ThreadDaoImpl threadDaoImpl = new ThreadDaoImpl();
        return threadDaoImpl.createThread(threadName,cleanedText,boardDTO, user, em);
    }
}
