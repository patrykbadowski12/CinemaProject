package com.patryk;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MoviesFactory {
	
	public Film create(String nameOfFilm, String time, String date, String image) {
		Film film = new Film();
		film.setNameOfFilm(nameOfFilm);
		film.setTime(time);
		film.setDate(date);
		film.setImage(image);
		
	
		
		return film;
	}

}
