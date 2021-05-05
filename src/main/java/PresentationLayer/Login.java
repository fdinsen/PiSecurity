package PresentationLayer;

import Exceptions.LoginSampleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Login.LoginBean;
import Persistence.LoginDaoImpl;

import java.io.IOException;
import javax.persistence.EntityManager;
import utils.EMF_Creator;
import org.apache.commons.text.StringEscapeUtils;

public class Login extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        //Escapes HTML tags
        String userName = StringEscapeUtils.escapeHtml4(request.getParameter("username"));
        String password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));

        LoginBean loginBean = new LoginBean();

        loginBean.setUserName(userName);
        loginBean.setPassword(password);

        LoginDaoImpl loginDao = new LoginDaoImpl();

        try {
            String userValidate = loginDao.verifyCredentials(loginBean, em);

            if (userValidate.equals("Admin_Role")) {
                System.out.println("Admin's Home");

                HttpSession session = request.getSession(); //Creating a session
                session.setAttribute("Admin", userName); //setting session attribute
                request.setAttribute("userName", userName);

                return "/WEB-INF/admin";
            }
            if (userValidate.equals("Editor_Role")) {
                System.out.println("Editor's Home");

                HttpSession session = request.getSession();
                session.setAttribute("Editor", userName);
                request.setAttribute("userName", userName);

                return "/WEB-INF/editor";
            }
            if (userValidate.equals("User_Role")) {
                System.out.println("User's Home");

                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("User", userName);
                request.setAttribute("userName", userName);

                return "/WEB-INF/user";
            }

            System.out.println("Error message = " + userValidate);
            request.setAttribute("errMessage", userValidate);

            return "login";

        } catch (Exception e1) {
            e1.printStackTrace();
            return "login";
        }
    } //End of doPost()
}
