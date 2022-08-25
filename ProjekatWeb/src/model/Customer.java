package model;

import java.util.List;

public class Customer extends User{
	private int points;
	private Membership membership;
	private List<SportsObject> sportsObjectAttended;
	private CustomerType customerType;
	
	public Customer(int points, Membership membership, List<SportsObject> sportsObjectAttended, CustomerType customerType) {
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
	public List<SportsObject> getSportsObjectAttended() {
		return sportsObjectAttended;
	}
	public void setSportsObjectAttended(List<SportsObject> sportsObjectAttended) {
		this.sportsObjectAttended = sportsObjectAttended;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
}
