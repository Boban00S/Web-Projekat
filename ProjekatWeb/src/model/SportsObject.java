package model;

import java.util.List;

public class SportsObject {
	private int id;
	private String name;
	private SportsObjectType objectType;
	private List<Offer> offers;
	private boolean opened;
	private Location location;
	private String imagePath;
	private Double averageGrade;
	private String workingHours;
	private OpeningHours openingHours;
	private String description;
	private String managerUsername;
	
	public SportsObject() {
		
	}
	
	
	
	public SportsObject(int id, String name, SportsObjectType objectType, List<Offer> offers, boolean opened, Location location, String imagePath,
			Double averageGrade, String workingHours, OpeningHours openingHours, String description, String managerUsername) {
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
		this.managerUsername = managerUsername;
	}



	public SportsObject(int id, String name, SportsObjectType objectType, List<Offer> offers, boolean opened, Location location, String imagePath,
			Double averageGrade, String workingHours) {
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



	public List<Offer> getOffers() {
		return offers;
	}


	public void setOffers(List<Offer> offers) {
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
	public Double getAverageGrade() {
		return averageGrade;
	}
	public void setAverageGrade(Double averageGrade) {
		this.averageGrade = averageGrade;
	}
	public String getWorkingHours() {
		return workingHours;
	}
	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}



	public boolean isOpened() {
		return opened;
	}



	public void setOpened(boolean opened) {
		this.opened = opened;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getManagerUsername() {
		return managerUsername;
	}



	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}



	
	
	

}
