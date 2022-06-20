package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;
import java.security.Key;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.BuildingDAO;
import dao.UserDAO;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jsonparsing.LocalDateConverter;
import model.LoginUser;
import model.User;
import spark.Session;
import ws.WsHandler;

public class SparkAppMain {

	
	
	private static Gson g = getGson();
	private static UserDAO userDAO = new UserDAO("C:\\Users\\stani\\Desktop\\ProjekatWeb\\static\\users.json");
	private static BuildingDAO buildingDAO = new BuildingDAO("C:\\Users\\stani\\Desktop\\ProjekatWeb\\static\\buildings.json");
	
	
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
		
		get("/rest/registration", (req, res) -> {
			return userDAO.findAll();
		});
		
		get("/rest/homepage", (req, res) -> {
			res.status(200);
			return g.toJson(buildingDAO.findAll());
		});
		
		get("rest/testlogin", (req, res) ->{
			Session ss = req.session(true);
			User user = ss.attribute("user");
			res.status(200);
			if(user == null) {
				return ("No");
			}else {
				System.out.println(g.toJson(user));
				return g.toJson(user);
			}
		});
		
		
		post("/rest/registration", (req, res) ->{
			res.type("application/json");
			String user = req.body();
			System.out.println(user);
			User u = g.fromJson(user, User.class);
			boolean contains = userDAO.contains(u);
			if(contains == true) {
				res.status(400);
				return ("No");
			}
			userDAO.addUser(u);
			Session ss = req.session(true);
			ss.attribute("user", u);
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
			return ("Yes");
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
		Gson gson = builder.create();
		return gson;
	}
	
}
