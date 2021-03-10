package Persistence.DAO;

import Login.LoginBean;
import Models.User;

public interface LoginDao {
    public String verifyCredentials(LoginBean bean);
}
