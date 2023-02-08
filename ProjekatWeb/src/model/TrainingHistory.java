package model;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class TrainingHistory {
	@Expose
	private Integer id;
	@Expose
	private LocalDateTime startDateAndTime;
	@Expose
	private Customer customer;
	@Expose
	private Trainer trainer;
	@Expose
	private Training training;

	@Expose
	private boolean deleted = false;

	public TrainingHistory(Integer id, LocalDateTime startDateAndTime, Customer customer, Trainer trainer, Training training) {
		super();
		this.id = id;
		this.startDateAndTime = startDateAndTime;
		this.customer = customer;
		this.trainer = trainer;
		this.training = training;
	}

	public TrainingHistory(Integer id, LocalDateTime startDateAndTime, Customer customer, Trainer trainer, Training training, boolean deleted) {
		this.id = id;
		this.startDateAndTime = startDateAndTime;
		this.customer = customer;
		this.trainer = trainer;
		this.training = training;
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getStartDateAndTime() {
		return startDateAndTime;
	}
	public void setStartDateAndTime(LocalDateTime startDateAndTime) {
		this.startDateAndTime = startDateAndTime;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	public Training getTraining() {
		return training;
	}
	public void setTraining(Training training) {
		this.training = training;
	}

}
