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
import java.util.Set;

import ca.ucalagary.seng696.g12.dictionary.Anthology;
import ca.ucalagary.seng696.g12.dictionary.Project;
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
	List<Project> projects;

	/** The current number. */
	int currentNumber = 0;

	/** The client GUI. */
	ClientGUI clientGUI;

	/**
	 * Setup.
	 */
	@Override
	protected void setup() {
		System.out.println("Client Agent: " + getAID().getName() + " is ready.");
		Set<AID> providers = searchForService("ProvidingService");
		projects = new ArrayList<>();
		clientGUI = new ClientGUI(this, providers, projects);
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
					String contents[] = null;
					Project project = null;
					String projectName, progressText, chatMessage;
					switch (msg.getPerformative()) {
					case Anthology.ACLMESSAGE_ACCEPT:
						// content = null;
						contents = msg.getContent().split(":");
						project = new Project(contents[0], contents[1], Integer.parseInt(contents[2]), msg.getSender(),
								myAgent.getAID(), null);
						reply = new ACLMessage(Anthology.ACLMESSAGE_CHAT);
						reply.addReceiver(msg.getSender());
						// content = project.getContract();
						clientGUI.addProject(project);
					case Anthology.ACLMESSAGE_REFUSE:
						// content = null;
						contents = msg.getContent().split(":");
						project = new Project(contents[0], contents[1], Integer.parseInt(contents[2]), msg.getSender(),
								myAgent.getAID(), null);
						// content = project.getRejectionMessage(msg.getSender());
						System.out.println("" + msg.getSender().getLocalName() + " responded to the proposal for "
								+ msg.getContent());
					case (Anthology.ACLMESSAGE_CHAT):
						projectName = msg.getContent().split(":")[0];
						chatMessage = msg.getContent().split(":")[1];
						for (Project project_iter : projects) {
							if (project_iter.getName().equals(projectName)) {
								project_iter.chatUpdate(chatMessage);
							}
						}
					case (Anthology.ACLMESSAGE_PROGRESS):
						projectName = msg.getContent().split(":")[0];
						progressText = msg.getContent().split(":")[1];
						int progress = Integer.parseInt(progressText);
						for (Project project_iter : projects) {
							if (project_iter.getName().equals(projectName)) {
								project_iter.setProgress(progress);
							}
						}
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
	 */
	public void sendProposal(Project p, AID provider) {
		ACLMessage message = new ACLMessage(Anthology.ACLMESSAGE_OFFER);
		message.setConversationId(Anthology.PROPOSAL);
		message.setContent(p.toString());
		System.out.println("Proposing " + p.toString() + " to " + provider.getLocalName());
		message.addReceiver(provider);
		send(message);
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
