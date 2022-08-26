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
import model.Administrator;
import model.ISerializable;
import model.Role;
import model.User;
import model.UserExcludingStrategy;

public class AdminDAO implements ISerializable<String, Administrator>{
	private UserDAO userDAO = new UserDAO("D:\\web-work-space\\Web-Projekat\\ProjekatWeb\\static\\users.json");
	
	private HashMap<String, Administrator> administrators;
	private String fileName;
	
	public AdminDAO() {
		
	}
	
	// fileName - naziv fajla gde se nalaze administratori
	public AdminDAO(String fileName) {
		this.fileName = fileName;
		try {
			// citanje administratora iz fajla (username, Administrator)
			administrators = deserialize();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Collection<Administrator> findAll() {
		return administrators.values();
	}
	
	
	public void addAdministrator(Administrator administrator) throws IOException{
		// dodavanje novog korisnika jer je administrator novi korisnik
		userDAO.addUser(administrator, Role.administrator);
		administrators.put(administrator.getUsername(), administrator);
		// potrebna je lista za serijalizaciju
		List<Administrator> administratorsList = new ArrayList<>(findAll());
		serialize(administratorsList, false);
	}
	
	@Override
	public void serialize(List<Administrator> objectList, boolean append) throws IOException {
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
	public HashMap<String, Administrator> deserialize() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Administrator[] adminsA = gson.fromJson(reader, Administrator[].class);
		HashMap<String, Administrator> output;
		output = new HashMap<String, Administrator>();
		if(adminsA != null) {
			for(Administrator a: adminsA) {
				User user = userDAO.findUserByUsername(a.getUsername());
				a.setId(user.getId());
				a.setBirthdate(user.getBirthdate());
				a.setGender(user.getGender());
				a.setLastName(user.getLastName());
				a.setName(user.getName());
				a.setPassword(user.getPassword());
				a.setRole(Role.administrator);
				output.put(a.getUsername(), a);
			}
		}
		return output;
	}
}
