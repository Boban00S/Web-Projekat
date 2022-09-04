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
import model.Customer;
import model.ISerializable;
import model.Role;
import model.User;
import model.UserExcludingStrategy;

public class CustomerDAO implements ISerializable<String, Customer> {
	private UserDAO userDAO = new UserDAO("data/users.json");
	
	private HashMap<String, Customer> customers;
	private String fileName;
	
	public CustomerDAO() {}
	
	public CustomerDAO(String fileName) {
		this.fileName = fileName;
		try {
			customers = deserialize();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public List<Customer> findCustomersBySportsObjectId(int sportsObjectId){
		List<Customer> output = new ArrayList<>();
		for(Customer c: customers.values()) {
			for(int i: c.getSportsObjectAttended()) {
				if(i == sportsObjectId) {
					output.add(c);
					break;
				}
			}
		}
		return output;
	}
	
	public Collection<Customer> findAll(){
		return customers.values();
	}
	
	public void addCustomer(Customer customer) throws IOException{
		userDAO.addUser(customer, Role.customer);
		customers.put(customer.getUsername(), customer);
		List<Customer> customersList = new ArrayList<>(findAll());
		serialize(customersList, false);
	}
	
	@Override
	public void serialize(List<Customer> objectList, boolean append) throws IOException {
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
	public HashMap<String, Customer> deserialize() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Customer[] customersA = gson.fromJson(reader, Customer[].class);
		HashMap<String, Customer> output;
		output = new HashMap<String, Customer>();
		if(customersA != null) {
			for(Customer m: customersA) {
				User user = userDAO.findUserByUsername(m.getUsername());
				m.setId(user.getId());
				m.setBirthdate(user.getBirthdate());
				m.setGender(user.getGender());
				m.setLastName(user.getLastName());
				m.setName(user.getName());
				m.setPassword(user.getPassword());
				m.setRole(Role.customer);
				output.put(m.getUsername(), m);
			}
		}
		return output;
	}

}