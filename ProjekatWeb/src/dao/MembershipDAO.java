package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jsonparsing.LocalDateConverter;
import model.*;

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

public class MembershipDAO implements ISerializable<String, Membership>{

	private HashMap<String, Membership> memberships;
	private String fileName;

	public MembershipDAO() {

	}

	public MembershipDAO(String fileName) {
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
		memberships.put(membership.getCode(), membership);
		List<Membership> memberships1 = new ArrayList<>(findAll());
		serialize(memberships1, false);
	}

	public void setExpireAndBuyingDate(Membership membership){
		membership.setPayingDate(LocalDate.now());
		membership.setExpireDate(this.getExpireDate(membership.getPayingDate(), membership.getMembershipType()));
	}

	public LocalDate getExpireDate(LocalDate payingDate, String membershipType){
		if(membershipType.equals("Month")){
			return payingDate.plusMonths(1);
		}else{
			return payingDate.plusYears(1);
		}
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
				output.put(m.getCode(), m);
			}
		}
		return output;
	}
}
