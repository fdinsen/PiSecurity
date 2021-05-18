package utils;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Exceptions.UserNotFoundException;
import Facades.ForumFacade;
import Facades.ThreadFacade;
import Models.Board;
import Models.Category;
import Models.Role;
import Models.User;
import Persistence.CategoryDaoImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestSystem {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    public static void main(String[] args) throws DBErrorException, InvalidInputException, UserNotFoundException {
        //addUser("Peter", "peter123");
        //addUser("Karl", "karl123");
        User user = getUser("frederikdinsen");

        String test = JWTHandling.createJWT("frederik");
        String username = JWTHandling.readJWT(test);
        System.out.println(test);
        System.out.println(username);

        Category cat = new Category();
        //cat.setBoards(new ArrayList<Board>());
        cat.setCreatedAt(new Date());
        cat.setCreatedBy(user);
        cat.setName("Category");
        cat.setId(1);

//        Board board = new Board();
//        board.setCategory(cat);
//        board.setDescription("Tests desc");
//        board.setName("Board1");
//        board.setCreatedAt(new Date());
//        board.setCreatedBy(user);
//        
//        Board board2 = new Board();
//        board2.setCategory(cat);
//        board2.setDescription("Tests desc 2");
//        board2.setName("Board2");
//        board2.setCreatedAt(new Date());
//        board2.setCreatedBy(user);
//        
//        cat.getBoards().add(board);
//        cat.getBoards().add(board2);
        //CategoryDTO catdto = new CategoryDTO(cat);
        //List<CategoryDTO> all = new CategoryDaoImpl().getAllCategories(EMF.createEntityManager());
        //ForumFacade forumFacade = new ForumFacade();
        //BoardDTO boardDTO = forumFacade.getBoardWithThreads("1");
//        ThreadFacade threadF = new ThreadFacade();
//        threadF.createThread("titljgjhhger", "<p>This is thbvnbvhe content</p>", "1", "frederikdinsen");
        ForumFacade forumFacade = new ForumFacade();
        BoardDTO boardDTO = forumFacade.getBoardWithThreads("1");
        EMF.close();
    }

    public static void addUser(String username, String pw) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            User user = new User();
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw(pw, BCrypt.gensalt(12)));
            user.setRole(Role.user);

            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static User getUser(String username) {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.username = :un";
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("un", username);
        User user = null;
        try {
            user = tq.getSingleResult();
            System.out.println(user.getUsername() + ", " + user.getId().toString() + ", " + user.getPassword());
        } catch (NoResultException e) {
            System.out.println("user " + username + " not found");
            e.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }

    public static void getUsers() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.id IS NOT NULL";
        TypedQuery<User> tq = em.createQuery(query, User.class);
        List<User> users;
        try {
            users = tq.getResultList();
            users.forEach(user -> System.out.println(user.getUsername() + ", " + user.getId().toString() + ", " + user.getPassword()));
        } catch (NoResultException e) {
            System.out.println("users not found");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void changeUName(String username, String pw) {
        EntityManager em = EMF.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            User user = new User();
            user.setUsername(username);
            user.setPassword(pw);

            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
