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
import jade.wrapper.ControllerException;

import java.util.ArrayList;
import java.util.List;

import ca.ucalagary.seng696.g12.dictionary.Anthology;
import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.dictionary.Provider;
import ca.ucalagary.seng696.g12.dictionary.User;
import ca.ucalagary.seng696.g12.gui.MessageGUI;
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

	/** The ratings. */
	ArrayList<Integer> ratings = new ArrayList<>();

	/** The provider GUI. */
	public ProviderGUI providerGUI;

	/** The rate. */
	private double rate = 0.0;

	/**
	 * Setup.
	 */
	@Override
	protected void setup() {
		System.out.println("Provider Agent: " + getAID().getName() + " is ready.");
		// register the agent service in yellow page
		this.registerService("ProvidingService");
		// addBehaviour(new MessageHandlingBehaviour(this));
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
					System.out.println(
							"A new message received for provider:" + getAID().getName() + " " + msg.getPerformative());
					String contents[] = msg.getContent().split(":");
					String projectName, chatMessage;
					if (msg.getPerformative() == Anthology.ACLMESSAGE_PAYMENT
							|| msg.getPerformative() == Anthology.ACLMESSAGE_DONE) {
						projectName = contents[0];
						chatMessage = contents[1];
					} else {
						projectName = "NA";
						chatMessage = "NA";
					}
					switch (msg.getPerformative()) {
					case Anthology.ACLMESSAGE_OFFER:
						reply = msg.createReply();
						MessageGUI msgGUI = new MessageGUI(myAgent, reply, msg, true);
						msgGUI.showGUI();
						break;
					case Anthology.ACLMESSAGE_CHAT:
						for (Project project : projects) {
							if (project.getName().equals(projectName)) {
								project.chatUpdate(chatMessage);
							}
						}
						break;
					case Anthology.ACLMESSAGE_PAYMENT:
						for (Project project : projects) {
							if (project.getName().equals(contents[0])) {
								project.setDone();
							}
						}
						providerGUI.updateProjects(projects);
						break;
					case Anthology.ACLMESSAGE_DONE:
						for (Project project : projects) {
							if (project.getName().equals(projectName)) {
								project.disposeGUI();
							}
						}
						break;
					}
				}

			}
		});

		providerGUI = new ProviderGUI(this, projects);
		providerGUI.showGUI();

	}

	/**
	 * Send message.
	 *
	 * @param client         the client
	 * @param messageText    the message text
	 * @param projectName    the project name
	 * @param conversationID the conversation ID
	 */
	public void sendMessage(AID client, String messageText, String projectName, String conversationID) {
		ACLMessage message = new ACLMessage(Anthology.ACLMESSAGE_CHAT);
		message.setConversationId(conversationID);
		message.setContent(projectName + ":" + messageText);
		message.addReceiver(client);
		send(message);
	}

	/**
	 * Update rate.
	 */
	public void updateRate() {
		Integer sum = 0;
		if (!ratings.isEmpty()) {
			for (Integer mark : ratings) {
				sum += mark;
			}
			rate = sum.doubleValue() / ratings.size();
		}
		rate = sum;
		System.out.println("changed rate to : ");
		System.out.println(rate);
	}

	/**
	 * Gets the provider.
	 *
	 * @return the provider
	 */
	public Provider getProvider() {
		String userName = this.getLocalName().split(":")[1];
		return SystemAgent.getProvider(userName);
	}

	/**
	 * Go premium.
	 *
	 * @return the boolean
	 */
	public Boolean goPremium() {

		Provider p = getProvider();
		p.setPremium();
		providerGUI.updatePremium();
		return true;
	}

	/**
	 * Withdraw.
	 * 
	 * @throws ControllerException
	 */
	public void withdraw() {
		Provider p = getProvider();
		p.setType(User.PROVIDER);
		providerGUI.dispose();
		createAgent("Client:" + p.getUsername(), ClientAgent.class);
		takeDown();
	}
}
