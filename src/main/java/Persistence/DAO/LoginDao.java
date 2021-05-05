package Persistence.DAO;

import Login.LoginBean;
import Models.User;
import javax.persistence.EntityManager;

public interface LoginDao {
    public String verifyCredentials(LoginBean bean, EntityManager em);
}
