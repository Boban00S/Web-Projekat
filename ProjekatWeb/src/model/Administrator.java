package model;

import java.time.LocalDate;

public class Administrator extends User {

	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrator(int id, String username, String password, String name, String lastName, Gender gender,
			LocalDate birthdate, Role role) {
		super(id, username, password, name, lastName, gender, birthdate, role);
		// TODO Auto-generated constructor stub
	}

	
	
}
