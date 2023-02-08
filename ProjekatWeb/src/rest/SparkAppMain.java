package rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	private static ManagerDAO managerDAO = new ManagerDAO("data/managers.json", userDAO);
	private static CustomerDAO customerDAO = new CustomerDAO("data/customers.json", userDAO);
	private static TrainerDAO trainerDAO = new TrainerDAO("data/trainers.json", userDAO);

	private static SportsObjectDAO sportsObjectDAO = new SportsObjectDAO("data/sports_objects.json");

	private static TrainingDAO trainingDAO = new TrainingDAO("data/training.json", trainerDAO, sportsObjectDAO);


	private static TrainingHistoryDAO trainingHistoryDAO = new TrainingHistoryDAO("data/training_history.json", trainingDAO, customerDAO, trainerDAO);

	private static MembershipDAO membershipDAO = new MembershipDAO("data/memberships.json");
//	private static CustomerMembershipDAO customerMembershipDAO = new CustomerMembershipDAO("data/customer_memberships.json");

	/**
	 * Ključ za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		runThread();

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

		get("/rest/memberships", (req, res) -> {
			res.status(200);

			return g.toJson(membershipDAO.findAll());
		});

		get("/rest/homepage", (req, res) -> {
			res.status(200);
			
			return g.toJson(sportsObjectDAO.findAll());
		});

		get("rest/sport-object/trainings", (req, res) ->{
			int userId = Integer.parseInt(req.queryMap("id").value());
			Manager m = managerDAO.findManagerById(userId);
			SportsObject sportsObject = sportsObjectDAO.findById(m.getSportsObject());
			res.status(200);
			List<Training> trainings = trainingDAO.findAllTrainingsInSportObject(sportsObject.getId());

			return g.toJson(trainings);
		});

		get("/rest/user", (req, res) -> {
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			User u = userDAO.findUserById(userId);
			res.status(200);
			return g.toJson(u);
		});

		get("/rest/customer", (req, res) -> {
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			Customer c = customerDAO.findById(userId);
			res.status(200);
			return g.toJson(c);
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
			Customer u = g.fromJson(user, Customer.class);
			boolean contains = userDAO.contains(u);
			if(contains == true) {
				res.status(400);
				return ("No");
			}
			customerDAO.addCustomer(u);
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
			return g.toJson(user);
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

		post("rest/customer/membership", (req, res)->{
			res.type("application/json");
			String customer = req.body();
			Customer c = g.fromJson(customer, Customer.class);
			membershipDAO.setExpireAndBuyingDate(c.getMembership());
			c.setDailyUsageLeft(c.getMembership().getDailyUsage());
			Customer c1 = customerDAO.addMembership(c);
			res.status(201);

			return g.toJson(c1);
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
			trainingHistoryDAO.deleteByTrainingId(trainingId);
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

		get("/rest/sport-object-trainings", (req, res) ->{
			res.type("application/json");
			int managerId = Integer.parseInt(req.queryMap("id").value());
			Manager m = managerDAO.findManagerById(managerId);
			List<Training> trainings = trainingDAO.findAllTrainingsInSportObject(m.getSportsObject());
			res.status(200);
			return g.toJson(trainings);
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

		delete("rest/sport-object/training", (req, res) -> {
			res.type("application/json");
			int sportObjectId = Integer.parseInt(req.queryMap("objectId").value());
			int trainingId = Integer.parseInt(req.queryMap("trainingId").value());
			trainingDAO.deleteById(trainingId);
			List<Training> trainings = trainingDAO.findBySportsObjectId(sportObjectId);
			res.status(200);
			return g.toJson(trainings);
		});

		post("rest/sport-object/training", (req, res) -> {
			res.type("application/json");
			String training = req.body();
			Training t = g.fromJson(training, Training.class);
			t.setDate(LocalDateTime.now());
			trainingDAO.addTraining(t);
			res.status(200);
			return ("Yes");
		});

		get("rest/sport-object/trainers", (req, res) ->{
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			Manager m = managerDAO.findManagerById(userId);
			List<Trainer> trainers = trainerDAO.findTrainerBySportsObjectId(m.getSportsObject());
			res.status(200);

			return g.toJson(trainers);
		});

		get("rest/sports-object/available-trainings", (req, res) ->{
			res.type("application/json");
			int sportsObjectId = Integer.parseInt(req.queryMap("id").value());
			List<Training> availableTrainings = trainingDAO.findAvailableTrainings(sportsObjectId);

			return g.toJson(availableTrainings);
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

		post("rest/training-history", (req, res) ->{
			String trainingHistoryString = req.body();
			TrainingHistory trainingHistory = g.fromJson(trainingHistoryString, TrainingHistory.class);
			if(trainingHistory.getCustomer().getDailyUsageLeft() == 0 || !trainingHistory.getCustomer().getMembership().isActive()){
				res.status(400);
				return ("No");
			}
			Customer c1 = trainingHistory.getCustomer();
			c1.setDailyUsageLeft(trainingHistory.getCustomer().getDailyUsageLeft()-1);
			List<Integer> sportsObjectsAttended = c1.getSportsObjectAttended();
			sportsObjectsAttended.add(trainingHistory.getTraining().getSportsObject().getId());
			c1.setSportsObjectAttended(sportsObjectsAttended);
			customerDAO.editCustomer(c1);
			trainingHistoryDAO.addTrainingHistory(trainingHistory);

			res.status(200);
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

		get("rest/sort/sports-objects", (req, res) ->{
			res.type("application/json");
			String sortColumn = req.queryMap("sortColumn").value();
			String ascending = req.queryMap("ascending").value();
			boolean isAscending = ascending.equals("true");
			return g.toJson(sportsObjectDAO.sortBy(sortColumn, isAscending));
		});

		get("rest/sort/customer-trainings", (req, res) ->{
			res.type("application/json");
			int customerId = Integer.parseInt(req.queryMap("id").value());
			String sortColumn = req.queryMap("sortColumn").value();
			String ascending = req.queryMap("ascending").value();
			boolean isAscending = ascending.equals("true");
			Customer customer = customerDAO.findById(customerId);
			return g.toJson(trainingHistoryDAO.sortyBy(sortColumn, isAscending, customer));
		});

		get("rest/sort/manager-trainings", (req, res) ->{
			res.type("application/json");
			int managerId = Integer.parseInt(req.queryMap("id").value());
			String sortColumn = req.queryMap("sortColumn").value();
			String ascending = req.queryMap("ascending").value();
			boolean isAscending = ascending.equals("true");
			Manager manager = managerDAO.findManagerById(managerId);
			return g.toJson(trainingDAO.sortSportObjectTrainingsBy(sortColumn, isAscending, manager.getSportsObject()));
		});


		get("rest/customer-type", (req, res) ->{
			res.type("application/json");
			int userId = Integer.parseInt(req.queryMap("id").value());
			User user = userDAO.findUserById(userId);

			return customerDAO.getCustomerType(user);
		});

		get("rest/sort/users", (req, res) ->{
			res.type("application/json");
			String sortColumn = req.queryMap("sortColumn").value();
			String ascending = req.queryMap("ascending").value();
			boolean isAscending = ascending.equals("true");
			return g.toJson(userDAO.sortBy(sortColumn, isAscending, customerDAO));
		});

		get("rest/sort/trainer-trainings/personal", (req, res) ->{
			res.type("application/json");
			int trainerId = Integer.parseInt(req.queryMap("id").value());
			String sortColumn = req.queryMap("sortColumn").value();
			String ascending = req.queryMap("ascending").value();
			boolean isAscending = ascending.equals("true");
			return g.toJson(trainingDAO.sortPersonalTrainingsBy(sortColumn, isAscending, trainerId));
		});

		get("rest/sport-object/trainings/date", (req, res) ->{
			res.type("application/json");
			int sportObjectId = Integer.parseInt(req.queryMap("id").value());
			LocalDate trainingDate = LocalDate.parse(req.queryMap("date").value());

			List<Training> trainings = trainingDAO.findAllTrainingsInSportObjectForDate(sportObjectId, trainingDate);

			return g.toJson(trainings);
		});

		get("rest/sort/trainer-trainings/group", (req, res) ->{
			res.type("application/json");
			int trainerId = Integer.parseInt(req.queryMap("id").value());
			String sortColumn = req.queryMap("sortColumn").value();
			String ascending = req.queryMap("ascending").value();
			boolean isAscending = ascending.equals("true");
			return g.toJson(trainingDAO.sortNonPersonalTrainingsBy(sortColumn, isAscending, trainerId));
		});

		get("rest/sort/sport-object-trainings", (req, res) ->{
			res.type("application/json");
			int sportObjectId = Integer.parseInt(req.queryMap("id").value());
			String sortColumn = req.queryMap("sortColumn").value();
			String ascending = req.queryMap("ascending").value();
			boolean isAscending = ascending.equals("ascending");
			return g.toJson(trainingDAO.sortSportObjectTrainingsBy(sortColumn, isAscending, sportObjectId));
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

	private static void runThread(){
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					customerDAO.checkMembershipsDate();
				}catch (IOException ex){
					ex.printStackTrace();
				}
			}
		}, 0, 1, TimeUnit.DAYS);
	}
	
}
