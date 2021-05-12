package Persistence.DAO;
import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.User;
import javax.persistence.EntityManager;

/**
 *
 * @author Oliver
 */
public interface UserDao {
    public User getUserFromUsername(String username, EntityManager em) throws DBErrorException, UserNotFoundException;
}
