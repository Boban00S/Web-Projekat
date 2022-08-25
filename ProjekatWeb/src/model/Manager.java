package model;

import java.time.LocalDate;

public class Manager extends User{
	private SportsObject sportsObject;

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Manager(int id, String username, String password, String name, String lastName, Gender gender,
			LocalDate birthdate, Role role) {
		super(id, username, password, name, lastName, gender, birthdate, role);
		// TODO Auto-generated constructor stub
	}



	public Manager(SportsObject sportsObject) {
		super();
		this.sportsObject = sportsObject;
	}



	public SportsObject getSportsObject() {
		return sportsObject;
	}



	public void setSportsObject(SportsObject sportsObject) {
		this.sportsObject = sportsObject;
	}
	
	
}
