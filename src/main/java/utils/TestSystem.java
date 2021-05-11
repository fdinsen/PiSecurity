package utils;

import Models.Role;
import Models.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestSystem {
    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static void main(String[] args) {
        addUser("Peter", "peter123");
        addUser("Karl", "karl123");
        getUser("Peter");
        
        String test = JWTHandling.createJWT("frederik");
        String username = JWTHandling.readJWT(test);
        System.out.println(test);
        System.out.println(username);

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
        }catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

    public static void getUser(String username) {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.username = :un";
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("un", username);
        User user = null;
        try {
            user = tq.getSingleResult();
            System.out.println(user.getUsername() + ", " + user.getId().toString() + ", " + user.getPassword());
        }catch(NoResultException e) {
            System.out.println("user " + username + " not found");
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

    public static void getUsers() {
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.id IS NOT NULL";
        TypedQuery<User> tq = em.createQuery(query, User.class);
        List<User> users;
        try {
            users = tq.getResultList();
            users.forEach(user -> System.out.println(user.getUsername() + ", " + user.getId().toString() + ", " + user.getPassword()));
        }catch(NoResultException e) {
            System.out.println("users not found");
            e.printStackTrace();
        }finally {
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
        }catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
