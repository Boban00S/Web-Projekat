package model;

import java.time.LocalDate;
import com.google.gson.annotations.Expose;

public class User {
	@Expose
	private int id;
	private String username;
	private String password;
	private String name;
	private String lastName;
	private Gender gender;
	private LocalDate birthdate;
	private Role role;
	private CustomerType customerType;
	private boolean deleted = false;


	public User() {
		
	}

	public User(int id, String username, String password, String name, String lastName, Gender gender,
				LocalDate birthdate, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthdate = birthdate;
		this.role = role;
	}

	public User(int id, String username, String password, String name, String lastName, Gender gender,
			LocalDate birthdate, Role role, CustomerType customerType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthdate = birthdate;
		this.role = role;
		this.customerType = customerType;
	}

	public User(int id, String username, String password, String name, String lastName, Gender gender, LocalDate birthdate, Role role, CustomerType customerType, boolean deleted) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthdate = birthdate;
		this.role = role;
		this.customerType = customerType;
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
