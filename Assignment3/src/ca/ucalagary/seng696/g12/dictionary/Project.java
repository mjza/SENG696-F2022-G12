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

import jade.core.AID;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import ca.ucalagary.seng696.g12.gui.ProjectGUI;

/**
 * The Class Project.
 */
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The progress. */
	private int progress = 0;
	
	/** The bid. */
	private int bid;
	
	/** The deadline. */
	private Date deadline;
	
	/** The provider. */
	private AID provider;
	
	/** The client. */
	private AID client;
	
	/** The done. */
	private boolean done = false;
	
	/** The messages history. */
	private ArrayList<String> chats = new ArrayList<>();
	
	/** The project detail GUI. */
	private ProjectGUI projectGUI;

	/**
	 * Instantiates a new project.
	 *
	 * @param name the name
	 * @param description the description
	 * @param bid the bid
	 * @param provider the provider
	 * @param client the client
	 * @param deadline the deadline
	 */
	public Project(String name, String description, int bid, AID provider, AID client, Date deadline) {
		this.name = name;
		this.bid = bid;
		this.description = description;
		this.provider = provider;
		this.client = client;
		this.deadline = deadline;
	}
	
	/**
	 * Instantiates a new project.
	 *
	 * @param name the name
	 * @param description the description
	 * @param bid the bid
	 * @param provider the provider
	 * @param client the client
	 * @param deadline the deadline
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Project(String name, String description, int bid, String provider, String client, Date deadline) throws ClassNotFoundException, IOException {
		this.name = name;
		this.bid = bid;
		this.description = description;
		this.provider = (AID) Serializer.toObject(provider);
		this.client = (AID) Serializer.toObject(client);
		this.deadline = deadline;
	}

	/**
	 * Sets the done.
	 */
	public void setDone() {
		this.done = true;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the chats.
	 *
	 * @return the chats
	 */
	public ArrayList<String> getChats() {
		return this.chats;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		if (this.done) {
			return "" + this.name + " (done)";
		}
		return this.name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String compact() {
		if (this.done) {
			return "" + this.name + " (done)";
		}
		return this.name + ":" + this.description + ":" + this.bid + ":" + this.deadline;
	}

	/**
	 * Gets the contract.
	 *
	 * @return the contract
	 */
	public String getContract() {
		return "Contract for project: " + compact();
	}

	/**
	 * Gets the rejection message.
	 *
	 * @param sender the sender
	 * @return the rejection message
	 */
	public String getRejectionMessage(AID sender) {
		return sender.getLocalName() + " has rejected " + compact();
	}

	/**
	 * Checks if is done.
	 *
	 * @return true, if is done
	 */
	public boolean isDone() {
		return this.progress == 100;
	}

	/**
	 * Gets the progress.
	 *
	 * @return the progress
	 */
	public int getProgress() {
		return this.progress;
	}

	/**
	 * Progress.
	 *
	 * @param progressPercentage the progress percentage
	 */
	public void setProgress(int progressPercentage) {
		int permittedProgress = 100 - this.progress;
		if (progressPercentage <= permittedProgress) {
			this.progress += progressPercentage;
		}
		if (this.projectGUI != null) {
			this.projectGUI.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(),
					this.getChats());
		}
	}

	/**
	 * Chat update.
	 *
	 * @param chat the chat message
	 */
	public void chatUpdate(String chat) {
		this.chats.add(chat);
		if (this.projectGUI != null) {
			this.projectGUI.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(),
					this.getChats());
		}
	}

	/**
	 * Gets the bid.
	 *
	 * @return the bid
	 */
	public int getBid() {
		return this.bid;
	}

	/**
	 * Gets the provider.
	 *
	 * @return the provider
	 */
	public AID getProvider() {
		return this.provider;
	}

	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public AID getClient() {
		return this.client;
	}

	/**
	 * Connect GUI.
	 *
	 * @param projectGUI the project detail GUI
	 */
	public void connectGUI(ProjectGUI projectGUI) {
		this.projectGUI = projectGUI;
	}

	/**
	 * Dispose GUI.
	 */
	public void disposeGUI() {
		if (this.projectGUI != null) {
			this.projectGUI.disposeGUI();
		}
	}
}
