package Persistence;

import Persistence.DAO.ILoginDao;
import Login.LoginBean;
import Models.Role;
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

public class LoginDaoImpl implements ILoginDao {

    @Override
    public User verifyCredentials(LoginBean loginBean, EntityManager em) {
        String email = loginBean.getEmail();
        String password = loginBean.getPassword();

        User user = null;
        String emailDB = "";
        String passwordDB = "";
        Role roleDB = null;

        em.getTransaction().begin();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);

        try {
            user = query.getSingleResult();
            emailDB = user.getEmail();
            passwordDB = user.getPassword();
            roleDB = user.getRole();
            
            if (email.equals(emailDB) && validatePassword(password, passwordDB)) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private boolean validatePassword(String originalPassword, String storedPassword) {
        return BCrypt.checkpw(originalPassword, storedPassword);
    }
    
    
}
