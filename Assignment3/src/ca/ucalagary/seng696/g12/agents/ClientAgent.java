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

import ca.ucalagary.seng696.g12.dictionary.Anthology;
import ca.ucalagary.seng696.g12.dictionary.Project;
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

	/** The client GUI. */
	ClientGUI clientGUI;

	/**
	 * Setup.
	 */
	@Override
	protected void setup() {
		System.out.println("Client Agent: " + getAID().getName() + " is ready.");
		// bind a GUI and show it
		clientGUI = new ClientGUI(this);
		clientGUI.showGUI();
		// addBehaviour(new MessageHandlingBehaviour(this));
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
					System.out.println(
							"A new message received for client:" + getAID().getName() + " " + msg.getPerformative());
					String contents[] = msg.getContent().split(":");
					String projectName, progressText, chatMessage;
					switch (msg.getPerformative()) 
					{
					case Anthology.ACLMESSAGE_REFUSE:
						break;
					case Anthology.ACLMESSAGE_ACCEPT:
						Project project = new Project(contents[0], contents[1], Integer.parseInt(contents[2]), msg.getSender(),
								myAgent.getAID(), null);
						reply = new ACLMessage(Anthology.ACLMESSAGE_CHAT);
						reply.addReceiver(msg.getSender());
						clientGUI.addProject(project);
						break;
					case (Anthology.ACLMESSAGE_CHAT):
						projectName = contents[0];
						chatMessage = contents[1];
						for (int i = 0; i< projects.size(); i++) {
							project = projects.get(i);
							if (project.getName().equals(projectName)) {
								project.chatUpdate(chatMessage);
							}
						}
						break;
					case (Anthology.ACLMESSAGE_PROGRESS):
						projectName = contents[0];
						progressText = contents[1];
						int progress = Integer.parseInt(progressText);
						for (int i = 0; i< projects.size(); i++) {
							project = projects.get(i);
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
	 * Send proposal.
	 *
	 * @param p        the p
	 * @param provider the provider
	 * @throws IOException 
	 */
	public void sendProposal(Project project, AID provider) {
		ACLMessage message = new ACLMessage(Anthology.ACLMESSAGE_OFFER);
		message.setConversationId(Anthology.PROPOSAL);
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
	 * Gets the providers.
	 *
	 * @return the providers
	 */
	public Set<AID> getProviders() {
		Set<AID> providers = searchForService("ProvidingService");
		return providers;
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
	 * Send message.
	 *
	 * @param provider     the provider
	 * @param p            the p
	 * @param projectName  the project name
	 * @param performative the performative
	 */
	public void sendMessage(AID provider, String p, String projectName, int performative) {
		ACLMessage message = new ACLMessage(performative);
		message.setConversationId(Anthology.CLIENT_TO_PROVIDER);
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
		ACLMessage message = new ACLMessage(Anthology.ACLMESSAGE_RATE);
		message.setConversationId(Anthology.NEGOTIATION);
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
		System.out.println("MARKING DONE " + project.getProvider().getLocalName());

		ACLMessage message = new ACLMessage(Anthology.ACLMESSAGE_PAYMENT);
		message.setConversationId(Anthology.NEGOTIATION);
		message.setContent(project.getName() + ":" + 70 * project.getBid() / 100);
		message.addReceiver(project.getProvider());
		send(message);
		project.setDone();
		for (Project p : projects) {
			if (p.getName().equals(project.getName())) {
				System.out.println("FOUND DONE PROJECT");
				p.setDone();
			}
		}
		clientGUI.updateProjects(projects);
	}

}
