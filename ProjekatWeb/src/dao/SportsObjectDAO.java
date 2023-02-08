package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonparsing.LocalDateConverter;
import jsonparsing.LocalDateTimeConverter;
import model.*;

public class SportsObjectDAO implements ISerializable<String, SportsObject> {
	private HashMap<String, SportsObject> sportsObjects;
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
	
	public void addObject(SportsObject sportsObject) throws IOException{
		sportsObject.setId(getNextId());
		sportsObjects.put(sportsObject.getName(), sportsObject);
		List<SportsObject> sportsObjectList = new ArrayList<>(findAll());
		serialize(sportsObjectList, false);
	}

	public SportsObject editObject(SportsObject sportsObject) throws IOException{
		SportsObject oldObject = findById(sportsObject.getId());
		oldObject.setName(sportsObject.getName());
		oldObject.setImagePath(sportsObject.getImagePath());
		oldObject.setObjectType(sportsObject.getObjectType());
		oldObject.setDescription(sportsObject.getDescription());
		oldObject.setLocation(sportsObject.getLocation());
		oldObject.setAverageGrade(sportsObject.getAverageGrade());
//		trainingDAO.editTrainings(sportsObject.getTrainings());
		oldObject.setTrainings(sportsObject.getTrainings());
		oldObject.setType(sportsObject.getType());
		oldObject.setManagerUsername(sportsObject.getManagerUsername());

		serialize(new ArrayList<>(findAll()), false);

		return oldObject;
	}


	public int getNextId() {
		int maxId = 0;
		for(SportsObject s:sportsObjects.values()) {
			if(s.getId()> maxId) {
				maxId = s.getId();
			}
		}
		return ++maxId;
	}
	
	public Collection<SportsObject> findAll() {
		return sportsObjects.values();
	}

	public boolean contains(SportsObject sportsObject) {
		return sportsObjects.containsKey(sportsObject.getName());
	}
	
	public int getLastId() {
		int i = 0;
		for(SportsObject s: sportsObjects.values()) {
			if(s.getId() > i)
				i = s.getId();
		}
		return i;
	}

	public Collection<SportsObject> sortBy(String sortColumn, boolean isAscending){
		FlexibleCentersComparator comparator = new FlexibleCentersComparator(isAscending);
		comparator.setSortingBy(sortColumn);
		List<SportsObject> objects = new ArrayList<>(findAll());
		Collections.sort(objects, comparator);
		return objects;
	}
	
	@Override
	public void serialize(List<SportsObject> SportsObjectList, boolean append) throws IOException{
		GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(SportsObjectList, writer);
		writer.flush();
		writer.close();
	}

	@Override
	public HashMap<String, SportsObject> deserialize() throws IOException{
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		Gson gson = builder.create();
		SportsObject[] SportsObjectsA = gson.fromJson(reader, SportsObject[].class);
		HashMap<String, SportsObject> output; 
		output = new HashMap<String, SportsObject>();
		if(SportsObjectsA != null) { 
			for(SportsObject b: SportsObjectsA) {
				output.put(b.getName(), b);
			}
		}
		return output;
	}

}
