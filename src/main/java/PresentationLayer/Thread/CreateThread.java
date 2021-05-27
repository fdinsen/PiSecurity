package PresentationLayer.Thread;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Facades.BoardFacade;
import Facades.ThreadFacade;
import Models.Role;
import PresentationLayer.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class CreateThread extends Command {
    public CreateThread(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String boardId = request.getParameter("boardId");
        request.setAttribute("boardId", boardId);

        try{
            String threadName = request.getParameter("name");
            String threadText = request.getParameter("text");
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");

            ThreadFacade threadFacade = new ThreadFacade();
            threadFacade.createThread(threadName,threadText,boardId,username);
            request.setAttribute("message", "Thread created");
        }catch (DBErrorException | UserNotFoundException | InvalidInputException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, create thread");
            request.setAttribute("errMessage", e.getMessage());
            return "createThread";
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, create thread");
            request.setAttribute("errMessage", "Something went wrong while creating thread");
            return "createThread";
        }

        return "createThread";
    }
}
