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
package ca.ucalagary.seng696.g12.agents;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import ca.ucalagary.seng696.g12.dictionary.Chat;
import ca.ucalagary.seng696.g12.dictionary.Ontology;
import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.dictionary.Provider;
import ca.ucalagary.seng696.g12.gui.OfferGUI;
import ca.ucalagary.seng696.g12.gui.ProviderGUI;

/**
 * The Class ProviderAgent.
 */
public class ProviderAgent extends EnhancedAgent {

	/**
	 * The serial version must be increased by each update.
	 */
	private static final long serialVersionUID = 1L;

	/** The projects. */
	private List<Project> projects = new ArrayList<>();

	/** The provider GUI. */
	public ProviderGUI providerGUI;

	/** The provider. */
	private Provider provider = null;

	/**
	 * Setup.
	 */
	@Override
	protected void setup() {
		System.out.println("Provider Agent: " + getAID().getName() + " is ready.");
		// register the agent service in yellow page
		this.registerService("service-provider");
		// bind a GUI and show it
		providerGUI = new ProviderGUI(this);
		providerGUI.showGUI();
		// in a cycle listen for messages
		this.addBehaviour(new CyclicBehaviour() {
			/**
			 * The serial version must be increased by each update.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Action.
			 */
			@Override
			public void action() {
				ACLMessage msg, reply = null;
				msg = myAgent.receive();
				if (msg != null) {
					System.out.println("A new message received for: " + getAID().getName() + ", Performative: "
							+ msg.getPerformative());
					String contents[] = msg.getContent().split(":");
					String projectId, chatMessage;					
					switch (msg.getPerformative()) {
					case Ontology.ACLMESSAGE_OFFER:
						reply = msg.createReply();
						OfferGUI msgGUI = new OfferGUI(myAgent, msg, reply);
						msgGUI.showGUI();
						break;
					case Ontology.ACLMESSAGE_CHAT:
						projectId = contents[0];
						chatMessage = contents[1];
						for (Project project : projects) {
							if (project.getId() == Integer.parseInt(projectId)) {
								Chat chat = new Chat(chatMessage, getProvider(), project.getClient(), false);
								project.addNewChat(chat);
								break;
							}
						}
						break;
					case Ontology.ACLMESSAGE_PAYMENT:
						projectId = contents[0];
						chatMessage = contents[1];
						for (Project project : projects) {
							if (project.getId() == Integer.parseInt(projectId)) {
								project.setPaid();
								break;
							}
						}
						providerGUI.updateProjectsJTableData();
						break;
					case Ontology.ACLMESSAGE_DONE:
						projectId = contents[0];
						chatMessage = contents[1];
						for (Project project : projects) {
							if (project.getId() == Integer.parseInt(projectId)) {
								project.disposeGUI();
								break;
							}
						}
						break;
					}
				}

			}
		});

	}

	/**
	 * Send message.
	 *
	 * @param client         the client
	 * @param messageText    the message text
	 * @param projectId    the project id
	 * @param conversationID the conversation ID
	 */
	public void sendMessage(AID client, String messageText, int projectId, String conversationID) {
		ACLMessage message = new ACLMessage(Ontology.ACLMESSAGE_CHAT);
		message.setConversationId(conversationID);
		message.setContent(projectId + ":" + messageText);
		message.addReceiver(client);
		send(message);
	}
	
	/**
	 * Send progress.
	 *
	 * @param client         the client
	 * @param messageText    the message text
	 * @param projectId    the project id
	 * @param conversationID the conversation ID
	 */
	public void sendProgress(AID client, String messageText, int projectId, String conversationID) {
		ACLMessage message = new ACLMessage(Ontology.ACLMESSAGE_PROGRESS);
		message.setConversationId(conversationID);
		message.setContent(projectId + ":" + messageText);
		message.addReceiver(client);
		send(message);
	}

	/**
	 * Gets the provider.
	 *
	 * @return the provider
	 */
	public Provider getProvider() {
		if (this.provider == null) {
			String userName = this.getUserName();
			this.provider = SystemAgent.getProvider(userName);
		}
		return this.provider;
	}

	/**
	 * Gets the projects.
	 *
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return this.projects;
	}

	/**
	 * Gets the project.
	 *
	 * @param projectId the project id
	 * @return the project
	 */
	public Project getProject(int projectId) {
		for (int i = 0; i < projects.size(); i++) {
			Project project = projects.get(i);
			if (project.getId() == projectId) {
				return project;
			}
		}
		return null;
	}

	/**
	 * Adds the project.
	 *
	 * @param project the project
	 */
	public void addProject(Project project) {
		this.projects.add(project);
		this.providerGUI.updateProjectsJTableData();
	}

	/**
	 * Go premium.
	 *
	 * @return the boolean
	 */
	public Boolean upgrade2Premium() {
		Provider provider = this.getProvider();
		provider.setPremium(true);
		providerGUI.updatePremium();
		return true;
	}

	/**
	 * Downgrade 2 client.
	 */
	public void downgrade2Client() {
		Provider provider = this.getProvider();
		providerGUI.dispose();
		Class<?> agentClass = ClientAgent.class;
		String agentName = this.generateAgentName(provider.getUsername(), agentClass);
		this.createAgent(agentName, agentClass);
		takeDown();
	}

	public void sendRating(AID clientAID, String text) {
		// TODO Provider must record a comment for a client		
	}

}
