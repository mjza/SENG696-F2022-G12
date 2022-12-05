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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import ca.ucalagary.seng696.g12.dictionary.Ontology;
import ca.ucalagary.seng696.g12.dictionary.Client;
import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.dictionary.Provider;
import ca.ucalagary.seng696.g12.dictionary.Serializer;
import ca.ucalagary.seng696.g12.gui.ClientGUI;

/**
 * The Class ClientAgent.
 */
public class ClientAgent extends EnhancedAgent {
	/**
	 * The serial version must be increased by each update.
	 */
	private static final long serialVersionUID = 1L;

	/** The projects. */
	protected List<Project> projects = new ArrayList<>();

	/** The providers AID. */
	private Set<AID> providersAID = null;

	/** The client GUI. */
	private ClientGUI clientGUI;

	/** The client. */
	private Client client = null;

	/**
	 * Setup.
	 */
	@Override
	protected void setup() {
		System.out.println("Client Agent: " + getAID().getName() + " is ready.");
		// register the agent service in yellow page
		this.registerService("service-consumer");
		// update list of providers
		this.updateProviders();
		// bind a GUI and show it
		clientGUI = new ClientGUI(this);
		clientGUI.showGUI();
		// in a cycle listen for messages
		addBehaviour(new CyclicBehaviour() {
			/**
			 * The serial version must be increased by each update.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Action.
			 */
			public void action() {
				ACLMessage msg, reply = null;
				msg = myAgent.receive();
				if (msg != null) {
					System.out.println("A new message received for: " + getAID().getName() + ", performative: "
							+ msg.getPerformative());
					String contents[] = msg.getContent().split(":");
					String projectName, progressText, chatMessage;
					switch (msg.getPerformative()) {
					case Ontology.ACLMESSAGE_REFUSE:
						// Do nothing.
						// TODO: We can add the project to table and then remove it.
						break;
					case Ontology.ACLMESSAGE_ACCEPT:
						// extract project data
						String content = msg.getContent();
						try {
							Project project = (Project) Serializer.toObject(content);
							reply = new ACLMessage(Ontology.ACLMESSAGE_CHAT);
							reply.addReceiver(msg.getSender());
							addProject(project);
						} catch (ClassNotFoundException | IOException e1) {
							e1.printStackTrace();
							showDecodingError();
							return;
						}
						break;
					case (Ontology.ACLMESSAGE_CHAT):
						projectName = contents[0];
						chatMessage = contents[1];
						for (int i = 0; i < projects.size(); i++) {
							Project project = projects.get(i);
							if (project.getName().equals(projectName)) {
								project.chatUpdate(chatMessage);
							}
						}
						break;
					case (Ontology.ACLMESSAGE_PROGRESS):
						projectName = contents[0];
						progressText = contents[1];
						int progress = Integer.parseInt(progressText);
						for (int i = 0; i < projects.size(); i++) {
							Project project = projects.get(i);
							if (project.getName().equals(projectName)) {
								project.setProgress(progress);
							}
						}
						break;
					}
				}
			}
		});
	}

	/**
	 * Show decoding error.
	 */
	public void showDecodingError() {
		JOptionPane.showMessageDialog(clientGUI.getjFrame(), "Problem in decoding data.", "Ops: Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Send proposal.
	 *
	 * @param p        the p
	 * @param provider the provider
	 * @throws IOException
	 */
	public void sendProposal(Project project, AID provider) {
		ACLMessage message = new ACLMessage(Ontology.ACLMESSAGE_OFFER);
		message.setConversationId(Ontology.PROPOSAL);
		try {
			String serializedProject = Serializer.toString(project);
			message.setContent(serializedProject);
			message.addReceiver(provider);
			send(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public Client getClient() {
		if (this.client == null) {
			String userName = this.getUserName();
			this.client = SystemAgent.getClient(userName);
		}
		return this.client;
	}

	/**
	 * updates the providers list.
	 */
	private void updateProviders() {
		this.providersAID = searchForService("service-provider");
	}

	/**
	 * Gets the providers.
	 *
	 * @return the providers
	 */

	public List<Provider> getProviders(String filter) {
		List<Provider> providers = new ArrayList<>();
		if (null != providersAID)
			for (AID providerAID : providersAID) {
				if (null != providerAID.getLocalName() && providerAID.getLocalName().split(":").length > 1) {
					String providerUserName = providerAID.getLocalName().split(":")[1];
					Provider provider = SystemAgent.getProvider(providerUserName);
					if (filter == null || filter.trim().length() == 0 || provider.getKeywords().contains(filter))
						providers.add(provider);
				}
			}
		return providers;
	}

	/**
	 * Gets the provider AID.
	 *
	 * @param providerUserName the provider user name
	 * @return the provider AID
	 */
	public AID getProviderAID(String providerUserName) {
		if (null != providersAID)
			for (AID providerAID : providersAID) {
				if (null != providerAID.getLocalName() && providerAID.getLocalName().split(":").length > 1) {
					if (providerUserName.equalsIgnoreCase(providerAID.getLocalName().split(":")[1]))
						return providerAID;
				}
			}
		return null;
	}

	/**
	 * Gets the projects.
	 *
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * Adds the project.
	 *
	 * @param project the project
	 */
	public void addProject(Project project) {
		this.projects.add(project);
		this.clientGUI.updateProjectsJTableData();
	}

	/**
	 * Gets the project.
	 *
	 * @param projectName the project name
	 * @return the project
	 */
	public Project getProject(String projectName) {
		for (int i = 0; i < projects.size(); i++) {
			Project project = projects.get(i);
			if (project.getName().equals(projectName)) {
				return project;
			}
		}
		return null;
	}

	/**
	 * Send message.
	 *
	 * @param provider     the provider
	 * @param p            the p
	 * @param projectName  the project name
	 * @param performative the performative
	 */
	public void sendMessage(AID provider, String p, String projectName, int performative) {
		ACLMessage message = new ACLMessage(performative);
		message.setConversationId(Ontology.CLIENT_TO_PROVIDER);
		message.setContent(projectName + ":" + p);
		message.addReceiver(provider);
		send(message);
	}

	/**
	 * Send rating.
	 *
	 * @param provider the provider
	 * @param rate     the rate
	 */
	public void sendRating(AID provider, String rate) {
		ACLMessage message = new ACLMessage(Ontology.ACLMESSAGE_RATE);
		message.setConversationId(Ontology.NEGOTIATION);
		message.setContent(rate);
		message.addReceiver(provider);
		send(message);
	}

	/**
	 * Mark project done.
	 *
	 * @param project the project
	 */
	public void markProjectAsDone(Project project) {
		System.out.println("MARKING DONE " + project.getProviderAID().getLocalName());

		ACLMessage message = new ACLMessage(Ontology.ACLMESSAGE_PAYMENT);
		message.setConversationId(Ontology.NEGOTIATION);
		message.setContent(project.getName() + ":" + 70 * project.getBid() / 100);
		message.addReceiver(project.getProviderAID());
		send(message);
		project.setDone();
		for (Project proj : projects) {
			if (proj.getName().equals(proj.getName())) {
				proj.setDone();
			}
		}
		clientGUI.updateProjects(projects);
	}

}
