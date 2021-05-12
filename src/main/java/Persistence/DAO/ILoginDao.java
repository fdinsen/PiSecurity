package Persistence.DAO;

import DTO.LoginDTO;
import Models.User;
import javax.persistence.EntityManager;

public interface ILoginDao {
    public User verifyCredentials(LoginDTO bean, EntityManager em);
}
