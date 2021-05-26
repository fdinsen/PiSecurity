package PresentationLayer.Board;

import DTO.BoardDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Facades.BoardFacade;
import Facades.CategoryFacade;
import Models.Role;
import PresentationLayer.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.apache.log4j.Logger;

public class DeleteBoard extends Command {

    public DeleteBoard(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String catIdString = request.getParameter("catId");
        String boardIdString = request.getParameter("boardId");

        //Delete
        try {
            BoardFacade boardFacade = new BoardFacade();
            boardFacade.deleteBoard(boardIdString);

            request.setAttribute("catId", catIdString);
            request.setAttribute("message", "Board deleted");
        } catch (DBErrorException | InvalidInputException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not delete board");
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not delete board");
            request.setAttribute("errMsg", "Something went wrong while deleting board");
        }

        return "AdminViewBoardsForCategory";
    }
}
