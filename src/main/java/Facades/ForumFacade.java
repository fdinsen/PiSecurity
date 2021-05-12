package Facades;

import Exceptions.DBErrorException;
import Models.Category;
import Persistence.CategoryDaoImpl;
import Facades.Interfaces.IForumFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import java.util.List;

public class ForumFacade implements IForumFacade {
    @Override
    public List<Category> getCategoriesWithBoards() throws DBErrorException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Get categories
        CategoryDaoImpl categoryDaoImpl  = new CategoryDaoImpl();
        return categoryDaoImpl.getCategoriesWithBoards(em);
    }
}
