package com.onlinegamestore.onlinegamestore.services;

import java.util.List;

import com.onlinegamestore.onlinegamestore.dao.GameDao;
import com.onlinegamestore.onlinegamestore.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	@Autowired
	private GameDao gameDao;
	
	public List<Game> getGames(){
		return this.gameDao.getGames();
	}
	
	public Game addGame(Game game) {
		return this.gameDao.addGame(game);
	}
	
	public Game getGame(int id) {
		return this.gameDao.getGame(id);
	}

	public Game updateGame(int id, Game game){
		game.setId(id);
		return this.gameDao.updateGame(game);
	}
	public boolean deleteGame(int id) {
		return this.gameDao.deleteGame(id);
	}

	
}
