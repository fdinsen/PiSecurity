package Persistence.DAO;

import DTO.LoginDTO;
import DTO.UserDTO;
import javax.persistence.EntityManager;

public interface ILoginDao {
    public UserDTO verifyCredentials(LoginDTO bean, EntityManager em);
}
