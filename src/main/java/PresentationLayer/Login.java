package PresentationLayer;

import Exceptions.LoginSampleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DTO.LoginDTO;
import DTO.UserDTO;
import Models.Role;
import Facades.Interfaces.ILoginFacade;
import Facades.LoginFacade;
import java.io.IOException;
import org.apache.log4j.Logger;
import utils.ValidationUtils;
import utils.VerifyRecaptcha;

public class Login extends Command {

    public Login(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        //Escapes HTML tags
        String email = ValidationUtils.escapeUnsafeCharacters(request.getParameter("email"));
        String password = ValidationUtils.escapeUnsafeCharacters(request.getParameter("password"));
        
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        try {
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            if(!verify) {
                request.setAttribute("errMessage", "Please click the captcha.");
                return "login";
            }
        }catch(IOException e) {
            request.setAttribute("errMessage", "Something went wrong with the Captcha.");
            return "login";
        }
        
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);
        ILoginFacade facade = new LoginFacade();
        try {
            UserDTO validatedUser = facade.verifyCredentials(loginDTO);

            HttpSession session = request.getSession(); //Creating a session
            session.setAttribute("role", validatedUser.getRole().toString());
            session.setAttribute("username", validatedUser.getUsername());
            session.setAttribute("email", validatedUser.getEmail());
            session.setAttribute("profilePicture", validatedUser.getProfilePicture());
            request.setAttribute("userName", validatedUser.getUsername());

            //TODO remove following if statements when deleting admin/editor/user test pages
            if (validatedUser.getRole().equals(Role.admin)) {

                session.setAttribute("Admin", validatedUser.getUsername()); //setting session attribute

                return "/WEB-INF/admin";
            }
            if (validatedUser.getRole().equals(Role.moderator)) {

                session.setAttribute("Editor", validatedUser.getUsername());

                return "/WEB-INF/editor";
            }
            if (validatedUser.getRole().equals(Role.user)) {

                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("User", validatedUser.getUsername());
                
                return "index";
                //return "/WEB-INF/user";
            }
            if (validatedUser.getRole().equals(Role.unverified)) {
                session.setAttribute("User", validatedUser.getUsername());
                return "/WEB-INF/user";
            }

            System.out.println("Error message = " + "Username or password is incorrect.");
            request.setAttribute("errMessage", "Username or password is incorrect.");
            
            return "login";

        } catch (NullPointerException e1) {
            e1.printStackTrace();
            request.setAttribute("errMessage", "Username or password is incorrect.");
            Logger.getLogger(this.getClass().getName()).warn("Login failed with email " + email);
                    
            return "login";
        }
    }
}
