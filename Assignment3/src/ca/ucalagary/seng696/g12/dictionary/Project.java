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

import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.gui.ProjectGUI;

/**
 * The Class Project.
 */
public class Project implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The global nextId. */
	private static int nextId = 1017;
	
	/** The id. */
	private int id;

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

	/** The provider AID. */
	private AID providerAID;

	/** The client AID. */
	private AID clientAID;

	/** The done. */
	private boolean done = false;

	/** The messages history. */
	private ArrayList<String> chats = new ArrayList<>();

	/** The project detail GUI. */
	private ProjectGUI projectGUI;

	/**
	 * Instantiates a new project.
	 *
	 * @param name        the name
	 * @param description the description
	 * @param bid         the bid
	 * @param providerAID the provider AID
	 * @param clientAID the client AID
	 * @param deadline    the deadline
	 */
	public Project(String name, String description, int bid, AID providerAID, AID clientAID, Date deadline) {
		this.id = ++Project.nextId;
		this.name = name;
		this.bid = bid;
		this.description = description;
		this.providerAID = providerAID;
		this.clientAID = clientAID;
		this.deadline = deadline;
	}

	/**
	 * Instantiates a new project.
	 *
	 * @param name        the name
	 * @param description the description
	 * @param bid         the bid
	 * @param provider    the provider
	 * @param client      the client
	 * @param deadline    the deadline
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	public Project(String name, String description, int bid, String provider, String client, Date deadline)
			throws ClassNotFoundException, IOException {
		this.id = ++Project.nextId;
		this.name = name;
		this.bid = bid;
		this.description = description;
		this.providerAID = (AID) Serializer.toObject(provider);
		this.clientAID = (AID) Serializer.toObject(client);
		this.deadline = deadline;
	}

	/**
	 * Gets the provider user name.
	 *
	 * @return the provider user name
	 */
	public String getProviderUserName() {
		if (null != this.providerAID && null != this.providerAID.getLocalName()
				&& this.providerAID.getLocalName().split(":").length > 1) {
			return this.providerAID.getLocalName().split(":")[1];
		}
		return null;
	}

	/**
	 * Gets the client user name.
	 *
	 * @return the client user name
	 */
	public String getClientUserName() {
		if (null != this.clientAID && null != this.clientAID.getLocalName()
				&& this.clientAID.getLocalName().split(":").length > 1) {
			return this.clientAID.getLocalName().split(":")[1];
		}
		return null;
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
	 * Gets the deadline.
	 *
	 * @return the deadline
	 */
	public Date getDeadline() {
		return deadline;
	}
	
	public long getTimestamp() {
		return deadline.getTime();
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
	 * Gets the provider AID.
	 *
	 * @return the provider AID
	 */
	public AID getProviderAID() {
		return this.providerAID;
	}

	/**
	 * Gets the client AID.
	 *
	 * @return the client AID
	 */
	public AID getClientAID() {
		return this.clientAID;
	}

	/**
	 * Gets the provider.
	 *
	 * @return the provider
	 */
	public Provider getProvider() {
		String userName = this.getProviderUserName();
		return SystemAgent.getProvider(userName);
	}

	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public Client getClient() {
		String userName = this.getClientUserName();
		return SystemAgent.getClient(userName);
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

	/**
	 * Gets the columns.
	 *
	 * @param isProvider the is provider
	 * @return the columns
	 */
	public static String[] getColumns(boolean isProvider) {
		String[] providerColumnNames = { "ID", "Name", "Description", "Bid", "Client", "Deadline", "Status" };
		String[] clientColumnNames = { "ID", "Name", "Description", "Bid", "Provider", "Deadline", "Status" };
		return isProvider ? providerColumnNames : clientColumnNames;
	}


	/**
	 * To array.
	 *
	 * @param isProvider the is provider
	 * @return the string[]
	 */
	public String[] toArray(boolean isProvider) {
		String[] providerData = { String.valueOf(this.getId()), this.getName(), this.getDescription(), String.valueOf(this.getBid()),
				this.getClientUserName(), this.getDeadline().toString(), (this.isDone() ? "Yes" : "No") };
		String[] clientData = { String.valueOf(this.getId()), this.getName(), this.getDescription(), String.valueOf(this.getBid()),
				this.getProviderUserName(), this.getDeadline().toString(), (this.isDone() ? "Yes" : "No") };
		return isProvider ? providerData : clientData;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
