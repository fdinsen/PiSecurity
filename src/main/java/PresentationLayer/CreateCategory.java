package PresentationLayer;

import Exceptions.DBErrorException;
import Exceptions.LoginSampleException;
import Exceptions.UserNotFoundException;
import Models.User;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateCategory extends Command {

    private static final long serialVersionUID = 1L;

    String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String categoryName = StringEscapeUtils.escapeHtml4(request.getParameter("name"));

        //Category name validation
        if (categoryName == null || categoryName.length() < 4) {
            request.setAttribute("errMsg", "Category name must be at least 4 chars");
            return "createCategory";
        }


        //Get user
        User user = null;
        try{
            HttpSession session = request.getSession();
            String userName = (String)session.getAttribute("userName");

            UserDaoImpl userDaoImpl  = new UserDaoImpl();
            user = userDaoImpl.getUserFromUsername(userName, em);
        }catch (DBErrorException | UserNotFoundException e) {
            request.setAttribute("errMsg", e.getMessage());
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
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while creating category");
        }

        return "createCategory";
    }
}
