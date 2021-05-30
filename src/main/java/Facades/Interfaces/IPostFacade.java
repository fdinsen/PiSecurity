/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades.Interfaces;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;

/**
 *
 * @author gamma
 */
public interface IPostFacade {
    public int createPost(String postText, String threadId, String username) throws DBErrorException, UserNotFoundException,InvalidInputException;
}
