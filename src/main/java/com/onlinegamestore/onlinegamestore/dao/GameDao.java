package com.onlinegamestore.onlinegamestore.dao;

import java.util.List;

import com.onlinegamestore.onlinegamestore.models.Game;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameDao {
	@Autowired
    private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }
	
	@Transactional
	public List<Game> getGames(){
		return this.sessionFactory.getCurrentSession().createQuery("from GAME").list();
	}
	
	@Transactional
	public Game addGame(Game game) {
		this.sessionFactory.getCurrentSession().save(game);
		return game;
	}
	
	@Transactional
	public Game getGame(int id) {
		return this.sessionFactory.getCurrentSession().get(Game.class, id);
	}

	public Game updateGame(Game game){
		this.sessionFactory.getCurrentSession().update(String.valueOf(Game.class), game);
		return game;
	}
	@Transactional
	public Boolean deleteGame(int id) {

		Session session = this.sessionFactory.getCurrentSession();
		Object persistenceInstance = session.load(Game.class, id);

		if (persistenceInstance != null) {
			session.delete(persistenceInstance);
			return true;
		}
		return false;
	}

}
