package ca.ucalagary.seng696.g12.dictionary;

import java.util.*;
import java.io.*;

/**
 * The Class Serializer.
 */
public class Serializer {

	/**
	 * To object.
	 *
	 * @param serializedString the serialized string
	 * @return the object
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Object toObject(String serializedString) throws IOException, ClassNotFoundException {
		byte[] byteData = Base64.getDecoder().decode(serializedString);
		ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteData));
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		return object;
	}

	/**
	 * To string.
	 *
	 * @param object the object
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String toString(Serializable object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
		return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
	}

}
