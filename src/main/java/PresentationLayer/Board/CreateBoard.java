package PresentationLayer.Board;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Models.Category;
import Models.Role;
import Models.User;
import Persistence.BoardsDaoImpl;
import Persistence.CategoryDaoImpl;
import Persistence.UserDaoImpl;
import PresentationLayer.Command;
import Facades.BoardFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import static utils.ValidationUtils.*;

public class CreateBoard extends Command {

    public CreateBoard(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String catIdString = request.getParameter("catId");
        request.setAttribute("catId", catIdString);

        try {
            String boardName = request.getParameter("name");
            String description = request.getParameter("description");
            String categoryIdString = request.getParameter("categoryId");
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");

            BoardFacade boardFacade = new BoardFacade();
            boardFacade.createBoard(boardName, description, categoryIdString, username);
            request.setAttribute("message", "Board created");
        } catch (DBErrorException | UserNotFoundException | InvalidInputException e) {
            request.setAttribute("errMsg", e.getMessage());
            Logger.getLogger(this.getClass().getName()).error("Database error, could not create board");
            return "createBoard";
        } catch (Exception e) {
            request.setAttribute("errMsg", "Something went wrong while creating board");
            Logger.getLogger(this.getClass().getName()).error("Database error, could not create board");
            return "createBoard";
        }

        return "AdminViewBoardsForCategory";
    }
}
