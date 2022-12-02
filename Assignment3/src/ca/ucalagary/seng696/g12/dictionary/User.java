/**
 * MIT License
 * 
 * Copyright (c) 2022 Mahdi Jaberzadeh Ansari
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. 
 */
package ca.ucalagary.seng696.g12.dictionary;

import java.util.Arrays;

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
