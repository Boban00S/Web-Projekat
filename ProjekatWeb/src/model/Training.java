package model;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Training extends Offer{
	@Expose
	private Trainer trainer;
	@Expose
	private LocalDateTime trainingDate;

	@Expose
	private boolean isPersonal;

	public Training(int id) {
		super(id);
	}

	public Training(Offer o, Trainer t, LocalDateTime trainingDate, boolean isPersonal){
		super(o);
		this.trainer = t;
		this.trainingDate = trainingDate;
		this.isPersonal = isPersonal;
	}

	public Training(int id, String name, String type, String imagePath, String description, int duration, SportsObject sportsObject, Trainer trainer, LocalDateTime trainingDate, boolean isPersonal) {
		super(id, name, type, imagePath, description, duration, sportsObject);
		this.trainer = trainer;
		this.trainingDate = trainingDate;
		this.isPersonal = isPersonal;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public LocalDateTime getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(LocalDateTime trainingDate) {
		this.trainingDate = trainingDate;
	}

	public boolean isPersonal() {
		return isPersonal;
	}

	public void setPersonal(boolean personal) {
		isPersonal = personal;
	}
}
