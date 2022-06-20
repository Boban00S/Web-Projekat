package jsonparsing;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
	  public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
	    return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE.format(src));
	  }

	  public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
	      throws JsonParseException {
	    return DateTimeFormatter.ISO_LOCAL_DATE.parse(json.getAsString(), LocalDate::from);
	  }
	}