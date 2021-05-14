package PresentationLayer.Category;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
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

import static utils.ValidationUtils.categoryIdStringValidation;
import static utils.ValidationUtils.categoryNameValidation;

public class EditCategory extends Command {
    public EditCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            String beginEditString = request.getParameter("beginEdit");
            String catIdString = request.getParameter("catId");
            String categoryName = request.getParameter("name");

            //Get username
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");

            //Setup for errors
            request.setAttribute("editing", true);
            request.setAttribute("editCatId", catIdString);
            request.setAttribute("categoryName", categoryName);

            CategoryFacade categoryFacade = new CategoryFacade();
            Boolean beginEdit = categoryFacade.editCategory(catIdString,categoryName,username,beginEditString);
            request.setAttribute("editing", beginEdit);
        } catch (UserNotFoundException | InvalidInputException | DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while updating category");
        }
        return "viewCategories";
    }
}
