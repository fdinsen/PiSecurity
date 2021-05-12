package Persistence.DAO;
import DTO.CategoryDTO;
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
    public CategoryDTO createCategory(String name, User user, EntityManager em) throws DBErrorException, UserNotFoundException;

    public CategoryDTO getCategoryFromName(String catName,EntityManager em) throws DBErrorException;

    public CategoryDTO getCategoryDTOFromID(int catId,EntityManager em) throws DBErrorException;

    public Category getCategoryFromID(int catId,Boolean closeEM, EntityManager em) throws DBErrorException;

    public List<CategoryDTO> getAllCategories(EntityManager em) throws DBErrorException;

    public List<CategoryDTO> getCategoriesWithBoards(EntityManager em) throws DBErrorException;

    public  void deleteCategory(CategoryDTO categoryDTO,EntityManager em) throws DBErrorException;

    public  void editCategory(CategoryDTO categoryDTO, String name, User user, EntityManager em) throws DBErrorException;

    void editCategory(Category categoryDTO, String name, User user, EntityManager em) throws DBErrorException;
}
