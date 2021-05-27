package Persistence;

import Persistence.DAO.ILoginDao;
import DTO.LoginDTO;
import DTO.UserDTO;
import Models.Role;
import Models.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class LoginDaoImpl implements ILoginDao {

    @Override
    public UserDTO verifyCredentials(LoginDTO loginDTO, EntityManager em) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

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
                return new UserDTO(user);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not authorize user " + loginDTO.getEmail());
            e.printStackTrace();
        }
        return null;
    }
    
    private boolean validatePassword(String originalPassword, String storedPassword) {
        return BCrypt.checkpw(originalPassword, storedPassword);
    }
    
    
}
