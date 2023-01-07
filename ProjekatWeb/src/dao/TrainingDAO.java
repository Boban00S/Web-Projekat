package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jsonparsing.LocalDateConverter;
import model.*;

public class TrainingDAO implements ISerializable<String, Training> {

	private SportsObjectDAO sportsObjectDAO;
	private OfferDAO offerDAO;
	private TrainerDAO trainerDAO;
	private HashMap<String, Training> trainings;
	private String fileName;

	public TrainingDAO(){}

	public TrainingDAO(String fileName, OfferDAO offerDAO, TrainerDAO trainerDAO, SportsObjectDAO sportsObjectDAO){
		this.fileName = fileName;
		this.offerDAO = offerDAO;
		this.trainerDAO = trainerDAO;
		this.sportsObjectDAO = sportsObjectDAO;
		try{
			trainings = deserialize();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public Training findById(int trainingId){
		for(Training t: trainings.values()){
			if(t.getId() == trainingId){
				return t;
			}
		}
		return null;
	}

	public int getNextId() {
		int maxId = 0;
		for(Training t:trainings.values()) {
			if(t.getId()> maxId) {
				maxId = t.getId();
			}
		}
		return ++maxId;
	}

	public void addTraining(Training t) throws IOException{
		t.setId(getNextId());
		trainings.put(t.getImagePath(), t);
		List<Training> trainingList = new ArrayList<>(findAll());
		serialize(trainingList, false);
	}

	public Collection<Training> findAll(){ return trainings.values(); }

	@Override
	public void serialize(List<Training> objectList, boolean append) throws IOException {
		GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(objectList, writer);
		writer.flush();
		writer.close();
	}
	
	@Override
	public HashMap<String, Training> deserialize() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Training[] trainings1 = gson.fromJson(reader, Training[].class);
		HashMap<String, Training> output = new HashMap<>();
		if(trainings1 != null){
			for(Training t: trainings1){
				Training t1 = new Training(offerDAO.findById(t.getId()), t.getTrainer());
				t1.setTrainer(trainerDAO.findById(t1.getTrainer().getId()));
				t1.setSportsObject(sportsObjectDAO.findById(t1.getSportsObject().getId()));
				output.put(t1.getImagePath(), t1);
			}
		}
		return output;
	}
}
