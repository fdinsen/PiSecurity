package PresentationLayer.Category;

import Exceptions.DBErrorException;
import Models.Category;
import Models.Role;
import Persistence.CategoryDaoImpl;
import PresentationLayer.Command;
import Facades.CategoryFacade;
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

        //Get categories
        try{
            CategoryFacade categoryFacade  = new CategoryFacade();
            List<Category> categories = categoryFacade.getAllCategories();
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
