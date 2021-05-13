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
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Policies;
import utils.ValidationUtils;
import utils.VerifyRecaptcha;

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
        //Escapes HTML tags
        String username = ValidationUtils.escapeUnsafeCharacters(request.getParameter("username"));
        String email = ValidationUtils.escapeUnsafeCharacters(request.getParameter("email"));
        String password = ValidationUtils.escapeUnsafeCharacters(request.getParameter("password"));
        String password1 = ValidationUtils.escapeUnsafeCharacters(request.getParameter("password1"));

        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        try {
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            if (!verify) {
                request.setAttribute("errMessage", "Please click the captcha.");
                return "register";
            }
        } catch (IOException e) {
            request.setAttribute("errMessage", "Something went wrong with the Captcha.");
            return "register";
        }

        ILoginFacade facade = new LoginFacade();
        if (!ValidationUtils.isPasswordValid(password)) {
            request.setAttribute("errMessage", Policies.getPasswordPolicy());
            return "register";
        }
        if (!password.equals(password1)) {
            request.setAttribute("errMessage", "Passwords do not match.");
            return "register";
        }
        if (facade.usernameExists(username)) {
            request.setAttribute("errMessage", "User by given username already exists.");
            return "register";
        }

        facade.createUser(username, email, password);

        String url = facade.createActivationUrl(request.getRequestURL().toString(), username);
        facade.sendActivationEmail(email, username, url);

        request.setAttribute("message", "User created successfully.");
        return "login";

    }

}
