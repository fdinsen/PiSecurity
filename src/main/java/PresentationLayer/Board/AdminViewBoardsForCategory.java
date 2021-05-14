package PresentationLayer.Board;

import DTO.BoardDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Facades.BoardFacade;
import Models.Role;
import PresentationLayer.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class AdminViewBoardsForCategory extends Command {
    public AdminViewBoardsForCategory(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            String catIdString = request.getParameter("catId");

            BoardFacade boardFacade = new BoardFacade();
            List<BoardDTO> boardDTOS = boardFacade.getBoardsForCategory(catIdString);

            request.setAttribute("catId", catIdString);
            request.setAttribute("boards", boardDTOS);
        }catch (DBErrorException | InvalidInputException e) {
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e) {
            request.setAttribute("errMsg", "Something went wrong while getting boards for category");
        }

        return "AdminViewBoardsForCategory";
    }
}
