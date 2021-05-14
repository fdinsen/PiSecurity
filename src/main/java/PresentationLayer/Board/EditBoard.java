package PresentationLayer.Board;

import DTO.BoardDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Facades.BoardFacade;
import Models.Role;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class EditBoard extends Command {
    public EditBoard(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String boardIdString = request.getParameter("boardId");
        String catIdString = request.getParameter("catId");
        String beginEditString = request.getParameter("beginEdit");
        String boardName = request.getParameter("name");
        String description = request.getParameter("description");

        try{
            //Get username
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");

            //Setup for
            request.setAttribute("catId", catIdString);
            request.setAttribute("editBoardId", boardIdString);
            request.setAttribute("editing", true);
            request.setAttribute("boardName", boardName);
            request.setAttribute("description", description);

            //Edit
            BoardFacade boardFacade = new BoardFacade();
            Boolean beginEdit = boardFacade.editBoard(boardIdString,boardName,description,username,beginEditString);

            request.setAttribute("editing", beginEdit);
        } catch (UserNotFoundException | InvalidInputException | DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while updating board");
        }

        try{
            //Get boards
            BoardFacade boardFacade = new BoardFacade();
            List<BoardDTO> boardDTOS = boardFacade.getBoardsForCategory(catIdString);
            request.setAttribute("boards", boardDTOS);
        }catch (InvalidInputException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (DBErrorException e) {
            request.setAttribute("errMsg", "Something went wrong while getting boards, try to reload");
        }
        return "AdminViewBoardsForCategory";
    }
}
