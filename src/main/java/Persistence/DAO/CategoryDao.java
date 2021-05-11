package Persistence.DAO;
import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Login.LoginBean;
import Models.Category;
import Models.User;
import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Oliver
 */
public interface CategoryDao {
    public Category createCategory(String name, User user, EntityManager em) throws DBErrorException, UserNotFoundException;

    public Category getCategoryFromName(String catName,EntityManager em) throws DBErrorException;

    public List<Category> getAllCategories(EntityManager em) throws DBErrorException;

    public  boolean deleteCategory(int catId,EntityManager em);

    public  Category editCategory(int catId, String name,EntityManager em);
}
