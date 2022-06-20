package model;

public class Building {
	private int id;
	private String name;
	private BuildingType type;
	private String content;
	private Location location;
	private String imagePath;
	private double averageGrade;
	private String workingHours;
	private OpeningHours openingHours;
	
	public Building() {
		
	}
	
	
	
	public Building(int id, String name, BuildingType type, String content, Location location, String imagePath,
			double averageGrade, String workingHours, OpeningHours openingHours) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.content = content;
		this.location = location;
		this.imagePath = imagePath;
		this.averageGrade = averageGrade;
		this.workingHours = workingHours;
		this.openingHours = openingHours;
	}



	public Building(int id, String name, BuildingType type, String content, Location location, String imagePath,
			double averageGrade, String workingHours) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.content = content;
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
	public BuildingType getType() {
		return type;
	}
	public void setType(BuildingType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
