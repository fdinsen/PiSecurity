package Facades;

import DTO.LoginDTO;
import DTO.UserDTO;
import Dependencies.EmailConnection;
import Persistence.CreateUserDaoImpl;
import Persistence.DAO.ICreateUserDao;
import Persistence.DAO.ILoginDao;
import Persistence.LoginDaoImpl;
import Facades.Interfaces.ILoginFacade;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;
import utils.JWTHandling;

public class LoginFacade implements ILoginFacade {

    private ILoginDao ldi;
    private ICreateUserDao cdi;
    private EntityManagerFactory EMF;

    public LoginFacade() {
        ldi = new LoginDaoImpl();
        cdi = new CreateUserDaoImpl();
        EMF = EMF_Creator.createEntityManagerFactory();
    }

    @Override
    public UserDTO verifyCredentials(LoginDTO user) {
        return ldi.verifyCredentials(user, EMF.createEntityManager());
    }
    
    @Override
    public boolean usernameExists(String userName) {
        return cdi.usernameExists(userName, EMF.createEntityManager());
    }
    
    @Override
    public UserDTO createUser(String username, String email, String password) {
        return cdi.createUser(username, email, password, EMF.createEntityManager());
    }

    @Override
    public String createActivationUrl(String requestUrl, String username) {
        String token = JWTHandling.createJWT(username);
        StringBuilder url = new StringBuilder(requestUrl);
        int indexOfFrontController = url.indexOf("FrontController");
        return url.replace(indexOfFrontController, url.length(), "aut?t=" + token).toString();
    }
    
    @Override
    public void sendActivationEmail(String email, String username, String url) {
        EmailConnection emailCon = new EmailConnection();
        emailCon.sendEmail(email, username, url.toString());
    }
}
