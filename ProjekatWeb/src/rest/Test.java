package rest;

import java.util.ArrayList;
import java.util.List;

import dao.BuildingDAO;
import model.Building;
import model.BuildingType;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BuildingType bt = new BuildingType("gym");
		Building building1 = new Building(1,"Tajs", bt, "pool", "Novi Sad", "static/images/tajs.jpg", 7.1, "08-20");
		Building building2 = new Building(2,"Vatrogas", bt, "sauna", "Loznica", "static/images/vatrogas.jpg", 9.5, "00-24");
		List<Building> buildings = new ArrayList<>();
		buildings.add(building2);
		buildings.add(building1);
		BuildingDAO bd = new BuildingDAO("C:\\Users\\stani\\Desktop\\ProjekatWeb\\static\\buildings.json");
		bd.serialize(buildings, false);
	}

}
