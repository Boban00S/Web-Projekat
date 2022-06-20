package model;

import java.time.LocalDate;




public class User {
	
	private int id;
	private String name;
	private String lastName;
	private LocalDate birthdate;
	private Gender gender;
	private String password;	
	private String username;
	private CustomerType customerType;
	
	public User() {
		
	}
	
	public User(int id, String name, String lastName, LocalDate birthdate, Gender gender, String password,
			String username, CustomerType customerType) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.gender = gender;
		this.password = password;
		this.username = username;
		this.customerType = customerType;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
