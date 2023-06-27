package com.onlinegamestore.onlinegamestore.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlinegamestore.onlinegamestore.models.User;


@Repository
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Transactional
    public List<User> getAllUser() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from USER").list();
        return userList;
    }

    @Transactional
    public User saveUser(User user) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(user);
        System.out.println("User added" + user.getId());
        return user;
    }

    @Transactional
    public boolean hasUser(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from USER where username = :username");
        System.out.println(query.getQueryString());
        query.setParameter("username", username);
        System.out.println(query.getQueryString());
        try {
            query.getSingleResult();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public User validateUser(String username, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery("from USER where username = :username");
        query.setParameter("username", username);

        try {
            User user = (User) query.getSingleResult();
            System.out.println(user.getPassword());
            if (password.equals(user.getPassword())) {
                return user;
            } else {
                return new User();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            User user = new User();
            return user;
        }

    }
}
