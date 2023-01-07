package model;

import com.google.gson.annotations.Expose;

public class Offer {
    @Expose
    private int id;
    private String name;
    private String type;
    private String imagePath;
    private String description;
    private int duration;
    private SportsObject sportsObject;

    public Offer(int id){
        this.id = id;
    }

    public Offer(int id, String name, String type, String imagePath, String description, int duration, SportsObject sportsObject) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imagePath = imagePath;
        this.description = description;
        this.duration = duration;
        this.sportsObject = sportsObject;
    }

    public Offer(Offer o1){
        this.id = o1.getId();
        this.name = o1.getName();
        this.type = o1.getType();
        this.imagePath = o1.getImagePath();
        this.description = o1.getDescription();
        this.duration = o1.getDuration();
        this.sportsObject = o1.getSportsObject();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public SportsObject getSportsObject() {
        return sportsObject;
    }

    public void setSportsObject(SportsObject sportsObject) {
        this.sportsObject = sportsObject;
    }
}
