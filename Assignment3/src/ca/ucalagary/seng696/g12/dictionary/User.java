package ca.ucalagary.seng696.g12.dictionary;

import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
    
    /** The username. */
    private String username;
    
    /** The password. */
    private String password;
    
    /** The type. */
    private String type;
    
    /** The rating. */
    private int rating;

    /** The Constant PROVIDER. */
    public static final String PROVIDER = "P";
    
    /** The Constant CLIENT. */
    public static final String CLIENT = "C";
    
    /** The Constant TYPES. */
    public static final String TYPES[] = {PROVIDER, CLIENT};

    /**
     * Instantiates a new user.
     *
     * @param id the id
     * @param name the name
     * @param username the username
     * @param password the password
     * @param type the type
     * @param rating the rating
     * @throws IllegalArgumentException the illegal argument exception
     */
    User(int id, String name, String username, String password, String type, int rating) throws IllegalArgumentException {
    	this.setId(id);
    	this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
        this.setType(type);
        this.setRating(rating);
    }

    /**
     * Sets the rating.
     *
     * @param rating the new rating
     */
    private void setRating(int rating) {
		this.rating = rating;		
	}

	/**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setType(String type) throws IllegalArgumentException {
    	 if(Arrays.stream(TYPES).anyMatch(type::equals))
    		 this.type = type;
         else
         	throw new IllegalArgumentException("type can be C or P");        
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public int getRating(){
        return rating;
    }

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

}
