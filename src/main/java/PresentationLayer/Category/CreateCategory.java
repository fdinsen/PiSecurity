package PresentationLayer.Category;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.LoginSampleException;
import Exceptions.UserNotFoundException;
import Models.Role;
import Models.User;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import PresentationLayer.Command;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static utils.ValidationUtils.categoryNameValidation;

public class CreateCategory extends Command {
    public CreateCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String categoryName = StringEscapeUtils.escapeHtml4(request.getParameter("name"));

        //Category name validation
        try{
            categoryNameValidation(categoryName);
        }catch(InvalidInputException e){
            request.setAttribute("errMsg", e.getMessage());
            return "createCategory";
        }catch(Exception e){
            request.setAttribute("errMsg", "Something went wrong while creating category");
            return "createCategory";
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
            return "createCategory";
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while creating category");
            return "createCategory";
        }

        //Create
        try{
            CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
            categoryDaoImpl.createCategory(categoryName, user, em);
            request.setAttribute("message", "Category created successfully.");

        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
            return "createCategory";
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while creating category");
            return "createCategory";
        }

        return "viewCategories";
    }
}
