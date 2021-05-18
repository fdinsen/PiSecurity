package Persistence;

import DTO.BoardDTO;
import DTO.ThreadDTO;
import Exceptions.DBErrorException;
import Models.Board;
import Models.Thread;
import Models.User;
import Persistence.DAO.IThreadDao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ThreadDaoImpl implements IThreadDao {

    @Override
    public int createThread(String name, String text, BoardDTO boardDTO, User user, EntityManager em) throws DBErrorException {
        try {
            em.getTransaction().begin();
            Thread thread = new Thread();
            thread.setName(name);
            BoardsDaoImpl boardsDao = new BoardsDaoImpl();
            Board board = boardsDao.getBoardFromID(boardDTO.getId(), false, em);
            thread.setBoard(board);
            thread.setText(text);
            thread.setCreatedBy(user);
            em.persist(thread);
            em.getTransaction().commit();

            return thread.getId();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while creating thread in DB");
        }finally {
            em.close();
        }
    }

    @Override
    public void updateThread(ThreadDTO threadDTO, User user, EntityManager em) throws DBErrorException {
        try {
            em.getTransaction().begin();
            //Get category from dto
            Thread thread = getThreadById(threadDTO.getId(),false,false, em);

            thread.setName(threadDTO.getName());
            thread.setText(threadDTO.getText());
            thread.setUpdatedBy(user);
            em.persist(thread);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while editing thread");
        }finally {
            em.close();
        }
    }

    @Override
    public void deleteThread(ThreadDTO threadDTO, EntityManager em) throws DBErrorException {
        try {
            //Delete
            em.getTransaction().begin();

            //Get category from dto
            Thread thread = getThreadById(threadDTO.getId(),false,false, em);

            em.remove(thread);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while deleting thread");
        }finally {
            em.close();
        }
    }

    @Override
    public ThreadDTO getThreadDTOById(int threadId, Boolean closeEM, Boolean updateViewCount, EntityManager em) throws DBErrorException {
        return new ThreadDTO(getThreadById(threadId,closeEM,updateViewCount,em));
    }

    @Override
    public Thread getThreadById(int threadId,Boolean closeEM, Boolean updateViewCount, EntityManager em) throws DBErrorException {
        //find thread in db
        try {
            TypedQuery<Thread> query = em.createQuery("SELECT t FROM Thread t WHERE t.id = :threadId", Thread.class);
            query.setParameter("threadId", threadId);
            Thread thread = query.getSingleResult();

            //Update view count


            return thread;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting thread from thread id");
        }finally {
            if(closeEM){
                em.close();
            }

        }
    }

    @Override
    public ThreadDTO getThreadWithPost(int threadId, EntityManager em) throws DBErrorException {
        //find thread in db
        try {
            TypedQuery<Thread> query = em.createQuery("SELECT t FROM Thread t LEFT JOIN t.posts p WHERE p.id = :threadId ", Thread.class);
            query.setParameter("threadId", threadId);
            Thread thread = query.getSingleResult();

            return new ThreadDTO(thread);
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting board and threads from board id");
        }finally {
            em.close();
        }
    }
}
