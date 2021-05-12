package PresentationLayer.Board;

import Exceptions.DBErrorException;
import Models.Category;
import Models.Role;
import Persistence.CategoryDaoImpl;
import PresentationLayer.Command;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewCategories extends Command {

    public ViewCategories(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Get categories
        try{
            CategoryDaoImpl categoryDaoImpl  = new CategoryDaoImpl();
            List<Category> categories = categoryDaoImpl.getAllCategories(em);
            request.setAttribute("categories", categories);
        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while getting categories from DB");
            return "index";
        }

        return "index";
    }
}
