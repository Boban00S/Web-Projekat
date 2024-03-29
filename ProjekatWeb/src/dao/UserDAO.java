package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonparsing.LocalDateConverter;
import jsonparsing.LocalDateTimeConverter;
import model.*;



public class UserDAO implements ISerializable<String, User> {
	private HashMap<String, User> users;
	private String fileName;
	
	public UserDAO() {
		
	}
	
	public UserDAO(String fileName) {
		this.fileName = fileName;
		try {
			users = deserialize();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public Collection<User> findAll() {
		return users.values();
	}
	
	public User findUserByUsername(String username) {
		return users.get(username);
	}
	
	public User findUserById(int userId) {
		for(User u: users.values()) {
			if(u.getId() == userId) {
				return u;
			}
		}
		return null;
	}
	
	public boolean contains(User user) {
		return users.containsKey(user.getUsername());
	}
	
	private int getNextId() {
		int maxId = 0;
		for(User u:users.values()) {
			if(u.getId()> maxId) {
				maxId = u.getId();
			}
		}
		return ++maxId;
	}
	
	public void addCustomer(User user) throws IOException{
		user.setId(getNextId());
		user.setRole(Role.customer);
		users.put(user.getUsername(), user);
		List<User> usersList = new ArrayList<>(findAll());
		serialize(usersList, false);
	}

	public List<User> sortBy(String sortColumn, boolean isAscending, CustomerDAO customerDAO){
		FlexibleUsersComparator comparator = new FlexibleUsersComparator(isAscending, customerDAO);
		comparator.setSortingBy(sortColumn);
		List<User> usersList = new ArrayList<>(findAll());
		Collections.sort(usersList, comparator);
		return usersList;
	}
	
	public void addUser(User user, Role role) throws IOException{
		user.setId(getNextId());
		user.setRole(role);
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
	
	public boolean containsOtherUsername(User u) {
		for(User userFromList: users.values()) {
			if(userFromList.getUsername() == u.getUsername() &&
					userFromList.getId() != u.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public void editUser(User u) throws IOException{
		for(User userFromList: users.values()) {
			if(userFromList.getId() == u.getId()) {
				userFromList.setName(u.getName());
				userFromList.setUsername(u.getUsername());
				userFromList.setPassword(u.getPassword());
				userFromList.setLastName(u.getLastName());
				userFromList.setGender(u.getGender());
				userFromList.setBirthdate(u.getBirthdate());
				userFromList.setRole(u.getRole());
				List<User> usersList = new ArrayList<>(findAll());
				serialize(usersList, false);
			}
		}
	}
	
	
	@Override
	public void serialize(List<User> objectList, boolean append) throws IOException{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
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
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
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
