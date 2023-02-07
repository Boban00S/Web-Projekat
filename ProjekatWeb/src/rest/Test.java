package rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.*;
import model.*;

public class Test {
	private static UserDAO userDAO = new UserDAO("data/users.json");
	private static AdminDAO adminDAO = new AdminDAO("data/administrators.json");
	private static TrainerDAO trainerDAO = new TrainerDAO("data/trainers.json");
	private static ManagerDAO managerDAO = new ManagerDAO("data/managers.json");
	private static CustomerDAO customerDAO = new CustomerDAO("data/customers.json");
	private static OfferDAO offerDAO = new OfferDAO("data/offers.json");

	private static MembershipDAO membershipDAO = new MembershipDAO("data/memberships.json");

//	private static TrainingDAO trainingDAO = new TrainingDAO("data/training.json", offerDAO, trainerDAO, sportObjectDAO);

//	private static TrainingHistoryDAO trainingHistoryDAO = new TrainingHistoryDAO("data/training_history.json", trainingDAO, customerDAO, trainerDAO);



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
		
//		createManagers();
//		createOffers();
//		createTrainingHistories();
		createMemberships();
	}

	
	private static void createAdministrators() throws Exception{
		Administrator admin1 = new Administrator(-1, "andrejA", "123", "Andrej", "Andric", Gender.male, LocalDate.of(1980, 12, 5), Role.administrator);
		adminDAO.addAdministrator(admin1);
	}

	private static void createMemberships() throws Exception{
		Membership m1 = new Membership("M123456789", "Month", 2000, 14);
		Membership m2 = new Membership("M123456782", "Month", 4000, 20);
		Membership m3 = new Membership("M123456786", "Year", 20000, 140);
		Membership m4 = new Membership("M123456689", "Year", 40000, 250);
		Membership m5 = new Membership("M123456589", "Year", 50000, -1);
		Membership m6 = new Membership("M123426589", "Month", 5000, -1);
		membershipDAO.addMembership(m1);
		membershipDAO.addMembership(m2);
		membershipDAO.addMembership(m3);
		membershipDAO.addMembership(m4);
		membershipDAO.addMembership(m5);
		membershipDAO.addMembership(m6);

	}
	
	private static void createTrainers() throws Exception{
		Trainer t1 = new Trainer(-1, "stefanS", "sifra123", "Stefan", "Stefic", Gender.male, LocalDate.of(1988, 2, 12), Role.trainer);
		t1.setSportsObjectId(1);
		
		trainerDAO.addTrainer(t1);
	}
	
	private static void createManagers() throws Exception{
		Manager m1 = new Manager(-1, "peraP", "123", "Pera", "Peric", Gender.male, LocalDate.of(1980, 12, 5), Role.manager);
		m1.setSportsObject(-1);
		managerDAO.addManager(m1);
	}
	
//	private static void createCustomers() throws Exception{
//		Customer c1 = new Customer(-1, "nikolaN", "123", "Nikola", "Nikolic", Gender.male, LocalDate.of(1988, 2, 12), Role.customer);
//		c1.setPoints(0f);
//		c1.setMembership(null);
//		List<Integer> sOA = new ArrayList<>();
//		sOA.add(1);
//		sOA.add(2);
//		c1.setSportsObjectAttended(sOA);
//		customerDAO.addCustomer(c1);
//	}

//	private static void createOffers() throws Exception{
//		Offer o1 = new Offer(1, "Pool Pass", "Pool", "https://example.com/pool-pass.jpg",
//				"Unlimited access to the pool for one month",
//				30,
//				2);
//		Offer o2 = new Offer(2, "Sauna Special", "Sauna", "asfgg",
//				"2 hours in the sauna for the price of 1!", 30, 1);
//		Offer o3 = new Offer(3, "Gym Membership", "Gym", "asd",
//				"Unlimited access to the gym for one month", 30, 1);
//		offerDAO.addOffer(o1);
//		offerDAO.addOffer(o2);
//		offerDAO.addOffer(o3);
//	}

//	private static void createTrainingHistories() throws Exception{
//		Customer customer = customerDAO.findById(3);
//		Trainer trainer = trainerDAO.findById(18);
//		Training training = trainingDAO.findById(3);
//		TrainingHistory trainingHistory = new TrainingHistory(-1, LocalDateTime.now(), customer, trainer, training);
//
//		trainingHistoryDAO.addTrainingHistory(trainingHistory);
//	}

}
