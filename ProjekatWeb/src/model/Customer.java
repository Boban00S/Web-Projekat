package model;

import java.time.LocalDate;
import java.util.List;

public class Customer extends User{
	private Float points;
	private Membership membership;
	private List<Integer> sportsObjectAttended;
	private int dailyUsageLeft;

	
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(int id, String username, String password, String name, String lastName, Gender gender,
			LocalDate birthdate, Role role, CustomerType customerType) {
		super(id, username, password, name, lastName, gender, birthdate, role, customerType);
		// TODO Auto-generated constructor stub
	}
	public Customer(Float points, Membership membership, List<Integer> sportsObjectAttended) {
		super();
		this.points = points;
		this.membership = membership;
		this.sportsObjectAttended = sportsObjectAttended;
	}
	public Float getPoints() {
		return points;
	}
	public void setPoints(Float points) {
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

	public int getDailyUsageLeft() {
		return dailyUsageLeft;
	}

	public void setDailyUsageLeft(int dailyUsageLeft) {
		this.dailyUsageLeft = dailyUsageLeft;
	}
}
