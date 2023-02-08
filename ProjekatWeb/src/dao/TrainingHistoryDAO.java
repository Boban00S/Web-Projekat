package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jsonparsing.LocalDateConverter;
import jsonparsing.LocalDateTimeConverter;
import model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class TrainingHistoryDAO implements ISerializable<String, TrainingHistory> {

	private TrainingDAO trainingDAO;
	private CustomerDAO customerDAO;
	private TrainerDAO trainerDAO;

	private HashMap<String, TrainingHistory> trainingHistories;
	private String fileName;

	public TrainingHistoryDAO(){}

	public TrainingHistoryDAO(String fileName, TrainingDAO trainingDAO, CustomerDAO customerDAO, TrainerDAO trainerDAO){
		this.fileName = fileName;
		this.trainingDAO = trainingDAO;
		this.customerDAO = customerDAO;
		this.trainerDAO = trainerDAO;
		try{
			trainingHistories = deserialize();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public int getNextId() {
		int maxId = 0;
		for(TrainingHistory t:trainingHistories.values()) {
			if(t.getId()> maxId) {
				maxId = t.getId();
			}
		}
		return ++maxId;
	}


	public Collection<TrainingHistory> findAll(){ return trainingHistories.values(); }

	public List<Training> findTrainingsByCustomerId(int customerId){
		HashMap<String, Training> output = new HashMap<>();
		for(TrainingHistory t: trainingHistories.values()){
			if(t.getCustomer().getId() == customerId && !t.isDeleted()){
				output.put(t.getTraining().getImagePath(), t.getTraining());
			}
		}
		List<Training> outputList = new ArrayList<>(output.values());
		return outputList;
	}


	public void addTrainingHistory(TrainingHistory trainingHistory) throws IOException{
		trainingHistory.setId(getNextId());
		trainingHistory.setStartDateAndTime(LocalDateTime.now());
		trainingHistories.put(trainingHistory.getId().toString(), trainingHistory);
		trainerDAO.addTrainingHistoryToTrainer(trainingHistory);
		List<TrainingHistory> trainingHistoryList = new ArrayList<>(findAll());
		serialize(trainingHistoryList, false);
	}

	public List<Training30Days> sortyBy(String sortColumn, boolean isAscending, Customer customer){
		List<Training30Days> training30DaysHistory = findLast30DaysOfTrainings(customer);
		FlexibleTrainingsComparator comparator = new FlexibleTrainingsComparator(isAscending);
		comparator.setSortingBy(sortColumn);
		Collections.sort(training30DaysHistory, comparator);
		return training30DaysHistory;
	}
	public List<Training30Days> findLast30DaysOfTrainings(Customer customer){
		List<Training30Days> output = new ArrayList<>();
		List<Training> uniqueTrainings = findTrainingsByCustomerId(customer.getId());
		for(Training t: uniqueTrainings){
			List<LocalDateTime> last30Days = new ArrayList<>();
			for(TrainingHistory tH: trainingHistories.values()){
				if(t.getId() == tH.getTraining().getId() && tH.getCustomer().getId() == customer.getId()){
					Period diff = Period.between(tH.getStartDateAndTime().toLocalDate(), LocalDateTime.now().toLocalDate());
					if(diff.getDays() < 30 && diff.getMonths() == 0 && diff.getYears() == 0){
						last30Days.add(tH.getStartDateAndTime());
					}
				}
			}
			Training30Days tD = new Training30Days(t.getId(), customer, t.getTrainer(), t, last30Days);
			output.add(tD);
		}
		return output;
	}

	public void deleteByTrainingId(int trainingId) throws IOException{
		for(TrainingHistory trainingHistory: trainingHistories.values()){
			if(trainingHistory.getTraining().getId() == trainingId){
				customerDAO.addDailyUsage(trainingHistory.getCustomer().getId());
				trainingHistory.setDeleted(true);
				trainingHistories.put(trainingHistory.getId().toString(), trainingHistory);
				List<TrainingHistory> trainingHistoryList = new ArrayList<>(trainingHistories.values());
				serialize(trainingHistoryList, false);
				break;
			}
		}

	}

	@Override
	public void serialize(List<TrainingHistory> objectList, boolean append) throws IOException {
		GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(objectList, writer);
		writer.flush();
		writer.close();
	}
	
	@Override
	public HashMap<String, TrainingHistory> deserialize() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		TrainingHistory[] trainingHistories1 = gson.fromJson(reader, TrainingHistory[].class);
		HashMap<String, TrainingHistory> output = new HashMap<>();
		if(trainingHistories1 != null){
			for(TrainingHistory t: trainingHistories1){
				Trainer trainer = trainerDAO.findById(t.getTrainer().getId());
				Customer customer = customerDAO.findById(t.getCustomer().getId());
				Training training = trainingDAO.findById(t.getTraining().getId());
				t.setTrainer(trainer);
				t.setCustomer(customer);
				t.setTraining(training);
				if(!t.isDeleted())
					output.put(t.getId().toString(), t);
			}
		}
		return output;
	}
}
