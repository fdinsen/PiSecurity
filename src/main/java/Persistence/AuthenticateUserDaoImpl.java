/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Models.Role;
import Models.User;
import Persistence.DAO.IAuthenticateUserDao;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utils.JWTHandling;
import utils.ValidationUtils;

public class AuthenticateUserDaoImpl implements IAuthenticateUserDao{

    @Override
    public boolean authenticateUser(String token, EntityManager em) {
        String username = JWTHandling.readJWT(ValidationUtils.escapeUnsafeCharacters(token));
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :un", User.class);
        query.setParameter("un", username);
        User user;
        try {
            em.getTransaction().begin();
            user = query.getSingleResult();
            if(user.getRole() == Role.unverified) {
                user.setRole(Role.user);
                em.persist(user);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        }finally {
            em.close();
        }
    }
    
}
