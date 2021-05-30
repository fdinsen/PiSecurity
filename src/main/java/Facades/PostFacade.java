/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Facades.Interfaces.IPostFacade;
import Models.Post;
import Models.User;
import Persistence.DAO.IPostDao;
import Persistence.DAO.UserDao;
import Persistence.PostDaoImpl;
import Persistence.UserDaoImpl;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;
import static utils.ValidationUtils.idStringValidation;

/**
 *
 * @author gamma
 */
public class PostFacade implements IPostFacade {
    EntityManagerFactory EMF;
    public PostFacade() {
        EMF = EMF_Creator.createEntityManagerFactory();
    }
    
    @Override
    public int createPost(String postText, String threadId, String username) throws DBErrorException, UserNotFoundException, InvalidInputException{
        EntityManager em = EMF.createEntityManager();
        
        int threadIdInt = idStringValidation(threadId);
        
        //Get user
        UserDao userDaoImpl  = new UserDaoImpl();
        User user = userDaoImpl.getUserFromUsername(username, em);
        
       IPostDao postDao = new PostDaoImpl();
       return postDao.createPost(postText, threadIdInt, user, em);
    }
}
