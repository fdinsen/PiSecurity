package Persistence.DAO;

import Login.LoginBean;
import Models.Role;
import Models.User;
import javax.persistence.EntityManager;

public interface ILoginDao {
    public User verifyCredentials(LoginBean bean, EntityManager em);
}
