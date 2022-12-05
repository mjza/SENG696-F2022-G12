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
import java.io.IOException;

import javax.swing.*;

import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.dictionary.Ontology;
import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.dictionary.Serializer;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 * The Class MessageGUI.
 */
public class OfferGUI {

	/** The j frame. */
	JFrame jFrame;

	/** The j text area messages. */
	JTextArea jTextAreaMessages;

	/** The project. */
	Project project = null;

	/**
	 * Instantiates a new message GUI.
	 *
	 * @param myAgent the my agent
	 * @param reply   the reply
	 * @param msg     the msg
	 */
	public OfferGUI(Agent myAgent, ACLMessage reply, ACLMessage msg) {
		String content = msg.getContent();
		try {
			project = (Project) Serializer.toObject(content);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
			this.showDecodingError();
			return;
		}
		jFrame = new JFrame("Proposal (" + myAgent.getLocalName() + ")");

		this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
			}
		});

		jFrame.setSize(600, 400);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());

		JLabel jLabel = new JLabel();
		String[] columns = Project.getColumns(true);
		String[] data = project.toArray(true);
		String output = "<HTML>";
		for (int i=0; i<columns.length && i<data.length; i++) {
			output += " " + columns[i] + ": " + data[i] + "<br/>";
		}
		output += "</HTML>";
		jLabel.setText(output);
		jLabel.setSize(new Dimension(20, 20));
		jPanel.add(jLabel, BorderLayout.CENTER);

		JPanel jPanelNewMessage = new JPanel();

		JButton jButtonAccept = new JButton("Accept");
		JButton jButtonReject = new JButton("Reject");
		jButtonAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reply.setContent(content);
				reply.setPerformative(Ontology.ACLMESSAGE_ACCEPT);
				myAgent.send(reply);				
				((ProviderAgent) myAgent).providerGUI.addProject(project);
				dispose();
			}
		});
		jButtonReject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reply.setContent(content);
				reply.setPerformative(Ontology.ACLMESSAGE_REFUSE);
				myAgent.send(reply);
				dispose();
			}
		});
		jPanelNewMessage.add(jButtonAccept, BorderLayout.WEST);
		jPanelNewMessage.add(jButtonReject, BorderLayout.EAST);

		jPanel.add(jPanelNewMessage, BorderLayout.SOUTH);
		jFrame.add(jPanel);
	}

	/**
	 * Show message.
	 *
	 * @param message the message
	 */
	public void showMessage(String message) {
		jTextAreaMessages.setText(message);
	}

	/**
	 * Show GUI.
	 */
	public void showGUI() {
		jFrame.setVisible(true);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		this.jFrame.dispose();
	}

	public void showDecodingError() {
		JOptionPane.showMessageDialog(jFrame, "Problem in decoding data.", "ERROR", JOptionPane.ERROR_MESSAGE);
	}

}
