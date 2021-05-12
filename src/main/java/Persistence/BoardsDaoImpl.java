package Persistence;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Models.Board;
import Models.Category;
import Models.User;
import Persistence.DAO.IBoardDao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class BoardsDaoImpl implements IBoardDao {


    @Override
    public void createBoard(String name, String description, Category category, User createdBy, EntityManager em) throws DBErrorException, UserNotFoundException {
        Board board = null;
        try{
            board = getBoardFromName(name, em);
        }catch (Exception e){
            throw new DBErrorException("Something went wrong while checking if board already exist");
        }

        if(board != null){
            throw new DBErrorException("Category already exist");
        }

        try {
            em.getTransaction().begin();
            board = new Board();
            board.setName(name);
            board.setDescription(description);
            board.setCategory(category);
            board.setCreatedBy(createdBy);
            em.persist(board);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while creating board in DB");
        }
    }

    @Override
    public Board getBoardFromName(String name, EntityManager em) throws DBErrorException {
        //Check if already exist
        Board board = null;

        //find category in db
        try {
            TypedQuery<Board> query = em.createQuery("SELECT b FROM Board b WHERE b.name = :boardName", Board.class);
            query.setParameter("boardName", name);
            board = query.getSingleResult();
            return  board;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting board from board name");
        }
    }

    @Override
    public Board getBoardFromID(int boardId, EntityManager em) throws DBErrorException {
        //Check if already exist
        Board board = null;

        //find category in db
        try {
            TypedQuery<Board> query = em.createQuery("SELECT b FROM Board b WHERE b.id = :boardId", Board.class);
            query.setParameter("boardId", boardId);
            board = query.getSingleResult();
            return  board;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting board from board id");
        }
    }

    @Override
    public List<Board> getBoardsForCategory(Category category, EntityManager em) throws DBErrorException {
        return null;
    }
}
