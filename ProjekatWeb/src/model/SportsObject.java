package model;

import java.util.List;

public class SportsObject {
	private int id;
	private String name;
	private SportsObjectType objectType;
	private List<String> offers;
	private boolean opened;
	private Location location;
	private String imagePath;
	private double averageGrade;
	private String workingHours;
	private OpeningHours openingHours;
	private String description;
	
	public SportsObject() {
		
	}
	
	
	
	public SportsObject(int id, String name, SportsObjectType objectType, List<String> offers, boolean opened, Location location, String imagePath,
			double averageGrade, String workingHours, OpeningHours openingHours, String description) {
		super();
		this.id = id;
		this.name = name;
		this.objectType = objectType;
		this.offers = offers;
		this.opened = opened;
		this.location = location;
		this.imagePath = imagePath;
		this.averageGrade = averageGrade;
		this.workingHours = workingHours;
		this.openingHours = openingHours;
		this.description = description;
	}



	public SportsObject(int id, String name, SportsObjectType objectType, List<String> offers, boolean opened, Location location, String imagePath,
			double averageGrade, String workingHours) {
		super();
		this.id = id;
		this.name = name;
		this.objectType = objectType;
		this.offers = offers;
		this.opened = opened;
		this.location = location;
		this.imagePath = imagePath;
		this.averageGrade = averageGrade;
		this.workingHours = workingHours;
	}
	
	public OpeningHours getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
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
	public SportsObjectType getType() {
		return objectType;
	}
	public void setType(SportsObjectType objectType) {
		this.objectType = objectType;
	}
	
	public SportsObjectType getObjectType() {
		return objectType;
	}



	public void setObjectType(SportsObjectType objectType) {
		this.objectType = objectType;
	}



	public List<String> getOffers() {
		return offers;
	}


	public void setOffers(List<String> offers) {
		this.offers = offers;
	}



	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public double getAverageGrade() {
		return averageGrade;
	}
	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}
	public String getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}
	
	
	
	

}
