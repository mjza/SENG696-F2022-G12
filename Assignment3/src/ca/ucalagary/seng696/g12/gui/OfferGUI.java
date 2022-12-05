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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.agents.SystemAgent;
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
	private JFrame jFrame;

	/** The project. */
	private Project project = null;

	/** The my agent. */
	private Agent myAgent;

	/** The reply. */
	private ACLMessage reply;

	/** The message. */
	private ACLMessage message;

	/**
	 * Instantiates a new message GUI.
	 *
	 * @param myAgent the my agent
	 * @param message the msg
	 * @param reply   the reply
	 */
	public OfferGUI(Agent myAgent, ACLMessage message, ACLMessage reply) {
		this.myAgent = myAgent;
		this.reply = reply;
		this.message = message;
		// extract project data
		String content = message.getContent();
		try {
			project = (Project) Serializer.toObject(content);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
			this.showDecodingError();
			return;
		}
		// Initiate the jFrame
		this.jFrame = new JFrame("B2B Match Making System: New offer for(" + myAgent.getLocalName() + ")");
		// set icon
		this.jFrame.setIconImage(SystemAgent.getIcon());
		// Set the size and position of the GUI to the right-hand-side of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.jFrame.getGraphicsConfiguration());
		int taskBarHeight = scnMax.bottom;
		int width = screenSize.width / 3;
		int height = (screenSize.height- taskBarHeight)/2 ;
		int posX = ((screenSize.width / 2) - width)/2;
		int posY = ((screenSize.height - taskBarHeight) - height)/2;
		this.jFrame.setSize(width,height);
		this.jFrame.setLocation(posX, posY);
		// Do not kill the agent
		this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
			}
		});
		//this.jFrame.setSize(384, 320);
		this.jFrame.add(this.getOfferJPanel());
	}

	/**
	 * Gets the offer J panel.
	 *
	 * @return the offer J panel
	 */
	private JPanel getOfferJPanel() {
		// Extract content
		String content = message.getContent();
		// Create jPanel
		JPanel offerJPanel = new JPanel();
		offerJPanel.setLayout(new BorderLayout());
		// A sign for providers
		offerJPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
		// Title
		JLabel titleJLabel = new JLabel("<<Offer Details>>", SwingConstants.CENTER);
		offerJPanel.add(titleJLabel, BorderLayout.NORTH);
		// Body
		JPanel bodyJPanel = new JPanel();
		bodyJPanel.setLayout(new BorderLayout());
		bodyJPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel projectInfoJLabel = new JLabel();
		String[] columns = Project.getColumns(true);
		String[] data = project.toArray(true);
		String output = "<HTML>";
		for (int i = 0; i < columns.length && i < data.length; i++) {
			output += " " + columns[i] + ": " + data[i] + "<br/>";
		}
		output += "</HTML>";
		projectInfoJLabel.setText(output);
		bodyJPanel.add(projectInfoJLabel);
		offerJPanel.add(bodyJPanel, BorderLayout.CENTER);
		// buttons
		JPanel buttonsJPanel = new JPanel();
		offerJPanel.add(buttonsJPanel, BorderLayout.SOUTH);
		JButton jButtonAccept = new JButton("Accept");
		jButtonAccept.setBackground(new Color(34, 139, 34));
		jButtonAccept.setOpaque(true);
		JButton jButtonReject = new JButton("Reject");
		jButtonReject.setBackground(new Color(220, 20, 60));
		jButtonReject.setOpaque(true);
		buttonsJPanel.add(jButtonAccept, BorderLayout.WEST);
		buttonsJPanel.add(jButtonReject, BorderLayout.EAST);
		// Handlers
		jButtonAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reply.setContent(content);
				reply.setPerformative(Ontology.ACLMESSAGE_ACCEPT);
				myAgent.send(reply);
				((ProviderAgent) myAgent).addProject(project);
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
		//
		return offerJPanel;
	}

	/**
	 * Show GUI.
	 */
	public void showGUI() {
		this.jFrame.setVisible(true);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		this.jFrame.dispose();
	}

	/**
	 * Show decoding error.
	 */
	public void showDecodingError() {
		JOptionPane.showMessageDialog(jFrame, "Problem in decoding data.", "ERROR", JOptionPane.ERROR_MESSAGE);
	}

}
