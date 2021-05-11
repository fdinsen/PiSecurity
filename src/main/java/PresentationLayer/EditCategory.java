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

public class EditCategory extends Command {
    public EditCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();


        //Escapes HTML
        int catId;
        String categoryName;
        try{
            String beginEdit = StringEscapeUtils.escapeHtml4(request.getParameter("beginEdit"));
            catId = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.getParameter("catId")));
            categoryName = StringEscapeUtils.escapeHtml4(request.getParameter("name"));

            //Setup for errors
            request.setAttribute("editing", true);
            request.setAttribute("editCatId", catId);
            request.setAttribute("categoryName", categoryName);

            if(beginEdit != null && beginEdit.equals("1")) {
                return "viewCategories";
            }

        }catch (NumberFormatException e){
            request.setAttribute("errMsg", "Category ID, has to be a integer");
            return "viewCategories";
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while updating category");
            return "viewCategories";
        }


        //Category name validation
        if (categoryName == null || categoryName.length() < 4) {
            request.setAttribute("errMsg", "Category name must be at least 4 chars");
            return "viewCategories";
        }

        //Get user
        User user = null;
        try{
            HttpSession session = request.getSession();
            String userName = (String)session.getAttribute("username");

            UserDaoImpl userDaoImpl  = new UserDaoImpl();
            user = userDaoImpl.getUserFromUsername(userName, em);
        }catch (DBErrorException | UserNotFoundException e) {
            request.setAttribute("errMsg", e.getMessage());
            return "viewCategories";
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while updating category");
            return "viewCategories";
        }

        //Create
        try{
            CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
            categoryDaoImpl.editCategory(catId,categoryName, user, em);

            request.setAttribute("editing", true);
            request.setAttribute("editCatId", null);
            request.setAttribute("categoryName", null);
            request.setAttribute("message", "Category updated successfully.");
        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while updating category");
        }

        return "viewCategories";
    }
}
