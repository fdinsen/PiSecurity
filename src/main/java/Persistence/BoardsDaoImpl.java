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
import java.util.ArrayList;
import java.util.List;

public class BoardsDaoImpl implements IBoardDao {


    @Override
    public void createBoard(String name, String description, CategoryDTO categoryDTO, User createdBy, EntityManager em) throws DBErrorException, UserNotFoundException {
        BoardDTO boardDTO = null;
        try{
            boardDTO = getBoardFromName(name,false, em);
        }catch (Exception e){
            em.close();
            throw new DBErrorException("Something went wrong while checking if board already exist");
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
    public BoardDTO getBoardFromName(String name, Boolean closeEM, EntityManager em) throws DBErrorException {
        //Check if already exist
        Board board = null;

        //find board in db
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
            if(closeEM){
            em.close();
            }
        }
    }

    @Override
    public BoardDTO getBoardDTOFromID(int boardId,Boolean closeEM, EntityManager em) throws DBErrorException {
        Board board = getBoardFromID(boardId,closeEM,em);
        return new BoardDTO(board);
    }

    @Override
    public Board getBoardFromID(int boardId,Boolean closeEM, EntityManager em) throws DBErrorException {
        //find board in db
        try {
            TypedQuery<Board> query = em.createQuery("SELECT b FROM Board b WHERE b.id = :boardId", Board.class);
            query.setParameter("boardId", boardId);
            Board board = query.getSingleResult();
            return board;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting board from board id");
        }finally {
            if(closeEM){
                em.close();
            }

        }
    }

    @Override
    public BoardDTO getBoardWithThreads(int boardId, EntityManager em) throws DBErrorException {
        //find board in db
        try {
            TypedQuery<Board> query = em.createQuery("SELECT b FROM Board b LEFT JOIN b.threads t WHERE b.id = :boardId ", Board.class);
            query.setParameter("boardId", boardId);
            Board board = query.getSingleResult();

            return new BoardDTO(board);
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting board and threads from board id");
        }finally {
                em.close();
        }
    }

    @Override
    public List<BoardDTO> getBoardsForCategory(CategoryDTO categoryDTO, EntityManager em) throws DBErrorException {
        //Check if already exist
        try {
            //find board
            TypedQuery<Board> query = em.createQuery("SELECT b FROM Board b WHERE b.category.id = :catId", Board.class);
            query.setParameter("catId", categoryDTO.getId());
            List<Board> boards = query.getResultList();

            List<BoardDTO> boardDTOS = new ArrayList<>();
            for (int i = 0; i < boards.size(); i++) {
                boardDTOS.add(new BoardDTO(boards.get(i)));
            }
            return boardDTOS;
        } catch (NoResultException nre){
            return null;
        }
        catch (Exception e) {
            throw new DBErrorException("Something went wrong while getting boards for category");
        }finally {
            em.close();
        }
    }

    @Override
    public void editBoard(int boardId, String boardName, String description, User user, EntityManager em) throws DBErrorException {
        try {
            em.getTransaction().begin();
            //Get category from dto
            Board board = getBoardFromID(boardId,false, em);

            board.setName(boardName);
            board.setDescription(description);
            board.setUpdatedBy(user);
            em.persist(board);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while updating board in DB");
        }finally {
            em.close();
        }
    }

    @Override
    public void deleteBoard(int boardId, EntityManager em) throws DBErrorException {
        try {
            //Delete
            em.getTransaction().begin();

            //Get category from dto
            Board board = getBoardFromID(boardId, false, em);

            em.remove(board);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            throw new DBErrorException("Something went wrong while deleting board");
        }finally {
            em.close();
        }
    }
}
