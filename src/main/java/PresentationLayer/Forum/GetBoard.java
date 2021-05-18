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
            request.setAttribute("errMsg", e.getMessage());
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while getting board and threads");
        }

        return "board";
    }
}
