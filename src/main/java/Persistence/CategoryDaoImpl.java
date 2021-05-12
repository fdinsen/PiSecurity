package Persistence;

import Exceptions.DBErrorException;
import Models.Category;
import Models.User;
import Persistence.DAO.ICategoryDao;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {

    @Override
    public Category createCategory(String name, User user, EntityManager em) throws DBErrorException {
        Category category = null;
        try{
             category = getCategoryFromName(name, em);
        }catch (Exception e){
            throw new DBErrorException("Something went wrong while checking if category already exist");
        }

        if(category != null){
            throw new DBErrorException("Category already exist");
        }

        try {
            em.getTransaction().begin();
            category = new Category();
            category.setName(name);
            category.setCreatedBy(user);
            em.persist(category);
            em.getTransaction().commit();
            return category;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while creating category in DB");
        }
    }

    @Override
    public Category getCategoryFromName(String catName,EntityManager em) throws DBErrorException {
        Category category = null;

        //find category in db
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.name = :catName", Category.class);
            query.setParameter("catName", catName);
            category = query.getSingleResult();
            return  category;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting category from category name");
        }
    }

    @Override
    public Category getCategoryFromID(int catId, EntityManager em) throws DBErrorException {
        Category category = null;

        //find category in db
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.id = :catId", Category.class);
            query.setParameter("catId", catId);
            category = query.getSingleResult();
            return  category;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting category from id");
        }
    }

    @Override
    public List<Category> getAllCategories(EntityManager em) throws DBErrorException {
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
            List<Category> categories = query.getResultList();
            return  categories;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting all categories");
        }
    }

    @Override
    public List<Category> getCategoriesWithBoardsAndCount(EntityManager em) throws DBErrorException {
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c LEFT JOIN c.boards b", Category.class);
            List<Category> categories = query.getResultList();
            return  categories;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting all categories");
        }
    }

    @Override
    public boolean deleteCategory(Category category, EntityManager em) throws DBErrorException {
        try {
            //Delete
            em.getTransaction().begin();
            em.remove(category);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while deleting category");
        }

    }

    @Override
    public Category editCategory(Category category, String name,User user, EntityManager em) throws DBErrorException {
        try {
            em.getTransaction().begin();
            category.setName(name);
            category.setUpdatedBy(user);
            em.persist(category);
            em.getTransaction().commit();
            return category;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while editing category");
        }
    }
}
