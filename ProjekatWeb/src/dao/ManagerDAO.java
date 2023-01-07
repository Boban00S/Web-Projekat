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
import model.Manager;
import model.Role;
import model.SportsObject;
import model.User;
import model.UserExcludingStrategy;

public class ManagerDAO implements ISerializable<String, Manager> {
	private UserDAO userDAO = new UserDAO("data/users.json");
	
	private HashMap<String, Manager> managers;
	private String fileName;
	
	public ManagerDAO() {}
	
	public ManagerDAO(String fileName) {
		this.fileName = fileName;
		try {
			// citanje menadzera iz fajla (username, Menadzer)
			managers = deserialize();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void addSportsObjectToManager(SportsObject s) throws IOException{
		for(Manager m: managers.values()) {
			if(m.getUsername().equals(s.getManagerUsername())) {
				m.setSportsObject(s.getId());
				List<Manager> managersList = new ArrayList<>(managers.values());
				serialize(managersList, false);
				break;
			}
		}

	}
	
	public Manager findManagerByUsername(String username) {
		for(Manager m: managers.values()) {
			if(m.getUsername().equals(username)) {
				return m;
			}
		}
		return null;
	}
	
	public Manager findManagerById(int userId) {
		for(Manager m: managers.values()) {
			if(m.getId() == userId) {
				return m;
			}
		}
		return null;
	}
	
	public List<Manager> findAvailableManagers(){
		List<Manager> output = new ArrayList<>();
		for(Manager m: managers.values()) {
			if(m.getSportsObject() == -1) {
				output.add(m);
			}
		}
		return output;
	}
	
	public Collection<Manager> findAll() {
		return managers.values();
	}
	
	public void addManager(Manager manager) throws IOException {
		// dodavanje novog korisnika jer je menadzer novi korisnik
				userDAO.addUser(manager, Role.manager);
				managers.put(manager.getUsername(), manager);
				// potrebna je lista za serijalizaciju
				List<Manager> managersList = new ArrayList<>(findAll());
				serialize(managersList, false);
	}
	
	@Override
	public void serialize(List<Manager> objectList, boolean append) throws IOException {
		GsonBuilder builder = new GsonBuilder()
				.setExclusionStrategies(new UserExcludingStrategy());
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(objectList, writer);
		writer.flush();
		writer.close();
	}
	
	@Override
	public HashMap<String, Manager> deserialize() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Manager[] managersA = gson.fromJson(reader, Manager[].class);
		HashMap<String, Manager> output;
		output = new HashMap<String, Manager>();
		if(managersA != null) {
			for(Manager m: managersA) {
				User user = userDAO.findUserByUsername(m.getUsername());
				m.setId(user.getId());
				m.setBirthdate(user.getBirthdate());
				m.setGender(user.getGender());
				m.setLastName(user.getLastName());
				m.setName(user.getName());
				m.setPassword(user.getPassword());
				m.setRole(Role.manager);
				output.put(m.getUsername(), m);
			}
		}
		return output;
	}
}
