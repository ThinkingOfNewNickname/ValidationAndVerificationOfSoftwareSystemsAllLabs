package com.entryverificationsystem.entryverificationsystem.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entryverificationsystem.entryverificationsystem.models.User;


@Repository
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Transactional
    public User saveUser(User user) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(user);
        return user;
    }

    @Transactional
    public boolean hasUser(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from USER where username = :username");
        query.setParameter("username", username);

        try {
            query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public User validateUser(String username, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery("from USER where username = :username");
        query.setParameter("username", username);

        try {
            User user = (User) query.getSingleResult();

            if (password.equals(user.getPassword())) {
                return user;
            } else {
                return new User();
            }
        }
        catch (Exception e) {
            return new User();
        }
    }
}
