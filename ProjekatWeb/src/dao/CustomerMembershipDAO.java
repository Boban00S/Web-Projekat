package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jsonparsing.LocalDateConverter;
import model.ISerializable;
import model.Membership;
import model.User;
import model.UserExcludingStrategy;

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

public class CustomerMembershipDAO implements ISerializable<String, Membership>{

	private HashMap<String, Membership> memberships;
	private String fileName;

	public CustomerMembershipDAO() {

	}

	private int getNextId() {
		int maxId = 0;
		for(Membership m:memberships.values()) {
			if(m.getId()> maxId) {
				maxId = m.getId();
			}
		}
		return ++maxId;
	}


	public CustomerMembershipDAO(String fileName) {
		this.fileName = fileName;
		try {
			memberships = deserialize();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	public Collection<Membership> findAll() {
		return memberships.values();
	}

	public void addMembership(Membership membership) throws IOException{
		memberships.put(membership.getId().toString(), membership);
		List<Membership> memberships1 = new ArrayList<>(findAll());
		serialize(memberships1, false);
	}


	@Override
	public void serialize(List<Membership> objectList, boolean append) throws IOException {
		GsonBuilder builder = new GsonBuilder()
				.setExclusionStrategies(new UserExcludingStrategy());
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Writer writer = new FileWriter(fileName, append);
		gson.toJson(objectList, writer);
		writer.flush();
		writer.close();
	}
	@Override
	public HashMap<String, Membership> deserialize() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(fileName));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
		Gson gson = builder.create();
		Membership[] membershipsA = gson.fromJson(reader, Membership[].class);
		HashMap<String, Membership> output;
		output = new HashMap<String, Membership>();
		if(membershipsA != null) {
			for(Membership m: membershipsA) {
				output.put(m.getId().toString(), m);
			}
		}
		return output;
	}
}
