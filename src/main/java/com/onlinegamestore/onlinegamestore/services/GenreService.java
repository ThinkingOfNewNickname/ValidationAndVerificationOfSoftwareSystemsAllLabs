package com.onlinegamestore.onlinegamestore.services;

import java.util.List;

import com.onlinegamestore.onlinegamestore.dao.GenreDao;
import com.onlinegamestore.onlinegamestore.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
	@Autowired
	private GenreDao genreDao;
	
	public Genre addGenre(String name) {
		return this.genreDao.addGenre(name);
	}
	
	public List<Genre> getGenres(){
		return this.genreDao.getGenre();
	}
	
	public Boolean deleteGenre(int id) {
		return this.genreDao.deleteGenre(id);
	}
	
	public Genre updateGenre(int id, String name) {
		return this.genreDao.updateGenre(id, name);
	}

	public Genre getGenre(int id) {
		return this.genreDao.getGenre(id);
	}
}
