package com.patryk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index() {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Films.class)
				.addAnnotatedClass(Persons.class).buildSessionFactory();

		try {

			Films tempFilm;
			int i = 1;

			do {
				Session session = factory.getCurrentSession();

				session.beginTransaction();

				tempFilm = session.get(Films.class, i);

		
					if (tempFilm != null) {
						listOfFilms.add(new Films(tempFilm));
					}
		

				session.getTransaction().commit();
				i++;

				System.out.println(tempFilm);
			} while (tempFilm != null);

		} finally {
			factory.close();
		}

		listOfFilms.add(
				new Films("Avengers", "07:42:00", "04.08.2015", "http://1.fwcdn.pl/po/37/58/693758/7839647.3.jpg"));
		listOfFilms.add(new Films("Spider Man", "15:23:00", "16.5.2018",
				"http://moviesroom.pl/images/0.Aktualizacja_listopad/Pat/spider-man-homecoming-poster-plakat-1000x600.jpg"));
		listOfFilms.add(new Films("Thor", "14:50:00", "06.10.2018",
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

		model.addAttribute("film", film);
		model.addAttribute("person", person);
		return "reservation";
	}

	@RequestMapping(value = "/reserv", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("person") Persons person, Model model) {
		System.out.println(person);

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Films.class)
				.addAnnotatedClass(Persons.class).buildSessionFactory();
		try {
			Session session = factory.getCurrentSession();

			session.beginTransaction();
			
			Films tempFilm = session.get(Films.class, film.getId());
			//film.getMapOfPersons().putIfAbsent(person.getSeat(), person); 
			System.out.println(tempFilm);
			System.out.println(person);
			tempFilm.getMapOfPersons().put(person.getSeat(), person);
			
			session.save(person);

			session.getTransaction().commit();

			System.out.println("Done!");

		} finally {
			factory.close();
		}

		if (film.getMapOfPersons().putIfAbsent(person.getSeat(), person) != null) {
			return "redirect:/reservation";
		} else {
			return "redirect:/index";
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

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Films.class)
				.addAnnotatedClass(Persons.class).buildSessionFactory();

		try {
			Session session = factory.getCurrentSession();

			session.beginTransaction();

			session.save(film);

			session.getTransaction().commit();

			System.out.println("Done!");

		} finally {
			factory.close();
		}

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
		model.addAttribute("numberOfFilm", numberOfFilm);
		return "unreservation";
	}

	@RequestMapping(value = "unreserv", method = RequestMethod.POST)
	public String unreserv(Model model, @ModelAttribute("numberOfFilm") int numberOfFilm,
			@ModelAttribute("person") Persons person) {

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

			Persons tempPerson = listOfFilms.get(numberOfFilm).getMapOfPersons().get(person.getSeat());

			if ((tempPerson != null) && (tempPerson.getKey().equals(person.getKey()))) {
				listOfFilms.get(numberOfFilm).getMapOfPersons().replace(person.getSeat(), null);
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
		if (adminLog.equals("admin") && adminPass.equals("admin")) {
			return "adminPort";
		} else {
			return "redirect:/index";
		}
	}

	@RequestMapping(value = "goToAddPage")
	public String goToAddPage(Model model) {
		return "redirect:/addFilm";
	}

	@RequestMapping(value = "goToRemovePage")
	public String goToRemovePage(Model model) {
		return "redirect:/removeFilm";
	}

	@RequestMapping(value = "goToMainPage")
	public String goToMainPage(Model model) {
		return "redirect:/index";
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private List<Films> listOfFilms = new ArrayList<Films>();

	private Films film;

	private int numberOfFilm;

	public List<Films> getListOfFilms() {
		return listOfFilms;
	}

	public void setListOfFilms(List<Films> listOfFilms) {
		this.listOfFilms = listOfFilms;
	}

	public int getNumberOfFilm() {
		return numberOfFilm;
	}

	public void setNumberOfFilm(int numberOfFilm) {
		this.numberOfFilm = numberOfFilm;
	}

}
