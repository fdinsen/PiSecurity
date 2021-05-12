package Persistence;

import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Models.Category;
import Models.User;
import Persistence.DAO.ICategoryDao;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {

    @Override
    public CategoryDTO createCategory(String name, User user, EntityManager em) throws DBErrorException {
        CategoryDTO categoryDTO = null;
        try{
            categoryDTO = getCategoryFromName(name, em);
        }catch (Exception e){
            throw new DBErrorException("Something went wrong while checking if category already exist");
        }finally {
            em.close();
        }

        if(categoryDTO != null){
            em.close();
            throw new DBErrorException("Category already exist");
        }

        try {
            em.getTransaction().begin();
            Category category = new Category();
            category.setName(name);
            category.setCreatedBy(user);
            em.persist(category);
            em.getTransaction().commit();

            return  new CategoryDTO(category);
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while creating category in DB");
        }finally {
            em.close();
        }
    }

    @Override
    public CategoryDTO getCategoryFromName(String catName,EntityManager em) throws DBErrorException {
        Category category = null;

        //find category in db
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.name = :catName", Category.class);
            query.setParameter("catName", catName);
            category = query.getSingleResult();

            return  new CategoryDTO(category);
        } catch (NoResultException nre){
            return null;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting category from category name");
        }finally {
            em.close();
        }
    }

    @Override
    public CategoryDTO getCategoryDTOFromID(int catId, EntityManager em) throws DBErrorException {
        //find category in db
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.id = :catId", Category.class);
            query.setParameter("catId", catId);
            Category category = query.getSingleResult();

            CategoryDTO categoryDTO = new CategoryDTO(category);

            return categoryDTO;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting category from id");
        }finally {
            em.close();
        }
    }

    @Override
    public Category getCategoryFromID(int catId,Boolean closeEM, EntityManager em) throws DBErrorException {
        Category category = null;

        //find category in db
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.id = :catId", Category.class);
            query.setParameter("catId", catId);
            category = query.getSingleResult();
            return category;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting category from id");
        }finally {
            if(closeEM) {
                em.close();
            }
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories(EntityManager em) throws DBErrorException {
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
            List<Category> categories = query.getResultList();

            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++) {
                categoryDTOS.add(new CategoryDTO(categories.get(i)));
            }

            return  categoryDTOS;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting all categories");
        }finally {
            em.close();
        }
    }

    @Override
    public List<CategoryDTO> getCategoriesWithBoards(EntityManager em) throws DBErrorException {
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c LEFT JOIN c.boards b", Category.class);
            List<Category> categories = query.getResultList();


            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++) {
                CategoryDTO categoryDTO = new CategoryDTO(categories.get(i));
                categoryDTOS.add(categoryDTO);
            }

            return  categoryDTOS;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting all categories");
        }finally {
            em.close();
        }
    }

    @Override
    public void deleteCategory(CategoryDTO categoryDTO, EntityManager em) throws DBErrorException {
        try {
            //Delete
            em.getTransaction().begin();

            //Get category from dto
            Category category = getCategoryFromID(categoryDTO.getId(),false, em);

            em.remove(category);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while deleting category");
        }finally {
            em.close();
        }

    }

    @Override
    public void editCategory(CategoryDTO categoryDTO, String name, User user, EntityManager em) throws DBErrorException {
        try {
            em.getTransaction().begin();
            //Get category from dto
            Category category = getCategoryFromID(categoryDTO.getId(),false, em);

            category.setName(name);
            category.setUpdatedBy(user);
            em.persist(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while editing category");
        }finally {
            em.close();
        }
    }

    @Override
    public void editCategory(Category categoryDTO, String name, User user, EntityManager em) throws DBErrorException {

    }
}
