package PresentationLayer.Category;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.Role;
import PresentationLayer.Command;
import Facades.CategoryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class CreateCategory extends Command {

    public CreateCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String categoryName = request.getParameter("name");
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");

            CategoryFacade categoryFacade = new CategoryFacade();
            categoryFacade.createCategory(categoryName, username);
            return "viewCategories";
        } catch (InvalidInputException | UserNotFoundException | DBErrorException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not create category");
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not create category");
            request.setAttribute("errMsg", "Something went wrong while creating category");
        }
        return "createCategory";
    }
}
