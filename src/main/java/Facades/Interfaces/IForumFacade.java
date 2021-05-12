package Facades.Interfaces;

import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Models.Category;

import java.util.List;

public interface IForumFacade {
    public List<CategoryDTO> getCategoriesWithBoards() throws DBErrorException;
}
