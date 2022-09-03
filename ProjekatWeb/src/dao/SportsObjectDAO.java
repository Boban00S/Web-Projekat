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
import model.SportsObject;
import model.ISerializable;
import model.User;

public class SportsObjectDAO implements ISerializable<Integer, SportsObject> {
	private HashMap<Integer, SportsObject> sportsObjects;
	private String fileName;
	
	public SportsObjectDAO() {
		
	}
	
	public SportsObjectDAO(String fileName) {
		this.fileName = fileName;
		try {
			sportsObjects = deserialize();

		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public SportsObject findById(int id) {
		for(SportsObject sp: sportsObjects.values()) {
			if(sp.getId() == id) {
				return sp;
			}
		}
		return null;
	}
	
	public Collection<SportsObject> findAll() {
		return sportsObjects.values();
	}
	
	@Override
	public void serialize(List<SportsObject> SportsObjectList, boolean append) throws IOException{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(SportsObjectList, writer);
		writer.flush();
		writer.close();
	}

	@Override
	public HashMap<Integer, SportsObject> deserialize() throws IOException{
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		SportsObject[] SportsObjectsA = gson.fromJson(reader, SportsObject[].class);
		HashMap<Integer, SportsObject> output; 
		output = new HashMap<Integer, SportsObject>();
		if(SportsObjectsA != null) { 
			for(SportsObject b: SportsObjectsA) {
				output.put(b.getId(), b);
			}
		}
		return output;
	}

}
