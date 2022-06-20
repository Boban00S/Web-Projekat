package rest;

import java.util.ArrayList;
import java.util.List;

import dao.BuildingDAO;
import model.Building;
import model.BuildingType;
import model.Location;
import model.OpeningHours;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BuildingType bt = new BuildingType("gym");
		Location l1 = new Location("45.243525", "24.234152", "Filipa Visnjica 5", "Novi Sad 21000", "Serbia");
		Location l2 = new Location("45.243525", "24.234152", "Georgija Jaksica 1", "Loznica 15300", "Serbia");
		Building building1 = new Building(1,"Tajs", bt, "Tajs is a place where you will realize that physicall activity is enjoyable and where parents can spend quality time with their children in a very healthy way-by doing lots of different exercises together in one place, and that is Tajs.\"", l1, "../images/tajs.jpg", 7.1, "16-24", new OpeningHours(16, 0, 24, 0));
		Building building2 = new Building(2,"Vatrogas", bt, "We provide quality service in our 400 m2 gym with latest Technogym equipment.You can choose between personal or group trainings that we know you would like-just come and see for yourself.", l2, "../images/vatrogas.jpg", 9.5, "00-24", new OpeningHours(0, 0, 24, 0));
		List<Building> buildings = new ArrayList<>();
		buildings.add(building2);
		buildings.add(building1);
		BuildingDAO bd = new BuildingDAO("C:\\Users\\stani\\Desktop\\FTN\\WEB\\Web-Projekat\\ProjekatWeb\\static\\buildings.json");
		bd.serialize(buildings, false);
	}

}
