/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.Thread;

import Exceptions.InvalidInputException;
import Exceptions.LoginSampleException;
import Facades.Interfaces.IThreadFacade;
import Facades.ThreadFacade;
import Models.Role;
import PresentationLayer.Command;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.ValidationUtils;

/**
 *
 * @author gamma
 */
public class DeleteThread extends Command {

    public DeleteThread(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        HttpSession session = request.getSession();
        try {
            String threadIdString = ValidationUtils.escapeUnsafeCharacters(request.getParameter("threadId"));
            int threadId = ValidationUtils.idStringValidation(threadIdString);
            
            String username = (String) session.getAttribute("username");
            Role userRole = Role.valueOf((String) session.getAttribute("role"));

            IThreadFacade facade = new ThreadFacade();

            if (facade.isThreadOwnedByUser(threadId, username) 
                || userRole.equals(Role.moderator) 
                || userRole.equals(Role.admin)) 
            {
                facade.deleteThread(threadId);
                request.setAttribute("errMessage", "Thread " + threadId + " deleted.");
            }else {
                org.apache.log4j.Logger.getLogger(this.getClass().getName()).warn("User " + username + " is not authorized to delete thread!");
                request.setAttribute("errMessage", "You are not authorized to delete this thread.");
            }
        } catch (InvalidInputException ex) {
            org.apache.log4j.Logger.getLogger(this.getClass().getName()).error("Database error, could not delete thread");
            Logger.getLogger(DeleteThread.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errMessage", "Something went wrong while deleting thread");
        }
        return "board";
    }

}
