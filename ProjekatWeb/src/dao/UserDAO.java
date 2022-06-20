package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonparsing.LocalDateConverter;
import model.ISerializable;
import model.LoginUser;
import model.User;


// ubaciti novu listu bez obzira na velicinu fajla

public class UserDAO implements ISerializable<String, User> {
	private HashMap<String, User> users;
	private String fileName;
	
	public UserDAO() {
		
	}
	
	public UserDAO(String fileName) {
		this.fileName = fileName;
		try {
			System.out.println("asfaf");
			users = deserialize();

		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
//	public User find(String username, String password) {
//		if (!users.containsKey(username)) {
//			return null;
//		}
//		User user = users.get(username);
//		if (!user.getPassword().equals(password)) {
//			return null;
//		}
//		return user;
//	}
//	
	public Collection<User> findAll() {
		return users.values();
	}
	
	public User findUserByUsername(String username) {
		return users.get(username);
	}
	
	public boolean contains(User user) {
		System.out.println(users.size());
		return users.containsKey(user.getUsername());
	}
	
	public void addUser(User user) throws IOException{
		users.put(user.getUsername(), user);
		List<User> usersList = new ArrayList<>(findAll());
		serialize(usersList, false);
	}
	
	public boolean validLogin(LoginUser loginUser) throws IOException{
		User user = users.get(loginUser.getUsername());
		if(user == null) {
			return false;
		}
		return user.getPassword().equals(loginUser.getPassword());
	}
	
	@Override
	public void serialize(List<User> objectList, boolean append) throws IOException{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(objectList, writer);
		writer.flush();
		writer.close();
	}

	@Override
	public HashMap<String, User> deserialize() throws IOException{
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		User[] usersA = gson.fromJson(reader, User[].class);
		HashMap<String, User> output; 
		output = new HashMap<String, User>();
		if(usersA != null) { 
			for(User u: usersA) {
				output.put(u.getUsername(), u);
			}
		}
		return output;
	}

}
