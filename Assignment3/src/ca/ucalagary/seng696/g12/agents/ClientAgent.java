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

public class ClientAgent extends EnhancedAgent {
    List<Project> projects;
    int currentNumber = 0;
    ClientGUI GUI;
    private int credit = 1000;
    @Override
    protected void setup() {
        System.out.println("Hello! ClientAgent " + getAID().getName() + " is here!");
        Set<AID> providers = searchForService("project-provide");
        projects = new ArrayList<>();
        GUI = new ClientGUI(this, providers, projects);
        GUI.showGUI();
//        addBehaviour(new MessageHandlingBehaviour(this));
        addBehaviour(new CyclicBehaviour() {
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
                            project = new Project(c[0], c[1], Integer.parseInt(c[2]), msg.getSender(), myAgent.getAID(),"");

                            reply = new ACLMessage(Constants.CHAT);
                            reply.addReceiver(msg.getSender());
                            content = project.getContract();
                            GUI.addProject(project);
                        case Constants.REFUSE:
                            content = null;
                            c = msg.getContent().split(":");
                            project = new Project(c[0], c[1], Integer.parseInt(c[2]), msg.getSender(), myAgent.getAID(),"");

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
                                project_iter.progress(progress);
                            }
                        }
                    }
                }
            }
        });
    }


    public void sendProposal(Project p, AID provider) {
        ACLMessage message = new ACLMessage(Constants.OFFER);
        message.setConversationId(Constants.PROJECT_STARTER);

        message.setContent(p.toString());
        System.out.println("Proposing " + p.toString() + " to " + provider.getLocalName());
        message.addReceiver(provider);
        send(message);
    }

    public void sendMessage(AID provider, String p, String projectName, int performative) {
        ACLMessage message = new ACLMessage(performative);
        message.setConversationId(Constants.CLIENT_SEND_MESSAGE);
        message.setContent(projectName + ":" + p);
        message.addReceiver(provider);
        send(message);
    }

    public void sendRating(AID provider, String rate) {
        ACLMessage message = new ACLMessage(Constants.RATE);
        message.setConversationId(Constants.CONVERSATION);
        message.setContent(rate);
        message.addReceiver(provider);
        send(message);
    }
    
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
    public int getCredit() {
    	return credit;
    }
    public void addCredit(int x) {
        credit += x;
        GUI.updateCredit();
    	
    }
}
