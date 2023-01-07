package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonparsing.LocalDateConverter;
import model.FlexibleCentersComparator;
import model.Offer;
import model.SportsObject;
import model.ISerializable;

public class SportsObjectDAO implements ISerializable<String, SportsObject> {
	private HashMap<String, SportsObject> sportsObjects;
	private OfferDAO offerDAO;
	private String fileName;
	
	public SportsObjectDAO() {
		
	}
	
	public SportsObjectDAO(String fileName, OfferDAO offerDAO) {
		this.offerDAO = offerDAO;
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
		offerDAO.editOffers(sportsObject.getOffers());
		oldObject.setOffers(sportsObject.getOffers());
		oldObject.setType(sportsObject.getType());
		oldObject.setManagerUsername(sportsObject.getManagerUsername());

		serialize(new ArrayList<>(findAll()), false);

		return oldObject;
	}
	
	public SportsObject deleteOfferById(int sportObjectId, int offerId) throws IOException{
		SportsObject sportsObject = findById(sportObjectId);
		for(Offer o: sportsObject.getOffers()){
			if(o.getId() == offerId){
				sportsObject.getOffers().remove(o);
				break;
			}
		}
		offerDAO.deleteOfferById(offerId);

		return sportsObject;
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

	public Collection<SportsObject> sortBy(String sortColumn){
		FlexibleCentersComparator comparator = new FlexibleCentersComparator();
		comparator.setSortingBy(sortColumn);
		List<SportsObject> objects = new ArrayList<>(findAll());
		Collections.sort(objects, comparator);
		return objects;
	}
	
	@Override
	public void serialize(List<SportsObject> SportsObjectList, boolean append) throws IOException{
		GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
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
		Gson gson = builder.create();
		SportsObject[] SportsObjectsA = gson.fromJson(reader, SportsObject[].class);
		HashMap<String, SportsObject> output; 
		output = new HashMap<String, SportsObject>();
		if(SportsObjectsA != null) { 
			for(SportsObject b: SportsObjectsA) {
				if(b.getOffers() != null) {
					List<Offer> offers = offerDAO.getOffersByIds(b.getOffers());
					b.setOffers(offers);
				}
				output.put(b.getName(), b);
			}
		}
		return output;
	}

}
