package dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import model.ISerializable;
import model.TrainingHistory;

public class TrainingHistoryDAO implements ISerializable<String, TrainingHistory> {
	//	private CustomerDAO customerDAO = new CustomerDAO("static/customers.json");
		private TrainerDAO trainerDAO = new TrainerDAO("data/trainers.json");
	//	private TrainingDAO trainingDAO = new TrainingDAO("static/trainings.json");
	
	@Override
	public void serialize(List<TrainingHistory> objectList, boolean append) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public HashMap<String, TrainingHistory> deserialize() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
