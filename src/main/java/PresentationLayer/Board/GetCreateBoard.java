package PresentationLayer.Board;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.Role;
import Models.User;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import PresentationLayer.Command;
import Facades.CategoryFacade;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetCreateBoard extends Command {

    public GetCreateBoard(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Get categories
        try{
            CategoryFacade categoryFacade  = new CategoryFacade();
            List<Category> categories = categoryFacade.getAllCategories();
            request.setAttribute("categories", categories);
        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while getting categories from DB");
            return "createBoard";
        }

        return "createBoard";
    }
}
