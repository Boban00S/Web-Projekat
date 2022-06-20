package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonparsing.LocalDateConverter;
import model.Building;
import model.ISerializable;
import model.User;

public class BuildingDAO implements ISerializable<Integer, Building> {
	private HashMap<Integer, Building> buildings;
	private String fileName;
	
	public BuildingDAO() {
		
	}
	
	public BuildingDAO(String fileName) {
		this.fileName = fileName;
		try {
			buildings = deserialize();

		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Collection<Building> findAll() {
		return buildings.values();
	}
	
	@Override
	public void serialize(List<Building> buildingList, boolean append) throws IOException{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(buildingList, writer);
		writer.flush();
		writer.close();
	}

	@Override
	public HashMap<Integer, Building> deserialize() throws IOException{
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Building[] buildingsA = gson.fromJson(reader, Building[].class);
		HashMap<Integer, Building> output; 
		output = new HashMap<Integer, Building>();
		if(buildingsA != null) { 
			for(Building b: buildingsA) {
				output.put(b.getId(), b);
			}
		}
		return output;
	}

}
