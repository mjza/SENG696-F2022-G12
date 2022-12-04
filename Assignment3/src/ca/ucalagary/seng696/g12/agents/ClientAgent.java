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

import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.gui.ClientGUI;
import ca.ucalagary.seng696.g12.settings.Constants;

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
    
    /** The gui. */
    ClientGUI GUI;
    
    /** The credit. */
    private int credit = 1000;
    
    /**
     * Setup.
     */
    @Override
    protected void setup() {
        System.out.println("Hello! ClientAgent " + getAID().getName() + " is here!");
        Set<AID> providers = searchForService("project-provide");
        projects = new ArrayList<>();
        GUI = new ClientGUI(this, providers, projects);
        GUI.showGUI();
//        addBehaviour(new MessageHandlingBehaviour(this));
        addBehaviour(new CyclicBehaviour() {
            /**
			 * The serial version must be increased by each update.
			 */
			private static final long serialVersionUID = 1L;

			public void action() {
                ACLMessage msg, reply = null;

                msg = myAgent.receive();

                if (msg != null) {
					String content = null;
                    String c[] = null;
                    Project project = null;
                    String projectName, progressText, chatMessage;
                   switch (msg.getPerformative()){
                        case Constants.ACCEPT:
                            content = null;
                            c = msg.getContent().split(":");
                            project = new Project(c[0], c[1], Integer.parseInt(c[2]), msg.getSender(), myAgent.getAID(),null);

                            reply = new ACLMessage(Constants.CHAT);
                            reply.addReceiver(msg.getSender());
                            content = project.getContract();
                            GUI.addProject(project);
                        case Constants.REFUSE:
                            content = null;
                            c = msg.getContent().split(":");
                            project = new Project(c[0], c[1], Integer.parseInt(c[2]), msg.getSender(), myAgent.getAID(),null);

                            content = project.getRejectionMessage(msg.getSender());
                        System.out.println("" + msg.getSender().getLocalName() + " responded to the proposal for " + msg.getContent());
                    case (Constants.CHAT):
                        projectName = msg.getContent().split(":")[0];
                        chatMessage = msg.getContent().split(":")[1];
                        for (Project project_iter : projects){
                            if (project_iter.getName().equals(projectName)){
                                project_iter.chatUpdate(chatMessage);
                            }
                        }
                    case (Constants.PROGRESS):
                        projectName = msg.getContent().split(":")[0];
                        progressText = msg.getContent().split(":")[1];
                        int progress = Integer.parseInt(progressText);
                        for (Project project_iter : projects){
                            if (project_iter.getName().equals(projectName)){
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
     * @param p the p
     * @param provider the provider
     */
    public void sendProposal(Project p, AID provider) {
        ACLMessage message = new ACLMessage(Constants.OFFER);
        message.setConversationId(Constants.PROJECT_STARTER);

        message.setContent(p.toString());
        System.out.println("Proposing " + p.toString() + " to " + provider.getLocalName());
        message.addReceiver(provider);
        send(message);
    }

    /**
     * Send message.
     *
     * @param provider the provider
     * @param p the p
     * @param projectName the project name
     * @param performative the performative
     */
    public void sendMessage(AID provider, String p, String projectName, int performative) {
        ACLMessage message = new ACLMessage(performative);
        message.setConversationId(Constants.CLIENT_SEND_MESSAGE);
        message.setContent(projectName + ":" + p);
        message.addReceiver(provider);
        send(message);
    }

    /**
     * Send rating.
     *
     * @param provider the provider
     * @param rate the rate
     */
    public void sendRating(AID provider, String rate) {
        ACLMessage message = new ACLMessage(Constants.RATE);
        message.setConversationId(Constants.CONVERSATION);
        message.setContent(rate);
        message.addReceiver(provider);
        send(message);
    }
    
    /**
     * Mark project done.
     *
     * @param project the project
     */
    public void markProjectDone(Project project) {
        System.out.println("MARKING DONE " + project.getProvider().getLocalName());
        
    	ACLMessage message = new ACLMessage(Constants.PAYMENT);
        message.setConversationId(Constants.CONVERSATION);
        message.setContent(project.getName()+":"+70*project.getBid()/100);
        message.addReceiver(project.getProvider());
        send(message);
        project.setDone();
        for(Project p: projects){
            if(p.getName().equals(project.getName())){
                System.out.println("FOUND DONE PROJECT");
                p.setDone();
            }
        }
        GUI.updateProjects(projects);
    	addCredit(-1 * project.getBid());
    }
    
    /**
     * Gets the credit.
     *
     * @return the credit
     */
    public int getCredit() {
    	return credit;
    }
    
    /**
     * Adds the credit.
     *
     * @param x the x
     */
    public void addCredit(int x) {
        credit += x;
        GUI.updateCredit();
    	
    }
}
