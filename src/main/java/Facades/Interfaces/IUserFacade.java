package Facades.Interfaces;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;

public interface IUserFacade {
    public String updateProfilePicture(String profilePicture, String username) throws DBErrorException, UserNotFoundException;
}
