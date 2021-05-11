/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.LoginSampleException;
import Models.Role;
import Persistence.CreateUserDaoImpl;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.EMF_Creator;
import org.apache.commons.text.StringEscapeUtils;
import utils.JWTHandling;
import utils.Policies;
import utils.ValidationUtils;

/**
 *
 * @author gamma
 */
public class Register extends Command {

    public Register(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String userName = ValidationUtils.escapeUnsafeCharacters(request.getParameter("username"));
        String email = ValidationUtils.escapeUnsafeCharacters(request.getParameter("email"));
        String password = ValidationUtils.escapeUnsafeCharacters(request.getParameter("password"));
        String password1 = ValidationUtils.escapeUnsafeCharacters(request.getParameter("password1"));

        if (!ValidationUtils.isPasswordValid(password)) {
            request.setAttribute("errMessage", Policies.getPasswordPolicy());
            return "register";
        }
        
        CreateUserDaoImpl createUserDao = new CreateUserDaoImpl();

        if (!password.equals(password1)) {
            request.setAttribute("errMessage", "Passwords do not match.");
            return "register";
        }
        if (createUserDao.usernameExists(userName, em)) {
            request.setAttribute("errMessage", "User by given username already exists.");
            return "register";
        }

        request.setAttribute("message", "User created successfully.");
        createUserDao.createUser(userName, email, password, em);
        
        String token = JWTHandling.createJWT(userName);
        //TODO Implement email
        request.setAttribute("errMessage", "http://localhost:8080/ValgfagBoilerPlateSecurity-1.0-SNAPSHOT/aut?t=" + token);
        
        return "login";

    }

}
