/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence.DAO;
import DTO.UserDTO;
import javax.persistence.EntityManager;

/**
 *
 * @author gamma
 */
public interface ICreateUserDao {
    public UserDTO createUser(String username, String email, String password, EntityManager em);
    public boolean usernameExists(String username, EntityManager em);
}
