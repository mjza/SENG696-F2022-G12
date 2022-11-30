package ca.ucalagary.seng696.g12.agents;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.dictionary.Provider;
import ca.ucalagary.seng696.g12.dictionary.User;
import ca.ucalagary.seng696.g12.gui.MessageGui;
import ca.ucalagary.seng696.g12.gui.ProviderGui;
import ca.ucalagary.seng696.g12.settings.Constants;

public class ProviderAgent extends EnhancedAgent {
    private List<Project> projects;
    ArrayList<Integer> ratings=new ArrayList<>();
    public ProviderGui providerGui;
    private double rate = 0.0;
    private int credit = 1000;

    @Override
    protected void setup() {
    	System.out.println("SETTING UP A PROVIDER AGENT");
        projects = new ArrayList<>();
        register("project-provide");
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg, reply;
                msg = ProviderAgent.this.receive();
                if (msg != null){
                    System.out.println("new message received for provider:" + getAID().getName() + " " + msg.getPerformative());
                    String projectName, chatMessage;
                    if (msg.getPerformative() == Constants.PAYMENT || msg.getPerformative() == Constants.DONE){
                        projectName = msg.getContent().split(":")[0];
                        chatMessage = msg.getContent().split(":")[1];
                    }
                    else{
                        projectName = "NA";
                        chatMessage = "NA";
                    }
                    switch (msg.getPerformative()) {
                        case Constants.OFFER:
                            reply = msg.createReply();
                            MessageGui msgGui = new MessageGui(myAgent, reply, msg, true);
                            msgGui.showGui();
                        case Constants.CHAT:
                            for (Project project : projects) {
                                if (project.getName().equals(projectName)) {
                                    project.chatUpdate(chatMessage);
                                }
                            }
                        case Constants.PAYMENT:
                            String contents[] = msg.getContent().split(":");
                            for (Project p : projects) {
                                if (p.getName().equals(contents[0])) {
                                    System.out.println("FOUND DONE PROJECT");
                                    p.setDone();
                                }
                            }
                            providerGui.updateProjects(projects);
                            int bid = Integer.parseInt(contents[1]);
                            ProviderAgent.this.addCredit(bid);
                        case Constants.DONE:
                            for (Project project : projects) {
                                if (project.getName().equals(projectName)) {
                                    project.disposeGUI();
                                }
                            }
                    }
                }
                else{
//                    System.out.println("provider receives no message");
                }

            }
        });
//        addBehaviour(new MessageHandlingBehaviour(this));
        providerGui = new ProviderGui(this,projects);
        providerGui.showGui();

    }

    public void sendMessage(AID client, String messageText, String projectName, String conversationID) {
        ACLMessage message = new ACLMessage(Constants.CHAT);
        message.setConversationId(conversationID);
        message.setContent(projectName + ":" + messageText);
        message.addReceiver(client);
        send(message);
    }



    private void update_rate() {
        Integer sum = 0;
        if(!ratings.isEmpty()) {
            for (Integer mark : ratings) {
                sum += mark;
            }
            rate = sum.doubleValue() / ratings.size();
        }
        rate = sum;
        System.out.println("changed rate to : ");
        System.out.println(rate);
    }

    public int getCredit() {
    	return credit;
    }
    public void addCredit(int x) {
        credit += x;
        providerGui.updateCredit();
    }
    public Provider getProvider(){
        return SystemAgent.getProvider(this.getLocalName().split(":")[1]);
    }
    public Boolean goPremium(){
        if(getCredit()<Constants.PREMIUM_PRICE){
            return false;
        }
        addCredit(-Constants.PREMIUM_PRICE);
        Provider p = getProvider();
        p.setPremium();
        providerGui.updatePremium();
        return true;
    }
    public void withdraw(){
        Provider p = getProvider();
        p.setRole(User.PROVIDER);
        providerGui.dispose();
        createAgent("Client:" + p.getUsername(), "ca.ucalagary.seng696.g12.agents.ClientAgent");
        takeDown();
    }
}

