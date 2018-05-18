package com.Patryk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index() {

		listOfFilms.add(
				new Films(1, "Avengers", "07:42:00", "04.08.2015", "http://1.fwcdn.pl/po/37/58/693758/7839647.3.jpg"));
		listOfFilms.add(new Films(2, "Spider Man", "15:23:00", "16.5.2018",
				"http://moviesroom.pl/images/0.Aktualizacja_listopad/Pat/spider-man-homecoming-poster-plakat-1000x600.jpg"));
		listOfFilms.add(new Films(3, "Thor", "14:50:00", "06.10.2018",
				"https://images-na.ssl-images-amazon.com/images/G/01/digital/video/hero/Movies/2013/ThorDarkWorld_2194942100-TDW0NNG1._V362444527_RI_SX940_.jpg"));

		return "redirect:/index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getMethod(Model model) {

		model.addAttribute("films", listOfFilms);
		return "index";
	}

	@RequestMapping(value = "/reservation", method = RequestMethod.GET)
	public String reservation(Model model) {

		Persons person = new Persons();

		model.addAttribute("seat", seat);
		model.addAttribute("film", film);
		model.addAttribute("person", person);
		return "reservation";
	}

	@RequestMapping(value = "/reserv", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("person") Persons person, @ModelAttribute("seat") int seat, Model model) {

		if (film.getMapOfPersons().putIfAbsent(seat, person) != null) {
			return "redirect:/reservation";
		} else {
			model.addAttribute("films", listOfFilms);
			return "index";
		}
	}

	@RequestMapping(value = "reservationPlace", method = RequestMethod.POST)
	public String reservationPlace(Model model, @ModelAttribute("numberOfFilm") int numberOfFilm) {

		Date d1 = new Date();
		System.out.println(d1);
		try {
			Date d2 = dateFormat
					.parse(listOfFilms.get(numberOfFilm).getDate() + " " + listOfFilms.get(numberOfFilm).getTime());
			long diff = d2.getTime() - d1.getTime();
			System.out.println(diff);
			long diffMinutes = diff / (60 * 1000);
			System.out.println(diffMinutes);

			if (diffMinutes > 30) {

				film = listOfFilms.get(numberOfFilm);
				System.out.println(film);
				return "redirect:/reservation";
			} else {
				return "redirect:/index";
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "redirect:/index";
	}

	@RequestMapping(value = "/addFilm", method = RequestMethod.GET)
	public String addFilm(Model model) {

		model.addAttribute("film", new Films());
		model.addAttribute("listOfFilms", listOfFilms);

		return "addFilm";
	}

	@RequestMapping(value = "/addFilm", method = RequestMethod.POST)
	public String addFilm(@ModelAttribute("film") Films film) {

		listOfFilms.add(film);

		return "adminPort";
	}

	@RequestMapping(value = "/removeFilm", method = RequestMethod.GET)
	public String remove(Model model) {
		model.addAttribute("listOfFilms", listOfFilms);
		return "removeFilm";
	}

	@RequestMapping(value = "/removeFilm", method = RequestMethod.POST)
	public String removeFilm(Model model, @ModelAttribute("numberOfFilm") int numberOfFilm) {

		getListOfFilms().remove(numberOfFilm);
		return "adminPort";
	}

	@RequestMapping(value = "/unreservation", method = RequestMethod.GET)
	public String unreservation(Model model) {

		Persons person = new Persons();
		model.addAttribute("person", person);
		model.addAttribute("seat", seat);
		model.addAttribute("numberOfFilm", numberOfFilm);
		return "unreservation";
	}

	@RequestMapping(value = "unreserv", method = RequestMethod.POST)
	public String unreserv(Model model, @ModelAttribute("sit") int sit,
			@ModelAttribute("numberOfFilm") int numberOfFilm, @ModelAttribute("person") Persons person) {

		Date d1 = new Date();
		long diffMinutes = 0;
		System.out.println(d1);
		try {
			Date d2 = dateFormat
					.parse(listOfFilms.get(numberOfFilm).getDate() + " " + listOfFilms.get(numberOfFilm).getTime());
			long diff = d2.getTime() - d1.getTime();
			System.out.println(diff);
			diffMinutes = diff / (60 * 1000);
			System.out.println(diffMinutes);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (diffMinutes >= 30) {

			Persons tempPerson = listOfFilms.get(numberOfFilm).getMapOfPersons().get(sit);

			if ((tempPerson != null) && (tempPerson.getKey().equals(person.getKey()))) {
				listOfFilms.get(numberOfFilm).getMapOfPersons().replace(sit, null);
				System.out.println("udało się zwolnic miejsce");
				return "redirect:/index";
			} else {
				System.out.println("Miejsce jest wolne");
				return "redirect:/unreservation";
			}
		} else {
			System.out.println("za pozno");
			return "redirect:/index";
		}
	}

	@RequestMapping(value = "goToAdmin", method = RequestMethod.POST)
	public String goToAdmin(Model model, @ModelAttribute("adminLog") String adminLog,
			@ModelAttribute("adminPass") String adminPass) {
		if(adminLog.equals("admin") && adminPass.equals("admin")) {
		return "adminPort";
	} else {
		return "redirect:/index";
		}
	}
	
	@RequestMapping(value="goToAddPage")
	public String goToAddPage(Model model) {
		return "redirect:/addFilm";
	}
	
	@RequestMapping(value="goToRemovePage")
	public String goToRemovePage(Model model) {
		return "redirect:/removeFilm";
	}
	
	@RequestMapping(value="goToMainPage")
	public String goToMainPage(Model model) {
		return "redirect:/index";
	}
	

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private List<Films> listOfFilms = new ArrayList<Films>();

	private Films film;

	private int numberOfFilm;

	private int seat;

	public List<Films> getListOfFilms() {
		return listOfFilms;
	}

	public void setListOfFilms(List<Films> listOfFilms) {
		this.listOfFilms = listOfFilms;
	}

	public int getSeat() {
		return seat;
	}

	public void setSit(int seat) {
		this.seat = seat;
	}

	public int getNumberOfFilm() {
		return numberOfFilm;
	}

	public void setNumberOfFilm(int numberOfFilm) {
		this.numberOfFilm = numberOfFilm;
	}

}
