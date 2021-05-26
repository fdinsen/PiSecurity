package Facades;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Facades.Interfaces.IUserFacade;
import Persistence.UserDaoImpl;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UserFacade implements IUserFacade {

    private EntityManagerFactory EMF;

    public UserFacade() {
        EMF = EMF_Creator.createEntityManagerFactory();
    }

    @Override
    public String updateProfilePicture(String profilePicture, String username) throws DBErrorException, UserNotFoundException {
        EntityManager em = EMF_Creator.createEntityManagerFactory().createEntityManager();

        UserDaoImpl userDaoImpl = new UserDaoImpl();
        return userDaoImpl.updateProfilePicture(profilePicture, username, em);
    }
}
