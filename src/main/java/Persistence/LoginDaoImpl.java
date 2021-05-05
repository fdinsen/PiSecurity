package Persistence;

import Dependencies.MysqlConnection;
import Persistence.DAO.LoginDao;
import Login.LoginBean;
import Models.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.security.crypto.bcrypt.BCrypt;
import utils.EMF_Creator;

public class LoginDaoImpl implements LoginDao {

    public String verifyCredentials(LoginBean loginBean, EntityManager em) {
        String userName = loginBean.getUserName();
        String password = loginBean.getPassword();

        User user = null;
        String userNameDB = "";
        String passwordDB = "";
        String roleDB = "";

        em.getTransaction().begin();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :uname", User.class);
        query.setParameter("uname", userName);

        try {
            user = query.getSingleResult();
            userNameDB = user.getUsername();
            passwordDB = user.getPassword();
            roleDB = user.getRole();
            
            if (userName.equals(userNameDB) && validatePassword(password, passwordDB) && roleDB.equals("Admin")) {
                return "Admin_Role";
            } else if (userName.equals(userNameDB) && validatePassword(password, passwordDB) && roleDB.equals("Editor")) {
                return "Editor_Role";
            } else if (userName.equals(userNameDB) && validatePassword(password, passwordDB) && roleDB.equals("User")) {
                return "User_Role";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Invalid user credentials";
    }
    
    private boolean validatePassword(String originalPassword, String storedPassword) {
        return BCrypt.checkpw(originalPassword, storedPassword);
    }
    
    
}
