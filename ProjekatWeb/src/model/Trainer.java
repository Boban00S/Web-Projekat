package model;

import java.time.LocalDate;
import java.util.List;

public class Trainer extends User{

	public Trainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Trainer(int id, String username, String password, String name, String lastName, Gender gender,
			LocalDate birthdate, Role role) {
		super(id, username, password, name, lastName, gender, birthdate, role);
		// TODO Auto-generated constructor stub
	}

}
