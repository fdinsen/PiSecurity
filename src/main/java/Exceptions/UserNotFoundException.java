package Exceptions;

/**
 * The purpose of UserNotFoundException is to be used when a user cannot be found in DB
 * @author kasper
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg) {
        super(msg);
    }
    

}
