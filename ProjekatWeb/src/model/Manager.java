package model;

import java.time.LocalDate;

public class Manager extends User{
	private int sportsObjectId;

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Manager(int id, String username, String password, String name, String lastName, Gender gender,
			LocalDate birthdate, Role role) {
		super(id, username, password, name, lastName, gender, birthdate, role);
		// TODO Auto-generated constructor stub
	}


	public int getSportsObject() {
		return sportsObjectId;
	}



	public void setSportsObject(int sportsObject) {
		this.sportsObjectId = sportsObject;
	}
	
	
}
