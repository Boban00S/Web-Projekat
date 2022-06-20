package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ISerializable<K, T> {
	public void serialize(List<T> objectList, boolean append) throws IOException;
	public HashMap<K, T> deserialize() throws IOException;
}
