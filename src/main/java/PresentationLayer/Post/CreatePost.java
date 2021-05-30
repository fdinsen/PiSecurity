/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer.Post;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.LoginSampleException;
import Exceptions.UserNotFoundException;
import Facades.Interfaces.IPostFacade;
import Facades.PostFacade;
import Models.Role;
import PresentationLayer.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import utils.ValidationUtils;

/**
 *
 * @author gamma
 */
public class CreatePost extends Command{

    public CreatePost(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        String threadId = request.getParameter("threadId");
        request.setAttribute("threadId", threadId);
        
        try {
            String postText = ValidationUtils.threadTextValidation(request.getParameter("text"));
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            
            IPostFacade postFacade = new PostFacade();
            postFacade.createPost(postText, threadId, username);
            request.setAttribute("message", "Post created");
        }catch (DBErrorException | UserNotFoundException | InvalidInputException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, create thread");
            request.setAttribute("errMessage", e.getMessage());
            return "createPost";
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, create thread");
            request.setAttribute("errMessage", "Something went wrong while creating thread");
            return "createPost";
        }

        return "createPost";
    }
    
}
