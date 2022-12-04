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

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Provider.
 */
public class Provider extends User {

	/** The keywords. */
	private String keywords;

	/** The resume. */
	private String resume;

	/** The is premium. */
	private Boolean isPremium = false;

	/** The website. */
	private String website;

	/** The compensation. */
	private double compensation;

	/** The projects. */
	private List<Project> projects = new ArrayList<>();

	/**
	 * Instantiates a new provider.
	 *
	 * @param id           the id
	 * @param name         the name
	 * @param username     the username
	 * @param password     the password
	 * @param type         the type
	 * @param rating       the rating
	 * @param keywords     the keywords
	 * @param resume       the resume
	 * @param isPremium    the is premium
	 * @param website      the website
	 * @param compensation the compensation
	 */
	public Provider(int id, String name, String username, String password, String type, int rating, String keywords,
			String resume, Boolean isPremium, String website, double compensation) {
		super(id, name, username, password, type, rating);
		this.setKeywords(keywords);
		this.setResume(resume);
		this.setPremium(isPremium);
		this.setWebsite(website);
		this.setCompensation(compensation);
	}

	/**
	 * Gets the checks if is premium.
	 *
	 * @return the checks if is premium
	 */
	public Boolean isPremium() {
		return isPremium;
	}

	/**
	 * Sets the checks if is premium.
	 *
	 * @param isPremium the new checks if is premium
	 */
	public void setPremium(Boolean isPremium) {
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
		this.projects.add(project);
	}

	/**
	 * Sets the premium.
	 */
	public void setPremium() {
		this.isPremium = true;
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
	public String getInfo() {
		String text = "";
		text += "Name: " + this.getName() + "; \n";
		text += "Keywords: " + this.getKeywords() + "; \n";
		text += "Premium: " + (this.isPremium() ? "Yes" : "No") + "; \n";
		text += "Rating:";
		for (int i = 0; i < this.getRating(); i++)
			text += "*";
		text += " \n";
		return text;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public static String[] getColumns() {
		String[] columnNames = { "ID", "Name", "Email", "Website", "Keywords", "Is Premium?", "Rating" };
		return columnNames;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String[] toArray() {
		String[] data = { String.valueOf(this.getId()), this.getName(), this.getUsername(), this.getWebsite(),
				this.getKeywords(), (this.isPremium() ? "Yes" : "No"), String.valueOf(this.getRating()) };
		return data;
	}

	/**
	 * Gets the website.
	 *
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Sets the website.
	 *
	 * @param website the new website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Gets the compensation.
	 *
	 * @return the compensation
	 */
	public double getCompensation() {
		return compensation;
	}

	/**
	 * Sets the compensation.
	 *
	 * @param compensation the new compensation
	 */
	public void setCompensation(double compensation) {
		this.compensation = compensation;
	}
}
