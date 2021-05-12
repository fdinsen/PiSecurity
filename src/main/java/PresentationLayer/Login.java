package PresentationLayer;

import Exceptions.LoginSampleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DTO.LoginDTO;
import Models.Role;
import Models.User;
import Persistence.LoginDaoImpl;
import javax.persistence.EntityManager;
import utils.EMF_Creator;
import utils.ValidationUtils;

public class Login extends Command {

    public Login(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String email = ValidationUtils.escapeUnsafeCharacters(request.getParameter("email"));
        String password = ValidationUtils.escapeUnsafeCharacters(request.getParameter("password"));

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        LoginDaoImpl loginDao = new LoginDaoImpl();

        try {
            User validatedUser = loginDao.verifyCredentials(loginDTO, em);

            HttpSession session = request.getSession(); //Creating a session
            session.setAttribute("role", validatedUser.getRole().toString());
            session.setAttribute("username", validatedUser.getUsername());
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

                return "/WEB-INF/user";
            }
            if(validatedUser.getRole().equals(Role.unverified)) {
                session.setAttribute("User", validatedUser.getUsername());
                return "/WEB-INF/user";
            }

            System.out.println("Error message = " + "Username or password is incorrect.");
            request.setAttribute("errMessage", "Username or password is incorrect.");

            return "login";

        } catch (NullPointerException e1) {
            e1.printStackTrace();
            request.setAttribute("errMessage", "Username or password is incorrect.");
            return "login";
        }
    }
}
