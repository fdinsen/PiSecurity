package Facades;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import DTO.ThreadDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Facades.Interfaces.IThreadFacade;
import Models.User;
import Persistence.BoardsDaoImpl;
import Persistence.DAO.IThreadDao;
import Persistence.ThreadDaoImpl;
import Persistence.UserDaoImpl;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import static utils.ValidationUtils.*;

public class ThreadFacade implements IThreadFacade {
    EntityManagerFactory EMF;
    public ThreadFacade() {
        EMF = EMF_Creator.createEntityManagerFactory();
    }
    @Override
    public int createThread(String threadName, String text, String boardId, String createdByUsername) throws DBErrorException, UserNotFoundException, InvalidInputException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        threadName = StringEscapeUtils.escapeHtml4(threadName);
        //text = StringEscapeUtils.escapeHtml4(text);
        text = Jsoup.clean(text, Whitelist.basicWithImages());
        boardId = StringEscapeUtils.escapeHtml4(boardId);
        createdByUsername = StringEscapeUtils.escapeHtml4(createdByUsername);

        //boardId Validation
        int boardIdInt = idStringValidation(boardId);

        //threadName name validation
        threadNameValidation(threadName);

        //text validation
        String cleanedText = threadTextValidation(text);

        //Get user
        UserDaoImpl userDaoImpl  = new UserDaoImpl();
        User user = userDaoImpl.getUserFromUsername(createdByUsername, em);

        //Create
        ThreadDaoImpl threadDaoImpl = new ThreadDaoImpl();
        return threadDaoImpl.createThread(threadName,cleanedText,boardIdInt, user, em);
    }
    
    public ThreadDTO getThread(String threadId) throws InvalidInputException, DBErrorException {
        EntityManager em = EMF.createEntityManager();
        
        //Escapes HTML Tags
        threadId = StringEscapeUtils.escapeHtml4(threadId);
        int threadIdInt = idStringValidation(threadId);
        
        IThreadDao threadDao = new ThreadDaoImpl();
        return threadDao.getThreadDTOById(threadIdInt, true, true, em);
    }
}
