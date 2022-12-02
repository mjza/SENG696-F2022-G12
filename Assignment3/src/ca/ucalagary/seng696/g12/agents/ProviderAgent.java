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

import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.dictionary.Provider;
import ca.ucalagary.seng696.g12.dictionary.User;
import ca.ucalagary.seng696.g12.gui.MessageGUI;
import ca.ucalagary.seng696.g12.gui.ProviderGUI;
import ca.ucalagary.seng696.g12.settings.Constants;

public class ProviderAgent extends EnhancedAgent {
    private List<Project> projects;
    ArrayList<Integer> ratings=new ArrayList<>();
    public ProviderGUI providerGUI;
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
                            MessageGUI msgGUI = new MessageGUI(myAgent, reply, msg, true);
                            msgGUI.showGUI();
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
                            providerGUI.updateProjects(projects);
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
        providerGUI = new ProviderGUI(this,projects);
        providerGUI.showGUI();

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
        providerGUI.updateCredit();
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
        providerGUI.updatePremium();
        return true;
    }
    public void withdraw(){
        Provider p = getProvider();
        p.setType(User.PROVIDER);
        providerGUI.dispose();
        createAgent("Client:" + p.getUsername(), "ca.ucalagary.seng696.g12.agents.ClientAgent");
        takeDown();
    }
}

