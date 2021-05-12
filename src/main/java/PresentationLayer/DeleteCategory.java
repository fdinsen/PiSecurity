package PresentationLayer;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.Role;
import Models.User;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static utils.ValidationUtils.categoryIdStringValidation;

public class DeleteCategory extends Command {
    public DeleteCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();


        //Escapes HTML
        String catIdString = StringEscapeUtils.escapeHtml4(request.getParameter("catId"));

        //categoryId Validation
        Category category;
        try{
            category = categoryIdStringValidation(catIdString, em);
        }catch(InvalidInputException e){
            request.setAttribute("errMsg", e.getMessage());
            return "viewCategories";
        }catch(Exception e){
            request.setAttribute("errMsg", "Something went wrong while deleting category");
            return "viewCategories";
        }

        //Delete
        try{
            CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
            categoryDaoImpl.deleteCategory(category, em);
            request.setAttribute("message", "Category deleted");
        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while deleting category");
        }

        return "viewCategories";
    }
}
