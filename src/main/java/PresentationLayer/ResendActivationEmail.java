/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.LoginSampleException;
import Models.Role;
import Facades.Interfaces.ILoginFacade;
import Facades.LoginFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gamma
 */
public class ResendActivationEmail extends Command {

    public ResendActivationEmail(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        String username = (String) request.getSession().getAttribute("username");
        String email = (String) request.getSession().getAttribute("email");
        
        ILoginFacade facade = new LoginFacade();
        
        String url = facade.createActivationUrl(request.getRequestURL().toString(), username);
        facade.sendActivationEmail(email, username, url);
        
        request.setAttribute("emailMsg", "An email with an activation link has been sent to your email.");
        return "index";
    }
    
}
