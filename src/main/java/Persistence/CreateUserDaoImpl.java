/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Models.Role;
import Models.User;
import Persistence.DAO.ICreateUserDao;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.security.crypto.bcrypt.BCrypt;
import utils.EMF_Creator;

/**
 *
 * @author gamma
 */
public class CreateUserDaoImpl implements ICreateUserDao {

    public User createUser(String username, String email, String password, EntityManager em) {
        em.getTransaction().begin();
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(generatePasswordHash(password));
        user.setRole(Role.unverified);
        em.persist(user);
        em.getTransaction().commit();
        return user;
    }

    public String generatePasswordHash(String password) {
        String generatedPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return generatedPasswordHash;
    }
    
    public boolean usernameExists(String username, EntityManager em) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :un", null);
        query.setParameter("un", username);
        List<User> user = query.getResultList();
        return user.size() != 0;
    }
}
