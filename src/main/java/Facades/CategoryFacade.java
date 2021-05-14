package Facades;

import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.User;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import Facades.Interfaces.ICategoryFacade;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import java.util.List;

import static utils.ValidationUtils.categoryIdStringValidation;
import static utils.ValidationUtils.categoryNameValidation;

/**
 *
 * @author Oliver
 */
public class CategoryFacade implements ICategoryFacade {

    @Override
    public void createCategory(String name, String username) throws DBErrorException, UserNotFoundException, InvalidInputException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String categoryName = StringEscapeUtils.escapeHtml4(name);

        //Category name validation
        categoryNameValidation(categoryName);

        //Get user
        UserDaoImpl userDaoImpl  = new UserDaoImpl();
        User user = userDaoImpl.getUserFromUsername(username, em);

        //Create
        CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
        categoryDaoImpl.createCategory(categoryName, user, em);
    }

    @Override
    public CategoryDTO getCategoryFromName(String catName) throws DBErrorException {
        return null;
    }

    @Override
    public CategoryDTO getCategoryFromID(int catId) throws DBErrorException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Get category
        CategoryDaoImpl categoryDaoImpl  = new CategoryDaoImpl();
        CategoryDTO categoryDTO = categoryDaoImpl.getCategoryDTOFromID(catId, em);

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() throws DBErrorException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Get categories
        CategoryDaoImpl categoryDaoImpl  = new CategoryDaoImpl();

        return categoryDaoImpl.getAllCategories(em);
    }

    @Override
    public void deleteCategory(String catIdString) throws DBErrorException, InvalidInputException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML
        catIdString = StringEscapeUtils.escapeHtml4(catIdString);

        //categoryId Validation
        CategoryDTO categoryDTO = categoryIdStringValidation(catIdString);

        //Delete
        CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
        categoryDaoImpl.deleteCategory(categoryDTO, em);
    }

    @Override
    public Boolean editCategory(String catIdString, String categoryName, String username, String beginEdit) throws DBErrorException, InvalidInputException, UserNotFoundException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML
        beginEdit = StringEscapeUtils.escapeHtml4(beginEdit);
        catIdString = StringEscapeUtils.escapeHtml4(catIdString);
        categoryName = StringEscapeUtils.escapeHtml4(categoryName);
        username = StringEscapeUtils.escapeHtml4(username);

        //categoryId Validation
        CategoryDTO categoryDTO = categoryIdStringValidation(catIdString);

        //Category name validation
        categoryNameValidation(categoryName);

        //First click on edit
        if(beginEdit != null && beginEdit.equals("1")) {
            return true;
        }

        //Get user
        UserDaoImpl userDaoImpl  = new UserDaoImpl();
        User user = userDaoImpl.getUserFromUsername(username, em);

        //Create
        CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
        categoryDaoImpl.editCategory(categoryDTO,categoryName, user, em);

        return false;
    }
}