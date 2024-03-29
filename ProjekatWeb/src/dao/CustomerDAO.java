package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonparsing.LocalDateConverter;
import model.*;

public class CustomerDAO implements ISerializable<String, Customer> {
	private UserDAO userDAO;
	
	private HashMap<String, Customer> customers;
	private String fileName;
	
	public CustomerDAO() {}
	
	public CustomerDAO(String fileName, UserDAO userDAO) {
		this.fileName = fileName;
		this.userDAO = userDAO;
		try {
			customers = deserialize();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Customer findById(int customerId){
		for(Customer c: customers.values()){
			if(c.getId() == customerId){
				return c;
			}
		}
		return null;
	}

	public List<Customer> findCustomersBySportsObjectId(int sportsObjectId){
		List<Customer> output = new ArrayList<>();
		for(Customer c: customers.values()) {
			if(c.getSportsObjectAttended() != null){
				for(int i: c.getSportsObjectAttended()) {
					if(i == sportsObjectId) {
						output.add(c);
						break;
					}
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
		customer.setSportsObjectAttended(new ArrayList<>());
		customer.setPoints(0.0f);
		CustomerType customerType = new CustomerType("silver", 0, 0);
		customer.setCustomerType(customerType);
		customers.put(customer.getUsername(), customer);
		List<Customer> customersList = new ArrayList<>(findAll());
		serialize(customersList, false);
	}

	public void checkMembershipsDate() throws IOException{
		for(Customer customer: customers.values()) {
			if (customer.getMembership() != null) {
				if (customer.getMembership().getExpireDate().isBefore(LocalDate.now()) && customer.getMembership().isActive()) {
					Float points;
					if (customer.getMembership().getDailyUsage() == -1) {
						points = customer.getMembership().getPrice() / 1000 * customer.getDailyUsageLeft();
					} else {
						points = customer.getMembership().getPrice() / 1000 * (customer.getMembership().getDailyUsage() - customer.getDailyUsageLeft());
						if (customer.getDailyUsageLeft() > 2 * customer.getMembership().getDailyUsage() / 3) {
							points -= customer.getMembership().getPrice() / 1000 * 133 * 4;
						}

					}
					customer.setPoints(customer.getPoints() + points);
					customer.getMembership().setActive(false);
					customer.setDailyUsageLeft(0);
					if(customer.getPoints() > 2000){
						customer.getCustomerType().setName("silver");
					}else{
						customer.getCustomerType().setName("basic");
					}
				}
			}
		}
		List<Customer> customerList = new ArrayList<>(findAll());
		serialize(customerList, false);
	}


	public Float findPointsById(int id){
		return findById(id).getPoints();
	}

	public String getCustomerType(User user){
		if(user.getRole() == Role.customer){
			Customer customer = findById(user.getId());
			return customer.getCustomerType().getName();
		}else{
			return "No";
		}
	}
	public Customer addMembership(Customer c) throws IOException{
		Customer output = null;
		for(Customer c1: customers.values()){
			if(c1.getId() == c.getId()){
				c1.setMembership(c.getMembership());
				c1.setDailyUsageLeft(c.getDailyUsageLeft());
				output = c1;
			}
		}
		List<Customer> customerList = new ArrayList<>(findAll());
		serialize(customerList, false);

		return output;
	}

	public void editCustomer(Customer c) throws IOException{
		for(Customer c1: customers.values()){
			if(c1.getId() == c.getId()){
				customers.put(c1.getUsername(), c);
			}
		}
		List<Customer> customerList = new ArrayList<>(findAll());
		serialize(customerList, false);
	}

	public void addDailyUsage(int customerId) throws IOException{
		for(Customer c1: customers.values()){
			if(c1.getId() == customerId){
				if(c1.getDailyUsageLeft() != -1){
					c1.setDailyUsageLeft(c1.getDailyUsageLeft()+1);
				}
			}
		}
		List<Customer> customerList = new ArrayList<>(findAll());
		serialize(customerList, false);
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
