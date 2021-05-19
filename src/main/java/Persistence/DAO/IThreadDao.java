package Persistence.DAO;

import DTO.BoardDTO;
import DTO.ThreadDTO;
import Exceptions.DBErrorException;
import Models.Thread;
import Models.User;

import javax.persistence.EntityManager;

/**
 *
 * @author Oliver
 */
public interface IThreadDao {
    public int createThread(String name, String text, int boardId, User user, EntityManager em) throws DBErrorException;

    public void updateThread(ThreadDTO threadDTO, User user, EntityManager em) throws DBErrorException;

    public void deleteThread(ThreadDTO threadDTO, EntityManager em) throws DBErrorException;

    public ThreadDTO getThreadDTOById(int Id,Boolean closeEM, Boolean updateViewCount, EntityManager em) throws DBErrorException;

    public Thread getThreadById(int Id, Boolean closeEM, Boolean updateViewCount, EntityManager em) throws DBErrorException;

    public ThreadDTO getThreadWithPost(int Id, EntityManager em) throws DBErrorException;
}
