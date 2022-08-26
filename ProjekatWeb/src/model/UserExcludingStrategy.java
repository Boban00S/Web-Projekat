package model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class UserExcludingStrategy implements ExclusionStrategy{
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == User.class && 
        		(f.getName().equals("firstName") || f.getName().equals("id")
        		|| f.getName().equals("birthdate") || f.getName().equals("gender")
        		|| f.getName().equals("lastName") || f.getName().equals("name")
        		|| f.getName().equals("role") || f.getName().equals("password")));
    }
}
