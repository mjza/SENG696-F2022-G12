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
import java.util.ArrayList;
import ca.ucalagary.seng696.g12.gui.ProjectDetailGUI;

/**
 * The Class Project.
 */
public class Project {
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The progress. */
	private int progress = 0;
	
	/** The bid. */
	private int bid;
	
	/** The messages history. */
	private ArrayList<String> messagesHistory = new ArrayList<>();
	
	/** The provider. */
	private AID provider;
	
	/** The client. */
	private AID client;
	
	/** The deadline. */
	private String deadline;
	
	/** The project detail GUI. */
	private ProjectDetailGUI projectDetailGUI;
	
	/** The done. */
	private boolean done = false;

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
	public Project(String name, String description, int bid, AID provider, AID client, String deadline) {
		this.name = name;
		this.bid = bid;
		this.description = description;
		this.provider = provider;
		this.client = client;
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
	 * @param newName the new name
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Gets the messages history.
	 *
	 * @return the messages history
	 */
	public ArrayList<String> getMessagesHistory() {
		return this.messagesHistory;
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
	public String toString() {
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
		return "Contract for project: " + toString();
	}

	/**
	 * Gets the rejection message.
	 *
	 * @param sender the sender
	 * @return the rejection message
	 */
	public String getRejectionMessage(AID sender) {
		return sender.getLocalName() + " has rejected " + toString();
	}

	/**
	 * Checks if is final.
	 *
	 * @return true, if is final
	 */
	public boolean isFinal() {
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
	public void progress(int progressPercentage) {
		int permittedProgress = 100 - this.progress;
		if (progressPercentage < permittedProgress) {
			this.progress += progressPercentage;
		}
		if (this.projectDetailGUI != null) {
			this.projectDetailGUI.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(),
					this.getMessagesHistory());
		}
	}

	/**
	 * Chat update.
	 *
	 * @param chatMessage the chat message
	 */
	public void chatUpdate(String chatMessage) {
		this.messagesHistory.add(chatMessage);
		if (this.projectDetailGUI != null) {
			this.projectDetailGUI.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(),
					this.getMessagesHistory());
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
	 * @param projectDetailGUI the project detail GUI
	 */
	public void connectGUI(ProjectDetailGUI projectDetailGUI) {
		this.projectDetailGUI = projectDetailGUI;
	}

	/**
	 * Dispose GUI.
	 */
	public void disposeGUI() {
		if (this.projectDetailGUI != null) {
			this.projectDetailGUI.disposeGUI();
		}
	}
}
