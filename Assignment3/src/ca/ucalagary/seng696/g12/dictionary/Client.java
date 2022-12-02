package ca.ucalagary.seng696.g12.dictionary;

/**
 * The Class Client.
 */
public class Client extends User {

    /**
     * Instantiates a new client.
     *
     * @param id the id
     * @param name the name
     * @param username the username
     * @param password the password
     * @param type the type
     * @param rating the rating
     */
    public Client(int id, String name, String username, String password, String type, int rating) {
        super(id, name, username, password, type, rating);
    }
}
