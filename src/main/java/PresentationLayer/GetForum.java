package PresentationLayer;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.User;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetForum extends Command {

    private static final long serialVersionUID = 1L;

    String execute(HttpServletRequest request, HttpServletResponse response) {
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
