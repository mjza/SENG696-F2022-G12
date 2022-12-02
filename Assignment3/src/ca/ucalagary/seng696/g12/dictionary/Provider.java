package ca.ucalagary.seng696.g12.dictionary;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Provider.
 */
public class Provider extends User {

    /** The keywords. */
    private String keywords;
    
    /** The resume. */
    private String resume;
    
    /** The current projects. */
    private List<Project> currentProjects = new ArrayList<>();
    
    /** The done projects. */
    private List<Project> doneProjects = new ArrayList<>();
    
    /** The is premium. */
    public Boolean isPremium = false;    
    

	/**
	 * Instantiates a new provider.
	 *
	 * @param id the id
	 * @param name the name
	 * @param username the username
	 * @param password the password
	 * @param type the type
	 * @param rating the rating
	 * @param keywords the keywords
	 * @param resume the resume
	 * @param premium the premium
	 */
    public Provider(int id, String name, String username, String password, String type, int rating, String keywords, String resume, Boolean isPremium) {
        super(id, name, username, password, type, rating);
        this.setKeywords(keywords);
        this.setResume(resume);
        this.setIsPremium(isPremium);
    }
    
    /**
     * Gets the checks if is premium.
     *
     * @return the checks if is premium
     */
    public Boolean getIsPremium() {
		return isPremium;
	}

	/**
	 * Sets the checks if is premium.
	 *
	 * @param isPremium the new checks if is premium
	 */
	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}

    /**
     * Sets the keywords.
     *
     * @param keywords the new keywords
     */
    private void setKeywords(String keywords) {
		this.keywords = keywords;		
	}

	/**
     * Gets the keywords.
     *
     * @return the keywords
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * Adds the project.
     *
     * @param project the project
     */
    public void addProject(Project project) {
        this.currentProjects.add(project);
    }

    /**
     * Gets the done project number.
     *
     * @return the done project number
     */
    public int getDoneProjectNumber() {
        return doneProjects.size();
    }

    /**
     * Sets the premium.
     */
    public void setPremium(){
        isPremium=true;
    }    

	/**
	 * Gets the resume.
	 *
	 * @return the resume
	 */
	public String getResume() {
		return resume;
	}

	/**
	 * Sets the resume.
	 *
	 * @param resume the new resume
	 */
	public void setResume(String resume) {
		this.resume = resume;
	}
	
	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo(){
        String text =  "";
        text += "Name: " + getUsername() + ",\n";
        text += "Type: " + getType() + ",\n";
        text += "Keywords: " + getKeywords() + ",\n";
        text += "Resume: " + getResume() + ",\n";
        text += "Premium: " + getIsPremium() + ",\n";
        text += "Completed Project No: " + getDoneProjectNumber() + ",\n";
        text += "Rating:" + getRating();
        return text;
    }
}
