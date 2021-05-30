/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Exceptions.DBErrorException;
import Models.Post;
import Models.User;
import Models.Thread;
import Persistence.DAO.IPostDao;
import Persistence.DAO.IThreadDao;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author gamma
 */
public class PostDaoImpl implements IPostDao{

    @Override
    public int createPost(String text, int threadId, User user, EntityManager em) throws DBErrorException {
        try {
            em.getTransaction().begin();
            Post post = new Post();
            
            IThreadDao threadDao = new ThreadDaoImpl();
            Thread thread = threadDao.getThreadById(threadId, false, false, em);
            post.setThread(thread);
            post.setText(text);
            post.setCreatedBy(user);
            em.persist(post);
            em.getTransaction().commit();
            
            return post.getId();
        }catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not create post by user " + user.getEmail());
            throw new DBErrorException("Something went wrong while creating thread in DB");
        }finally {
            em.close();
        }
    }
    
}
