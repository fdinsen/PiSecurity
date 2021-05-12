package Facades.Interfaces;

import Exceptions.DBErrorException;
import Models.Category;

import java.util.List;

public interface IForumFacade {
    public List<Category> getCategoriesWithBoards() throws DBErrorException;
}
