/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence.DAO;

import javax.persistence.EntityManager;

/**
 *
 * @author gamma
 */
public interface IAuthenticateUserDao {
    public boolean authenticateUser(String token, EntityManager em);
}
