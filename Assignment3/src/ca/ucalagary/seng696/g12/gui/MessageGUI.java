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
package ca.ucalagary.seng696.g12.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.dictionary.Antalogy;
import ca.ucalagary.seng696.g12.dictionary.Project;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class MessageGUI {

    JFrame jFrame;
    JTextArea jTextAreaMessages;

    public MessageGUI(Agent myAgent, ACLMessage reply, ACLMessage msg, Boolean isProposal) {
        String content = msg.getContent();
        jFrame = new JFrame("Proposal (" + myAgent.getLocalName() + ")");

        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
//                myAgent.killAgent(myAgent.getLocalName());
            }
        });

        jFrame.setSize(600, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JLabel jLabel = new JLabel();
        String[] text = content.split(":");
        String output = "<HTML>";
        for(String string : text){
            output+=" "+string+"<br/>";
        }
        output+="</HTML>";
        jLabel.setText(output);
        jLabel.setSize(new Dimension(20, 20));
        jPanel.add(jLabel, BorderLayout.CENTER);
        
        JPanel jPanelNewMessage = new JPanel();
        if(isProposal) {
        	JButton jButtonAccept = new JButton("Accept");
        	JButton jButtonReject = new JButton("Reject");
            jButtonAccept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	reply.setContent(content);
                	reply.setPerformative(Antalogy.ACLMESSAGE_ACCEPT);
                    myAgent.send(reply);
                    String c[] = content.split(":");
                    Project project = new Project(c[0], c[1], Integer.parseInt(c[2]), myAgent.getAID(), msg.getSender(),null);
                    ((ProviderAgent)myAgent).providerGUI.addProject(project);
                    dispose();
                }
            });
            jButtonReject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	reply.setContent(content);
                	reply.setPerformative(Antalogy.ACLMESSAGE_REFUSE);
                    myAgent.send(reply);
                    dispose();
                }
            });
            jPanelNewMessage.add(jButtonAccept, BorderLayout.WEST);
            jPanelNewMessage.add(jButtonReject, BorderLayout.EAST);
        }else if(reply != null){
	        HintTextField newMessage = new HintTextField("Reply:");
	        newMessage.setPreferredSize(new Dimension(300, 30));
	        JButton jButtonSend = new JButton("Send");
	        jButtonSend.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	reply.setContent(content + "\n" + newMessage.getText());
	                myAgent.send(reply);
	            }
	        });
	        
	        jPanelNewMessage.add(newMessage, BorderLayout.CENTER);
	        jPanelNewMessage.add(jButtonSend, BorderLayout.SOUTH);
//	        myAgent.openProject(selectProject);
        }
        jPanel.add(jPanelNewMessage, BorderLayout.SOUTH);
        jFrame.add(jPanel);
    }


    private void showMessage(String message) {
        jTextAreaMessages.setText(message);
    }

    public void showGUI() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }



}
