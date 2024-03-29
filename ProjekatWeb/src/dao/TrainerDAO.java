package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonparsing.LocalDateConverter;
import jsonparsing.LocalDateTimeConverter;
import model.*;

public class TrainerDAO implements ISerializable<String, Trainer>{
	private UserDAO userDAO;
	
	private HashMap<String, Trainer> trainers;
	private String fileName;
	
	public TrainerDAO() {}
	
	public TrainerDAO(String fileName, UserDAO userDAO) {
		this.fileName = fileName;
		this.userDAO = userDAO;
		try {
			trainers = deserialize();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public Trainer findById(int trainerId){
		for(Trainer t: trainers.values()){
			if(t.getId() == trainerId){
				return t;
			}
		}
		return null;
	}

	public Collection<Trainer> findAll() {
		return trainers.values();
	}
	
	public void addTrainer(Trainer trainer) throws IOException{
		userDAO.addUser(trainer, Role.trainer);
		trainer.setTrainingHistoryList(new ArrayList<>());
		trainers.put(trainer.getUsername(), trainer);
		List<Trainer> trainersList = new ArrayList<>(findAll());
		serialize(trainersList, false);
	}
	
	public List<Trainer> findTrainerBySportsObjectId(int sportsObjectId){
		List<Trainer> output = new ArrayList<>();
		for(Trainer t: trainers.values()) {
			for(TrainingHistory trainingHistory: t.getTrainingHistoryList()){
				if(trainingHistory.getTraining().getSportsObject().getId() == sportsObjectId){
					output.add(t);
					break;
				}
			}
		}
		return output;
	}

	public void addTrainingHistoryToTrainer(TrainingHistory trainingHistory) throws IOException{
		for(Trainer t: trainers.values()){
			if(t.getId() == trainingHistory.getTrainer().getId()){
				List<TrainingHistory> trainingHistoryList = t.getTrainingHistoryList();
				trainingHistoryList.add(trainingHistory);
				t.setTrainingHistoryList(trainingHistoryList);
			}
		}
		List<Trainer> trainerList = new ArrayList<>(findAll());
		serialize(trainerList, false);
	}
	
	@Override
	public void serialize(List<Trainer> objectList, boolean append) throws IOException {
		GsonBuilder builder = new GsonBuilder()
				.setExclusionStrategies(new UserExcludingStrategy())
						.setExclusionStrategies(new TrainerExcludingStrategy());
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(objectList, writer);
		writer.flush();
		writer.close();
	}
	
	@Override
	public HashMap<String, Trainer> deserialize() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		Gson gson = builder.create();
		Trainer[] trainersA = gson.fromJson(reader, Trainer[].class);
		HashMap<String, Trainer> output;
		output = new HashMap<String, Trainer>();
		if(trainersA != null) {
			for(Trainer t: trainersA) {
				User user = userDAO.findUserByUsername(t.getUsername());
				t.setId(user.getId());
				t.setBirthdate(user.getBirthdate());
				t.setGender(user.getGender());
				t.setLastName(user.getLastName());
				t.setName(user.getName());
				t.setPassword(user.getPassword());
				t.setRole(Role.trainer);
				output.put(t.getUsername(), t);
			}
		}
		return output;
	}
}
