package rest;

import java.time.LocalDate;

import dao.AdminDAO;
import dao.TrainerDAO;
import dao.UserDAO;
import model.Administrator;
import model.Gender;
import model.Role;
import model.Trainer;

public class Test {
	private static UserDAO userDAO = new UserDAO("data/users.json");
	private static AdminDAO adminDAO = new AdminDAO("data/administrators.json");
	private static TrainerDAO trainerDAO = new TrainerDAO("data/trainers.json");

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		SportsObjectType bt = new SportsObjectType("gym");
//		Location l1 = new Location("45.243525", "24.234152", "Filipa Visnjica 5", "Novi Sad 21000", "Serbia");
//		Location l2 = new Location("45.243525", "24.234152", "Georgija Jaksica 1", "Loznica 15300", "Serbia");
//		List<String> offers = new ArrayList<String>();
//		offers.add("Gym");
//		offers.add("Pool");
//		
//		SportsObject SportsObject1 = new SportsObject(1, "Tajs", bt, offers, false, l1, "../images/tajs.jpg", 7.1, "16-24", new OpeningHours(16, 0, 24, 0),
//				"Tajs is a place where you will realize that physicall activity is enjoyable and where parents can spend quality time with their children in a very healthy way-by doing lots of different exercises together in one place, and that is Tajs." );
//		SportsObject SportsObject2 = new SportsObject(2, "Vatrogas", bt, offers, false, l2, "../images/vatrogas.jpg", 9.0, "00-24", new OpeningHours(0, 0, 24, 0),
//				"We provide quality service in our 400 m2 gym with latest Technogym equipment.You can choose between personal or group trainings that we know you would like-just come and see for yourself.");
//		List<SportsObject> SportsObjects = new ArrayList<>();
//		SportsObjects.add(SportsObject2);
//		SportsObjects.add(SportsObject1);
//		SportsObjectDAO bd = new SportsObjectDAO("D:\\web-work-space\\Web-Projekat\\ProjekatWeb\\static\\sports_objects.json");
//		bd.serialize(SportsObjects, false);
		
//		createAdministrators();
		createTrainers();
	}

	
	private static void createAdministrators() throws Exception{
		Administrator admin1 = new Administrator(-1, "markoM", "sifra123", "Marko", "Markovic", Gender.male, LocalDate.of(1980, 12, 5), Role.administrator);
		adminDAO.addAdministrator(admin1);
	}
	
	private static void createTrainers() throws Exception{
		Trainer t1 = new Trainer(-1, "nikolaN", "sifra123", "Nikola", "Nikolic", Gender.male, LocalDate.of(1988, 2, 12), Role.trainer);
		trainerDAO.addTrainer(t1);
	}
}
