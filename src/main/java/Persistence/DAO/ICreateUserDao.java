/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence.DAO;
import Models.User;
import javax.persistence.EntityManager;

/**
 *
 * @author gamma
 */
public interface ICreateUserDao {
    public User createUser(String username, String email, String password, EntityManager em);
}
