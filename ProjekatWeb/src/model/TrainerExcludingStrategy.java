package model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class TrainerExcludingStrategy implements ExclusionStrategy{
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == TrainingHistory.class &&
                (f.getName().equals("startDateAndTime") || f.getName().equals("customer")
                        || f.getName().equals("trainer")
                        )) || (f.getDeclaringClass() == Training.class && (
         f.getName().equals("name") || f.getName().equals("type")) || f.getName().equals("imagePath") || f.getName().equals("description")
        || f.getName().equals("duration") || f.getName().equals("price") || f.getName().equals("date") || f.getName().equals("trainer"))
                || (f.getDeclaringClass() == SportsObject.class && (f.getName().equals("name") || f.getName().equals("objectType")
        || f.getName().equals("trainings") || f.getName().equals("opened") || f.getName().equals("location") || f.getName().equals("imagePath")
        || f.getName().equals("averageGrade") || f.getName().equals("workingHours") || f.getName().equals("openingHours")
        || f.getName().equals("description") || f.getName().equals("managerUsername")));
    }
}
