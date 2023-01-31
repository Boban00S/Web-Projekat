package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jsonparsing.LocalDateConverter;
import jsonparsing.LocalDateTimeConverter;
import model.ISerializable;
import model.Offer;
import model.SportsObject;

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

public class OfferDAO implements ISerializable<String, Offer> {

    private HashMap<String, Offer> offers;
    private String fileName;

    public OfferDAO(){

    }

    public OfferDAO(String fileName){
        this.fileName = fileName;
        try{
            offers = deserialize();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int getNextId() {
        int maxId = 0;
        for(Offer o:offers.values()) {
            if(o.getId()> maxId) {
                maxId = o.getId();
            }
        }
        return ++maxId;
    }

    public Collection<Offer> findAll() {
        return offers.values();
    }

    public void addOffer(Offer o) throws IOException{
        o.setId(getNextId());
        offers.put(o.getImagePath(), o);
        List<Offer> offersList = new ArrayList<>(findAll());
        serialize(offersList, false);
    }

    public void deleteOfferById(int offerId) throws IOException{
        for (Offer o: offers.values()){
            if(o.getId() == offerId){
                offers.remove(o.getImagePath());
                List<Offer> offersList = new ArrayList<>(offers.values());
                serialize(offersList, false);
                break;
            }
        }
    }

    public Offer findById(int id){
        for(Offer o: offers.values()){
            if(o.getId() == id){
                return o;
            }
        }
        return null;
    }

    public List<Offer> getOffersByIds(List<Offer> offersIds){
        List<Offer> output = new ArrayList<>();
        for(Offer o: offersIds){
            Offer o1 = findById(o.getId());
            output.add(o1);
        }
        return output;
    }

    public void editOffer(Offer o) throws IOException{
        boolean found = false;
        for(Offer o1: offers.values()){
            if(o1.getId() == o.getId()){
                found = true;
                o1.setName(o.getName());
                o1.setDescription(o.getDescription());
                o1.setDuration(o.getDuration());
                o1.setType(o.getType());
                o1.setImagePath(o.getImagePath());
            }
        }
        if(!found){
            offers.put(o.getImagePath(), o);
        }
        serialize(new ArrayList<>(findAll()), false);
    }

    public void editOffers(List<Offer> newOffers) throws IOException{
        for(Offer o1: newOffers){
            editOffer(o1);
            }
        }

    @Override
    public void serialize(List<Offer> objectList, boolean append) throws IOException {
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
    public HashMap<String, Offer> deserialize() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
        builder.registerTypeAdapter(new TypeToken<LocalDateTime>(){}.getType(), new LocalDateTimeConverter());
        Gson gson = builder.create();
        Offer[] offers1 = gson.fromJson(reader, Offer[].class);
        HashMap<String, Offer> output;
        output = new HashMap<String, Offer>();
        if(offers1 != null){
            for(Offer o: offers1){
                output.put(o.getImagePath(), o);
            }
        }

        return output;
    }
}
