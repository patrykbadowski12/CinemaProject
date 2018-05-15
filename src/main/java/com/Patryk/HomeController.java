package com.Patryk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public List<Films> listOfFilms = new ArrayList<Films>();
	
	Films film;

	public List<Films> getListOfFilms() {
		return listOfFilms;
	}

	public void setListOfFilms(List<Films> listOfFilms) {
		this.listOfFilms = listOfFilms;
	}

	int sit;

	public int getSit() {
		return sit;
	}

	public void setSit(int sit) {
		this.sit = sit;
	}
	
	int numberOfFilm;

	public int getNumberOfFilm() {
		return numberOfFilm;
	}

	public void setNumberOfFilm(int numberOfFilm) {
		this.numberOfFilm = numberOfFilm;
	}

	@RequestMapping("/")
	public String index() {
		
		listOfFilms.add(new Films(1, "Avengers","07:42:00", "04.08.2015",
				"http://1.fwcdn.pl/po/37/58/693758/7839647.3.jpg"));
		listOfFilms.add(new Films(2, "Spider Man", "09:00:00", "06.10.2018",
				"http://moviesroom.pl/images/0.Aktualizacja_listopad/Pat/spider-man-homecoming-poster-plakat-1000x600.jpg"));
		listOfFilms.add(new Films(3, "Thor", "13:30:00", "06.10.2018",
				"https://images-na.ssl-images-amazon.com/images/G/01/digital/video/hero/Movies/2013/ThorDarkWorld_2194942100-TDW0NNG1._V362444527_RI_SX940_.jpg"));

		return "redirect:/index";
	}

	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String getMethod(Model model) {
		
		model.addAttribute("films", listOfFilms);
		return "index";
	}
	
	@RequestMapping(value="/reservation", method = RequestMethod.GET)
	public String reservation (Model model) {
		
		Persons person= new Persons();
		
		model.addAttribute("sit",sit);
		model.addAttribute("film",film);
		model.addAttribute("person", person);
		return "reservation";
	}

	@RequestMapping(value = "/reserv", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("person") Persons person, @ModelAttribute("sit") int sit, Model model) {
		
		if(film.getMapOfPersons().putIfAbsent(sit, person) != null) {
			return "redirect:/reservation";
		}else{
			model.addAttribute("films", listOfFilms);
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value="reservationPlace", method = RequestMethod.POST)
	public String reservationPlace(Model model, @ModelAttribute("numberOfFilm") int numberOfFilm ) {
		
		film = listOfFilms.get(numberOfFilm);
		System.out.println(film);
		
		return "redirect:/reservation";
	}


	@GetMapping("/addFilm")
	public String addFilm(Model model) {

	model.addAttribute("film", new Films());
		model.addAttribute("listOfFilms", listOfFilms);
		
		return "addFilm";
	}

	@RequestMapping(value = "/addFilm", method = RequestMethod.POST)
	public String addFilm(@ModelAttribute("film") Films film) {
		

		listOfFilms.add(film);
		
		return "redirect:/index";
	}
	@RequestMapping(value="/unreservation", method = RequestMethod.GET)
	public String unreservation(Model model) {
		
		Persons person = new Persons();
		model.addAttribute("person",person);
		model.addAttribute("sit",sit);
		model.addAttribute("numberOfFilm",numberOfFilm);
		return "unreservation";
	}
	
	@RequestMapping(value="unreserv",  method = RequestMethod.POST)
	public String unreserv(Model model, @ModelAttribute("sit") int sit, @ModelAttribute("numberOfFilm") int numberOfFilm,
			@ModelAttribute("person") Persons person) {
		
		Persons tempPerson=listOfFilms.get(numberOfFilm).getMapOfPersons().get(sit);
	
		if((tempPerson!= null) && (tempPerson.getKey().equals(person.getKey()))) {
		listOfFilms.get(numberOfFilm).getMapOfPersons().replace(sit, null);
		System.out.println("udało się zwolnic miejsce");
		} else {
			System.out.println("Miejsce jest wolne");
		}
		
		return "redirect:/index";
	}

}
