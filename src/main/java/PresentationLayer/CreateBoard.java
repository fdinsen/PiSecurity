package PresentationLayer;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.Role;
import Models.User;
import Persistence.BoardsDaoImpl;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateBoard extends Command {
    public CreateBoard(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String boardName = StringEscapeUtils.escapeHtml4(request.getParameter("name"));
        String description = StringEscapeUtils.escapeHtml4(request.getParameter("description"));

        //Board name validation
        if (boardName == null || boardName.length() < 4) {
            request.setAttribute("errMsg", "Category name must be at least 4 chars");
            return "createBoard";
        }

        //description name validation
        if (description == null || description.length() < 10) {
            request.setAttribute("errMsg", "Description must be at least 10 chars");
            return "createBoard";
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
            return "createBoard";
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while creating board");
            return "createBoard";
        }

        //Get category
        Category category = null;
        try{
            HttpSession session = request.getSession();
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            CategoryDaoImpl categoryDaoImpl  = new CategoryDaoImpl();
            category = categoryDaoImpl.getCategoryFromID(categoryId, em);
        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
            return "createBoard";
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while creating board");
            return "createBoard";
        }

        //Create
        try{
            BoardsDaoImpl boardsDaoImpl = new BoardsDaoImpl();
            boardsDaoImpl.createBoard(boardName,description, category, user, em);
            request.setAttribute("message", "Board created successfully.");

        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while creating board");
        }

        return "createBoard";
    }
}
