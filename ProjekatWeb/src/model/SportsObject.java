package model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SportsObject {
	@Expose
	private int id;
	@Expose
	private String name;
	@Expose
	private SportsObjectType objectType;
	@Expose
	private List<Training> trainings;
	@Expose
	private boolean opened;
	@Expose
	private Location location;
	@Expose
	private String imagePath;
	@Expose
	private Double averageGrade;
	@Expose
	private String workingHours;
	@Expose
	private OpeningHours openingHours;
	@Expose
	private String description;
	@Expose
	private String managerUsername;
	
	public SportsObject() {
		
	}
	
	
	
	public SportsObject(int id, String name, SportsObjectType objectType, List<Training> trainings, boolean opened, Location location, String imagePath,
			Double averageGrade, String workingHours, OpeningHours openingHours, String description, String managerUsername) {
		super();
		this.id = id;
		this.name = name;
		this.objectType = objectType;
		this.trainings = trainings;
		this.opened = opened;
		this.location = location;
		this.imagePath = imagePath;
		this.averageGrade = averageGrade;
		this.workingHours = workingHours;
		this.openingHours = openingHours;
		this.description = description;
		this.managerUsername = managerUsername;
	}



	public SportsObject(int id, String name, SportsObjectType objectType, List<Training> trainings, boolean opened, Location location, String imagePath,
			Double averageGrade, String workingHours) {
		super();
		this.id = id;
		this.name = name;
		this.objectType = objectType;
		this.trainings = trainings;
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


	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
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
