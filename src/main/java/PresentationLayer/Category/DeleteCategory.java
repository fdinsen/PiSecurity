package PresentationLayer.Category;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Models.Role;
import PresentationLayer.Command;
import Facades.CategoryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DeleteCategory extends Command {

    public DeleteCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //Delete
        try {
            String catIdString = request.getParameter("catId");

            CategoryFacade categoryFacade = new CategoryFacade();
            categoryFacade.deleteCategory(catIdString);
            request.setAttribute("message", "Category deleted");
        } catch (DBErrorException | InvalidInputException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not delete category");
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not delete category");
            request.setAttribute("errMsg", "Something went wrong while deleting category");
        }

        return "viewCategories";
    }
}
