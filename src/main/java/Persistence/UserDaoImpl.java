package Persistence;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.User;
import Persistence.DAO.UserDao;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author Oliver
 */
public class UserDaoImpl implements UserDao {

    @Override
    public User getUserFromUsername(String username, EntityManager em) throws DBErrorException, UserNotFoundException {
        User user = null;

        //find user in db
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :uname", User.class);
            query.setParameter("uname", username);
            user = query.getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(this.getClass().getName()).warn("User " + username + " could not be found.");
            throw new UserNotFoundException("User could not be found in DB");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not get user from username "+ username);
            throw new DBErrorException("Something went wrong while finding user");
        }

        //Does not exist
        if (user == null) {
            Logger.getLogger(this.getClass().getName()).warn("User " + username + " could not be found.");
            throw new UserNotFoundException("User could not be found in DB");
        }

        return user;
    }

    @Override
    public String updateProfilePicture(String profilePicture, String username, EntityManager em) throws DBErrorException, UserNotFoundException {
        User user = null;
        String oldProfilePicture = null;
        try {
            //find user in db
            user = getUserFromUsername(username, em);
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while finding user");
        }

        //Does not exist
        if(user == null){
            throw new UserNotFoundException("User could not be found in DB");
        }

        try {
            em.getTransaction().begin();
            oldProfilePicture = user.getProfilePicture();
            user.setProfilePicture(profilePicture);
            em.persist(user);
            em.getTransaction().commit();

            return oldProfilePicture;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while updating profile picture");
        }
    }
}
