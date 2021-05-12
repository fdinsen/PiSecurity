package Persistence.DAO;
import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.User;
import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Oliver
 */
public interface ICategoryDao {
    public Category createCategory(String name, User user, EntityManager em) throws DBErrorException, UserNotFoundException;

    public Category getCategoryFromName(String catName,EntityManager em) throws DBErrorException;

    public Category getCategoryFromID(int catId,EntityManager em) throws DBErrorException;

    public List<Category> getAllCategories(EntityManager em) throws DBErrorException;

    public List<Category> getCategoriesWithBoardsAndCount(EntityManager em) throws DBErrorException;

    public  boolean deleteCategory(Category category,EntityManager em) throws DBErrorException;

    public  Category editCategory(Category category, String name,User user,EntityManager em) throws DBErrorException;
}
