package Exceptions;

/**
 * The purpose of DBErrorException is to be used when something goes wrong while working on DB
 * @author kasper
 */
public class DBErrorException extends Exception {

    public DBErrorException(String msg) {
        super(msg);
    }
    

}
