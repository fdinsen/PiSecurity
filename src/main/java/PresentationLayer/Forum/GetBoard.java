package PresentationLayer.Forum;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Facades.ForumFacade;
import Models.Role;
import PresentationLayer.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.apache.log4j.Logger;

public class GetBoard extends Command {

    public GetBoard(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //Get board with threads
        String boardIdString = request.getParameter("boardId");
        request.setAttribute("boardId", boardIdString);

        try{
            ForumFacade forumFacade  = new ForumFacade();
            BoardDTO boardDTO = forumFacade.getBoardWithThreads(boardIdString);
            request.setAttribute("board", boardDTO);
        }catch (DBErrorException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not get board");
            request.setAttribute("errMessage", e.getMessage());
        }catch (Exception e){
            Logger.getLogger(this.getClass().getName()).error("Database error, could not get board");
            request.setAttribute("errMessage", "Something went wrong while getting board and threads");
        }

        return "board";
    }
}
