package Persistence;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.User;
import Persistence.DAO.CreateUserDao;
import Persistence.DAO.UserDao;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
        } catch (Exception e) {
            throw new DBErrorException("Could not create Category as something went wrong while finding your user in DB.");
        }

        //Does not exist
        if(user == null){
            throw new UserNotFoundException("Could not create Category as your user could not be found in DB");
        }

        return user;
    }
}
