/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence.DAO;

import Exceptions.DBErrorException;
import Models.User;
import javax.persistence.EntityManager;

/**
 *
 * @author gamma
 */
public interface IPostDao {
    public int createPost(String text, int boardId, User user, EntityManager em) throws DBErrorException;
}
