/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.LoginSampleException;
import Models.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.JWTHandling;

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
        String token = JWTHandling.createJWT(username);
        
        //TODO implement email
        request.setAttribute("errMessage", "http://localhost:8080/ValgfagBoilerPlateSecurity-1.0-SNAPSHOT/aut?t=" + token);
        return "index";
    }
    
}
