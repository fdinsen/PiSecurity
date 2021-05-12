package PresentationLayer.Category;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Models.Role;
import PresentationLayer.Command;
import Facades.CategoryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCategory extends Command {
    public DeleteCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //Delete
        try{
            String catIdString = request.getParameter("catId");

            CategoryFacade categoryFacade = new CategoryFacade();
            categoryFacade.deleteCategory(catIdString);
            request.setAttribute("message", "Category deleted");
        }catch (DBErrorException | InvalidInputException e) {
            request.setAttribute("errMsg", e.getMessage());
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while deleting category");
        }

        return "viewCategories";
    }
}
