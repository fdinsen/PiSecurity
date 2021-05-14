package Facades.Interfaces;

import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;

import java.util.List;

public interface ICategoryFacade {
    public void createCategory(String name, String username) throws DBErrorException, UserNotFoundException, InvalidInputException;

    public CategoryDTO getCategoryFromName(String catName) throws DBErrorException;

    public CategoryDTO getCategoryFromID(int catId) throws DBErrorException;

    public List<CategoryDTO> getAllCategories() throws DBErrorException;

    public void deleteCategory(String catIdString) throws DBErrorException, InvalidInputException;

    public Boolean editCategory(String catIdString, String categoryName, String username, String beginEdit) throws DBErrorException, InvalidInputException, UserNotFoundException;
}
