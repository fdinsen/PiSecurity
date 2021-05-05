/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import Exceptions.LoginSampleException;
import Persistence.CreateUserDaoImpl;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.EMF_Creator;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author gamma
 */
public class Register extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String userName = StringEscapeUtils.escapeHtml4(request.getParameter("username"));
        String password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
        String password1 = StringEscapeUtils.escapeHtml4(request.getParameter("password1"));

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
        createUserDao.createUser(userName, password, em);
        return "login";

    }

}
