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

public class TrainingDAO implements ISerializable<String, Training> {

	private SportsObjectDAO sportsObjectDAO;
	private TrainerDAO trainerDAO;
	private HashMap<String, Training> trainings;
	private String fileName;

	public TrainingDAO(){}

	public TrainingDAO(String fileName, TrainerDAO trainerDAO, SportsObjectDAO sportsObjectDAO){
		this.fileName = fileName;
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
			if(t.getId() == trainingId && !t.isDeleted()){
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

	public List<Training> findPersonalTrainingsByTrainerId(int trainerId){
		List<Training> output = new ArrayList<>();
		for(Training t: trainings.values()){
			if(t.getTrainer() != null){
				if(t.getTrainer().getId() == trainerId && this.isPersonal(t) && !t.isDeleted()){
					output.add(t);
				}
			}
		}
		return output;
	}

	public List<Training> getTrainingsByIds(List<Training> trainingsIds){
		List<Training> trainings1 = new ArrayList<>();
		for(Training t: trainingsIds){
			Training t1 = findById(t.getId());
			if(!t1.isDeleted())
				trainings1.add(t1);
		}
		return trainings1;
	}

	public boolean isPersonal(Training t){
		return t.getType().equals("Personal");
	}

	public void editTrainings(Training t) throws IOException{
		boolean found = false;
		for(Training t1: trainings.values()){
			if(t1.getId() == t.getId()){
				found = true;
				t1.setName(t.getName());
				t1.setDescription(t.getDescription());
				t1.setDuration(t.getDuration());
				t1.setType(t.getType());
				t1.setImagePath(t.getImagePath());
				t1.setPrice(t.getPrice());
				t1.setDate(t.getDate());
				t1.setTrainer(t.getTrainer());
			}
		}
		if(!found){
			trainings.put(t.getImagePath(), t);
		}
		serialize(new ArrayList<>(findAll()), false);
	}

	public void editTrainings(List<Training> newTrainings) throws IOException{
		for(Training t1: newTrainings){
			editTrainings(t1);
		}
	}

	public void deleteById(int trainingId) throws IOException{
		for(Training t: trainings.values()){
			if(t.getId() == trainingId){
				t.setDeleted(true);
				trainings.put(t.getImagePath(), t);
				break;
			}
		}
		List<Training> trainingList = new ArrayList<>(findAll());
		serialize(trainingList, false);
	}

	public List<Training> sortPersonalTrainingsBy(String sortColumn, boolean isAscending, int trainerId){
		List<Training> personalTrainings = findPersonalTrainingsByTrainerId(trainerId);
		FlexibleTrainerTrainingsComparator comparator = new FlexibleTrainerTrainingsComparator(isAscending);
		comparator.setSortingBy(sortColumn);
		Collections.sort(personalTrainings, comparator);
		return personalTrainings;
	}

	public List<Training> sortNonPersonalTrainingsBy(String sortColumn, boolean isAscending, int trainerId){
		List<Training> nonPersonalTrainings = findNonPersonalTrainingsByTrainerId(trainerId);
		FlexibleTrainerTrainingsComparator comparator = new FlexibleTrainerTrainingsComparator(isAscending);
		comparator.setSortingBy(sortColumn);
		Collections.sort(nonPersonalTrainings, comparator);
		return nonPersonalTrainings;
	}

	public List<Training> sortSportObjectTrainingsBy(String sortColumn, boolean isAscending, int sportObjectId){
		List<Training> sportObjectTrainings = findAllTrainingsInSportObject(sportObjectId);
		FlexibleTrainerTrainingsComparator comparator = new FlexibleTrainerTrainingsComparator(isAscending);
		comparator.setSortingBy(sortColumn);
		Collections.sort(sportObjectTrainings, comparator);
		return sportObjectTrainings;
	}

	public List<Training> findAllTrainingsInSportObject(int sportObjectId){
		List<Training> output = new ArrayList<>();
		for(Training t: trainings.values()){
			if(t.getSportsObject().getId() == sportObjectId && !t.isDeleted()){
				output.add(t);
			}
		}
		return output;
	}

	public List<Training> findNonPersonalTrainingsByTrainerId(int trainerId){
		List<Training> output = new ArrayList<>();
		for(Training t: trainings.values()){
			if(t.getTrainer() != null){
				if(t.getTrainer().getId() == trainerId && this.isNonPersonal(t) && !t.isDeleted()){
					output.add(t);
				}
			}
		}
		return output;
	}

	public List<Training> findAvailableTrainings(int sportsObjectId){
		List<Training> output = new ArrayList<>();
		for(Training t: trainings.values()){
			if(t.getDate().isAfter(LocalDateTime.now()) && t.getDate().isBefore(LocalDateTime.now().plusDays(1))
			&& t.getSportsObject().getId() == sportsObjectId && !t.isDeleted()){
				output.add(t);
			}
		}
		return output;
	}

	public List<Training> findAllTrainingsInSportObjectForDate(int sportsObjectId, LocalDate trainingDate){
		List<Training> output = new ArrayList<>();
		for(Training t: trainings.values()){
			if(t.getSportsObject().getId() == sportsObjectId && t.getDate().toLocalDate().isEqual(trainingDate) && !t.isDeleted()){
				output.add(t);
			}
		}
		return output;
	}

	public List<Training> findBySportsObjectId(int sportsObjectId){
		List<Training> output = new ArrayList<>();
		for(Training t: trainings.values()){
			if(t.getSportsObject().getId() == sportsObjectId && !t.isDeleted()){
				output.add(t);
			}
		}
		return output;
	}

	public boolean isNonPersonal(Training t){
		return t.getType().equals("Group");
	}

	@Override
	public void serialize(List<Training> objectList, boolean append) throws IOException {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
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
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		Gson gson = builder.create();
		Training[] trainings1 = gson.fromJson(reader, Training[].class);
		HashMap<String, Training> output = new HashMap<>();
		if(trainings1 != null){
			for(Training t: trainings1){
				if(t.getTrainer() != null)
					t.setTrainer(trainerDAO.findById(t.getTrainer().getId()));
				t.setSportsObject(sportsObjectDAO.findById(t.getSportsObject().getId()));
				if(!t.isDeleted())
					output.put(t.getImagePath(), t);
			}
		}
		return output;
	}
}
