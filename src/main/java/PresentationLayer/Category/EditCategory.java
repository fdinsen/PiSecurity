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
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML
        int catId;
        String beginEdit = StringEscapeUtils.escapeHtml4(request.getParameter("beginEdit"));
        String catIdString = StringEscapeUtils.escapeHtml4(request.getParameter("catId"));
        String categoryName = StringEscapeUtils.escapeHtml4(request.getParameter("name"));


        //Setup for errors
        request.setAttribute("editing", true);
        request.setAttribute("editCatId", catIdString);
        request.setAttribute("categoryName", categoryName);

        //categoryId Validation
        Category category;
        try{
            category = categoryIdStringValidation(catIdString, em);
        }catch(InvalidInputException e){
            request.setAttribute("errMsg", e.getMessage());
            return "createCategory";
        }catch(Exception e){
            request.setAttribute("errMsg", "Something went wrong while updating category");
            return "createCategory";
        }

        //Category name validation
        try{
            categoryNameValidation(categoryName);
        }catch(InvalidInputException e){
            request.setAttribute("errMsg", e.getMessage());
            return "createCategory";
        }catch(Exception e){
            request.setAttribute("errMsg", "Something went wrong while updating category");
            return "createCategory";
        }

        //First click on edit
        if(beginEdit != null && beginEdit.equals("1")) {
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
            categoryDaoImpl.editCategory(category,categoryName, user, em);

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
