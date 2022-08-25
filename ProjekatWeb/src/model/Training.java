package model;

import java.time.LocalTime;

public class Training {
	private int id;
	private String name;
	private LocalTime duration;
	private String description;
	private String image;
	private SportsObject sportsObject;
	
	public Training(int id, String name, LocalTime duration, String description, String image,
			SportsObject sportsObject) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.description = description;
		this.image = image;
		this.sportsObject = sportsObject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalTime getDuration() {
		return duration;
	}
	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public SportsObject getSportsObject() {
		return sportsObject;
	}
	public void setSportsObject(SportsObject sportsObject) {
		this.sportsObject = sportsObject;
	}
	
}
