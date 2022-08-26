package dao;

import java.util.HashMap;

import model.ISerializable;
import model.Trainer;

public class TrainerDAO implements ISerializable<String, Trainer>{
	private UserDAO userDAO = new UserDAO("D:\\web-work-space\\Web-Projekat\\ProjekatWeb\\static\\users.json");
	
	private HashMap<String, Trainer> trainers;
	private String fileName;
}
