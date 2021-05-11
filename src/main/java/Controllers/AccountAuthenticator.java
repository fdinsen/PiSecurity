package Controllers;

import Persistence.AuthenticateUserDaoImpl;
import Persistence.DAO.IAuthenticateUserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.persistence.EntityManager;
import utils.EMF_Creator;


/*
 This servlet is a general servlet. You should create a servles for each type of requests you have.
 */
@WebServlet(name = "Authenticator", urlPatterns = {"/aut"})
public class AccountAuthenticator extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("t");
        IAuthenticateUserDao auth = new AuthenticateUserDaoImpl();
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();
        if(auth.authenticateUser(token, em)) {
            request.setAttribute("errMessage", "You have successfully confirmed your email! You can now use the forum!");
        }else {
            request.setAttribute("errMessage", "An error occurred. The activation link might have expired.");
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
