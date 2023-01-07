package model;

import com.google.gson.annotations.Expose;

import java.time.LocalTime;

public class Training extends Offer{
	@Expose
	private Trainer trainer;

	public Training(int id) {
		super(id);
	}

	public Training(Offer o, Trainer t){
		super(o);
		this.trainer = t;
	}

	public Training(int id, String name, String type, String imagePath, String description, int duration, SportsObject sportsObject, Trainer trainer) {
		super(id, name, type, imagePath, description, duration, sportsObject);
		this.trainer = trainer;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
}
