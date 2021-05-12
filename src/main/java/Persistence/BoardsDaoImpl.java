package Persistence;

import DTO.BoardDTO;
import DTO.CategoryDTO;
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
    public void createBoard(String name, String description, CategoryDTO categoryDTO, User createdBy, EntityManager em) throws DBErrorException, UserNotFoundException {
        BoardDTO boardDTO = null;
        try{
            boardDTO = getBoardFromName(name, em);
        }catch (Exception e){
            throw new DBErrorException("Something went wrong while checking if board already exist");
        }finally {
            em.close();
        }

        if(boardDTO != null){
            em.close();
            throw new DBErrorException("Category already exist");
        }

        try {
            em.getTransaction().begin();
            Board board = new Board();
            board.setName(name);
            board.setDescription(description);

            //Get category
            CategoryDaoImpl categoryDao = new CategoryDaoImpl();
            Category category = categoryDao.getCategoryFromID(categoryDTO.getId(),false, em);
            board.setCategory(category);

            board.setCreatedBy(createdBy);

            //check if detached
            em.persist(board);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while creating board in DB");
        }finally {
            em.close();
        }
    }

    @Override
    public BoardDTO getBoardFromName(String name, EntityManager em) throws DBErrorException {
        //Check if already exist
        Board board = null;

        //find category in db
        try {
            TypedQuery<Board> query = em.createQuery("SELECT b FROM Board b WHERE b.name = :boardName", Board.class);
            query.setParameter("boardName", name);
            board = query.getSingleResult();

            BoardDTO boardDTO = new BoardDTO(board);

            return boardDTO;
        } catch (NoResultException nre){
            return null;
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting board from board name");
        } finally {
            em.close();
        }
    }

    @Override
    public BoardDTO getBoardFromID(int boardId, EntityManager em) throws DBErrorException {
        //Check if already exist
        Board board = null;

        //find category in db
        try {
            TypedQuery<Board> query = em.createQuery("SELECT b FROM Board b WHERE b.id = :boardId", Board.class);
            query.setParameter("boardId", boardId);
            board = query.getSingleResult();

            BoardDTO boardDTO = new BoardDTO(board);

            return boardDTO;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting board from board id");
        }finally {
            em.close();
        }
    }

    @Override
    public List<BoardDTO> getBoardsForCategory(CategoryDTO categoryDTO, EntityManager em) throws DBErrorException {
        return null;
    }
}
