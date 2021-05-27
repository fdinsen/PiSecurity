/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.Thread;

import DTO.ThreadDTO;
import Exceptions.DBErrorException;
import Exceptions.LoginSampleException;
import Facades.ThreadFacade;
import Models.Role;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author gamma
 */
public class GetThread extends Command {

    public GetThread(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        String threadId = request.getParameter("threadId");
        request.setAttribute("threadId", threadId);

        try {
            ThreadFacade facade = new ThreadFacade();
            ThreadDTO threadDTO = facade.getThread(threadId);
            request.setAttribute("thread", threadDTO);
            return "thread";
        } catch (DBErrorException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not get thread");
            request.setAttribute("errMsg", e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not get thread");
            request.setAttribute("errMsg", "Something went wrong while getting board and threads");
        }
        return "thread";
    }

}
