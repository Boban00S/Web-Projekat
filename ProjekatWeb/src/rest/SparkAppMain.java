package rest;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jsonparsing.LocalDateConverter;
import jsonparsing.LocalDateTimeConverter;
import model.*;
import spark.Session;
import ws.WsHandler;

import static spark.Spark.*;

public class SparkAppMain {

	
	
	private static Gson g = getGson();
	private static UserDAO userDAO = new UserDAO("data/users.json");
	private static OfferDAO offerDAO = new OfferDAO("data/offers.json");
	private static SportsObjectDAO sportsObjectDAO = new SportsObjectDAO("data/sports_objects.json", offerDAO);
	private static ManagerDAO managerDAO = new ManagerDAO("data/managers.json");
	private static CustomerDAO customerDAO = new CustomerDAO("data/customers.json");
	private static TrainerDAO trainerDAO = new TrainerDAO("data/trainers.json");

	private static TrainingDAO trainingDAO = new TrainingDAO("data/training.json", offerDAO, trainerDAO, sportsObjectDAO);

	private static TrainingHistoryDAO trainingHistoryDAO = new TrainingHistoryDAO("data/training_history.json", trainingDAO, customerDAO, trainerDAO);

	/**
	 * Ključ za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		port(8080);

		webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/rest/test", (req, res) -> {
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if(user == null) {
				System.out.println("asd");
				res.status(200);
				return ("No");
			}
			res.status(200);
			return ("No");
//			}else {
//				res.status(200);
//				System.out.println(g.toJson(user));
//				return g.toJson(user);
//			}
		});
		
		get("/rest/users", (req, res) -> {
			res.status(200);
			
			return g.toJson(userDAO.findAll());
		});
		
		get("/rest/homepage", (req, res) -> {
			res.status(200);
			
			return g.toJson(sportsObjectDAO.findAll());
		});
		
		get("/rest/user", (req, res) -> {
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			User u = userDAO.findUserById(userId);
			res.status(200);
			return g.toJson(u);
		});
		
		get("rest/testlogin", (req, res) ->{
			Session ss = req.session(true);
			User user = ss.attribute("user");
			res.status(200);
			if(user == null) {
				return ("No");
			}else {
				return g.toJson(user);
			}
		});
		
		
		post("/rest/registration", (req, res) ->{
			res.type("application/json");
			String user = req.body();
			User u = g.fromJson(user, User.class);
			boolean contains = userDAO.contains(u);
			if(contains == true) {
				res.status(400);
				return ("No");
			}
			userDAO.addCustomer(u);
			Session ss = req.session(true);
			ss.attribute("user", u);
			res.status(200);
			return ("Yes");
		});

		post("/rest/create-employee", (req, res) ->{
			res.type("application/json");
			String user = req.body();
			User u = g.fromJson(user, User.class);
			boolean contains = userDAO.contains(u);
			if(contains == true) {
				res.status(400);
				return ("No");
			}
			if(u.getRole() == Role.manager){
				Manager m = g.fromJson(user, Manager.class);
				managerDAO.addManager(m);
			}else{
				Trainer t = g.fromJson(user, Trainer.class);
				trainerDAO.addTrainer(t);
			}
			Session ss = req.session(true);
			ss.attribute("newUser", u);
			res.status(200);
			return ("Yes");
		});

		post("/rest/login", (req, res) ->{
			res.type("application/json");
			String userLogin = req.body();
			LoginUser u = g.fromJson(userLogin, LoginUser.class);
			boolean validLogin = userDAO.validLogin(u);
			if(validLogin == false) {
				res.status(400);
				return "No user logged in.";
			}
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if(user == null) {
				user = userDAO.findUserByUsername(u.getUsername());
				ss.attribute("user", user);
			}
			res.status(200);
			return user.getId();
		});
		
		get("/rest/logout", (req, res) ->{
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if(user != null) {
				ss.invalidate();
			}
			return true;
		});
		
		post("/rest/edit_user", (req, res)-> {
			res.type("application/json");
			String user = req.body();
			User u = g.fromJson(user, User.class);
			boolean containsOtherUsername = userDAO.containsOtherUsername(u);
			if(containsOtherUsername == true) {
				res.status(400);
				return ("No");
			}
			userDAO.editUser(u);
			res.status(200);
			
			return ("Yes");
		});
	
		post("/rest/registration", (req, res) ->{
			res.type("application/json");
			String user = req.body();
			User u = g.fromJson(user, User.class);
			boolean contains = userDAO.contains(u);
			if(contains == true) {
				res.status(400);
				return ("No");
			}
			userDAO.addCustomer(u);
			Session ss = req.session(true);
			ss.attribute("user", u);
			res.status(200);
			return ("Yes");
		});

		get("/rest/personal-trainings", (req, res) ->{
			res.type("application/json");
			int trainerId = Integer.parseInt(req.queryMap("id").value());
			Trainer t = trainerDAO.findById(trainerId);
			List<Training> personalTrainings = trainingDAO.findPersonalTrainingsByTrainerId(trainerId);
			res.status(200);
			return g.toJson(personalTrainings);
		});

		delete("/rest/personal-training", (req, res) ->{
			res.type("application/json");
			int trainerId = Integer.parseInt(req.queryMap("trainerId").value());
			int trainingId = Integer.parseInt(req.queryMap("trainingId").value());
			trainingDAO.deleteById(trainingId);
			List<Training> personalTrainings = trainingDAO.findPersonalTrainingsByTrainerId(trainerId);
			res.status(200);
			return g.toJson(personalTrainings);
		});


		get("/rest/non-personal-trainings", (req, res) ->{
			res.type("application/json");
			int trainerId = Integer.parseInt(req.queryMap("id").value());
			Trainer t = trainerDAO.findById(trainerId);
			List<Training> personalTrainings = trainingDAO.findNonPersonalTrainingsByTrainerId(trainerId);
			res.status(200);
			return g.toJson(personalTrainings);
		});



		get("rest/manager-object", (req, res) ->{
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			Manager m = managerDAO.findManagerById(userId);
			SportsObject sportsObject = sportsObjectDAO.findById(m.getSportsObject());
			res.status(200);
			
			return g.toJson(sportsObject);
		});

		put("rest/sport-object", (req, res) ->{
			res.type("application/json");
			String sportObject = req.body();
			SportsObject sO = g.fromJson(sportObject, SportsObject.class);
			SportsObject sportsObject = sportsObjectDAO.editObject(sO);
			res.status(200);
			return g.toJson(sportsObject);
		});

		get("rest/trainings-month", (req, res) ->{
			res.type("application/json");
			int customerId = Integer.parseInt(req.queryMap("id").value());
			Customer customer = customerDAO.findById(customerId);
			List<Training30Days> training30DaysHistory = trainingHistoryDAO.findLast30DaysOfTrainings(customer);
			res.status(200);
			return g.toJson(training30DaysHistory);
		});

		delete("rest/sport-object/offer", (req, res) -> {
			res.type("application/json");
			int sportObjectId = Integer.parseInt(req.queryMap("objectId").value());
			int offerId = Integer.parseInt(req.queryMap("offerId").value());
			SportsObject sportsObject = sportsObjectDAO.deleteOfferById(sportObjectId, offerId);
			res.status(200);
			return g.toJson(sportsObject);
		});

		get("rest/sport-object/trainers", (req, res) ->{
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			Manager m = managerDAO.findManagerById(userId);
			List<Trainer> trainers = trainerDAO.findTrainerBySportsObjectId(m.getSportsObject());
			res.status(200);

			return g.toJson(trainers);
		});

		get("rest/sport-object", (req, res) ->{
			res.type("application/json");
			int objectId = Integer.parseInt(req.queryMap("id").value());
			SportsObject sportsObject = sportsObjectDAO.findById(objectId);
			res.status(200);

			return g.toJson(sportsObject);
		});

		get("rest/customers", (req, res) ->{
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			Manager m = managerDAO.findManagerById(userId);
			List<Customer> customers = customerDAO.findCustomersBySportsObjectId(m.getSportsObject());
			res.status(200);

			return g.toJson(customers);
		});
		
		get("rest/trainers", (req, res) ->{
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			Manager m = managerDAO.findManagerById(userId);
			List<Trainer> trainers = trainerDAO.findTrainerBySportsObjectId(m.getSportsObject());
			res.status(200);
			
			return g.toJson(trainers);
		});
		
		get("rest/available/managers", (req, res) ->{
			res.type("application/json");
			List<Manager> availableManagers = managerDAO.findAvailableManagers();
			res.status(200);
			return g.toJson(availableManagers);
		});
		
		post("rest/manager/registration", (req, res) -> {
			String manager = req.body();
			Manager m = g.fromJson(manager, Manager.class);
			boolean contains = userDAO.contains(m);
			if(contains == true) {
				res.status(400);
				return ("No");
			}
			managerDAO.addManager(m);
			return ("Yes");
			
		});
		
		post("rest/create/object", (req, res) ->{
			String sportsObject = req.body();
			SportsObject s = g.fromJson(sportsObject, SportsObject.class);
			boolean contains = sportsObjectDAO.contains(s);
			if(contains == true) {
				res.status(400);
				return ("No");
			}
			int objectsId = sportsObjectDAO.getNextId();
			s.setImagePath("../images/"+s.getName()+"_"+objectsId+".jpg");
			sportsObjectDAO.addObject(s);
			managerDAO.addSportsObjectToManager(s);
			return ("Yes");
		});
		
		post("rest/object/image", (req, res) -> {
			MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
			req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
			String fileName = req.raw().getParameter("fileName");
			int lastId = sportsObjectDAO.getLastId();
			String fullPath = "static/images/" + fileName+"_"+lastId+".jpg";
			Part file = req.raw().getPart("file");
			Path path = Paths.get(fullPath);
			try(final InputStream in = file.getInputStream()){
				Files.copy(in, path);
			}
			
			return "OK";
			
		});

		post("rest/offer/image", (req, res) -> {
			MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
			req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
			String fileName = req.raw().getParameter("fileName");
			String fullPath = "static/images/" + fileName+".jpg";
			Part file = req.raw().getPart("file");
			Path path = Paths.get(fullPath);
			try(final InputStream in = file.getInputStream()){
				Files.copy(in, path);
			}
			
			return "OK";
			
		});

		get("rest/sort", (req, res) ->{
			res.type("application/json");
			String sortColumn = req.queryMap("sortColumn").value();
			return g.toJson(sportsObjectDAO.sortBy(sortColumn));
		});

		
/*
 * 
 * 
 * 
 * 			res.type("application/json");
			String userLogin = req.body();
			LoginUser u = g.fromJson(userLogin, LoginUser.class);
			boolean validLogin = userDAO.validLogin(u);
			if(validLogin == false) {
				res.status(400);
				return ("No");
			}
			res.status(200);
			return ("Yes");
 * 
 * 
 * 
 * UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		boolean contains = userDAO.contains(user);
		if(contains == true) {
			return Response.status(400).entity("Username already exists!").build();
		}
		try {
			userDAO.addUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).build();
 * */
	}
	private static Gson getGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		Gson gson = builder.create();
		return gson;
	}
	
}
