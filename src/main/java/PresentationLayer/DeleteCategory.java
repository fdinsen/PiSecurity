package PresentationLayer;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
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

public class DeleteCategory extends Command {
    public DeleteCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();


        //Escapes HTML
        int catId;
        try{
            catId = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.getParameter("catId")));

        }catch (NumberFormatException e){
            request.setAttribute("errMsg", "Category ID, has to be a integer");
            return "viewCategories";
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while trying to delete category");
            return "viewCategories";
        }

        //Delete
        try{
            CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
            categoryDaoImpl.deleteCategory(catId, em);

            request.setAttribute("message", "Category deleted");
        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while deleting category");
        }

        return "viewCategories";
    }
}
