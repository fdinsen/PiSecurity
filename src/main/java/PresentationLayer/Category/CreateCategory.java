package PresentationLayer.Category;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.Role;
import PresentationLayer.Command;
import Service.CategoryFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateCategory extends Command {
    public CreateCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            String categoryName = request.getParameter("name");
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");

            CategoryFacade categoryFacade = new CategoryFacade();
            categoryFacade.createCategory(categoryName, username);
            return "viewCategories";
        }catch(InvalidInputException | UserNotFoundException | DBErrorException e){
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e) {
            request.setAttribute("errMsg", "Something went wrong while creating category");
        }
        return "createCategory";
    }
}
