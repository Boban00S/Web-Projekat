package model;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.util.List;

public class Trainer extends User{

	private List<TrainingHistory> trainingHistoryList;


	public Trainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Trainer(List<TrainingHistory> trainingHistoryList) {
		this.trainingHistoryList = trainingHistoryList;
	}

	public Trainer(int id, String username, String password, String name, String lastName, Gender gender, LocalDate birthdate, Role role, List<TrainingHistory> trainingHistoryList) {
		super(id, username, password, name, lastName, gender, birthdate, role);
		this.trainingHistoryList = trainingHistoryList;
	}

	public Trainer(int id, String username, String password, String name, String lastName, Gender gender, LocalDate birthdate, Role role, CustomerType customerType, List<TrainingHistory> trainingHistoryList) {
		super(id, username, password, name, lastName, gender, birthdate, role, customerType);
		this.trainingHistoryList = trainingHistoryList;
	}

	public Trainer(int id, String username, String password, String name, String lastName, Gender gender, LocalDate birthdate, Role role, CustomerType customerType, boolean deleted, List<TrainingHistory> trainingHistoryList) {
		super(id, username, password, name, lastName, gender, birthdate, role, customerType, deleted);
		this.trainingHistoryList = trainingHistoryList;
	}

	public Trainer(int id, String username, String password, String name, String lastName, Gender gender,
				   LocalDate birthdate, Role role) {
		super(id, username, password, name, lastName, gender, birthdate, role);
		// TODO Auto-generated constructor stub
	}

	public List<TrainingHistory> getTrainingHistoryList() {
		return trainingHistoryList;
	}

	public void setTrainingHistoryList(List<TrainingHistory> trainingHistoryList) {
		this.trainingHistoryList = trainingHistoryList;
	}


}
