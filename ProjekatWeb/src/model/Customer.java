package model;

import java.time.LocalDate;
import java.util.List;

public class Customer extends User{
	private int points;
	private Membership membership;
	private List<Integer> sportsObjectAttended;
	private CustomerType customerType;
	
	
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(int id, String username, String password, String name, String lastName, Gender gender,
			LocalDate birthdate, Role role) {
		super(id, username, password, name, lastName, gender, birthdate, role);
		// TODO Auto-generated constructor stub
	}
	public Customer(int points, Membership membership, List<Integer> sportsObjectAttended, CustomerType customerType) {
		super();
		this.points = points;
		this.membership = membership;
		this.sportsObjectAttended = sportsObjectAttended;
		this.customerType = customerType;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public Membership getMembership() {
		return membership;
	}
	public void setMembership(Membership membership) {
		this.membership = membership;
	}
	public List<Integer> getSportsObjectAttended() {
		return sportsObjectAttended;
	}
	public void setSportsObjectAttended(List<Integer> sportsObjectAttended) {
		this.sportsObjectAttended = sportsObjectAttended;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
}
