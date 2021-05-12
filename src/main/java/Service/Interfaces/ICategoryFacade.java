package Service.Interfaces;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.User;

import javax.persistence.EntityManager;
import java.util.List;

public interface ICategoryFacade {
    public void createCategory(String name, String username) throws DBErrorException, UserNotFoundException, InvalidInputException;

    public Category getCategoryFromName(String catName) throws DBErrorException;

    public Category getCategoryFromID(int catId) throws DBErrorException;

    public List<Category> getAllCategories() throws DBErrorException;

    public  void deleteCategory(String catIdString) throws DBErrorException, InvalidInputException;

    public  Boolean editCategory(String catIdString, String categoryName,String username,String beginEdit) throws DBErrorException, InvalidInputException, UserNotFoundException;
}
