package Facades.Interfaces;

import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;

public interface IThreadFacade {
    public int createThread(String name, String text, String boardId, String createdByUsername) throws DBErrorException, UserNotFoundException, InvalidInputException;

}
