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

import javax.swing.*;

import ca.ucalagary.seng696.g12.agents.ClientAgent;
import ca.ucalagary.seng696.g12.agents.EnhancedAgent;
import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.dictionary.Chat;
import ca.ucalagary.seng696.g12.dictionary.Ontology;
import ca.ucalagary.seng696.g12.dictionary.Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The Class ProjectGUI.
 */
public class ProjectGUI {

	/** The j frame. */
	JFrame jFrame;

	/** The label. */
	JLabel chatJLabel = new JLabel();

	/** The j label. */
	JLabel projectJLabel = new JLabel();
	
	Project project = null;
	
	EnhancedAgent myAgent = null;

	/**
	 * Instantiates a new project GUI. It is shown when the user is a Provider
	 * 
	 * @param agent   the agent
	 * @param project the project
	 */
	public ProjectGUI(ProviderAgent agent, Project project) {
		project.bindGUI(this);

		this.jFrame = new JFrame("Welcome " + agent.getLocalName());
		this.jFrame.setSize(400, 400);

		
		this.jFrame.add(this.getProviderJPanel());
	}

	/**
	 * Instantiates a new project GUI.
	 *
	 * @param agent   the agent
	 * @param project the project
	 */
	public ProjectGUI(ClientAgent agent, Project project) {
		project.bindGUI(this);
		this.myAgent = agent;
		this.project = project;
		this.jFrame = new JFrame("Welcome " + agent.getLocalName());
		this.jFrame.setSize(400, 400);

		
		this.jFrame.add(this.getClientJPanel());
	}

	private JPanel getProviderJPanel() {
		ProviderAgent agent = (ProviderAgent) this.myAgent;
		Project project = this.project;
		
		JPanel providerJPanel = new JPanel();
		providerJPanel.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(chatJLabel, BorderLayout.EAST);
		centerPanel.add(projectJLabel, BorderLayout.WEST);
		providerJPanel.add(centerPanel, BorderLayout.CENTER);

		updateRightLabel(project.getName(), project.getDescription(), project.getProgress(), project.getChats());

		JTextField jTextFieldMessage = new JHintTextField("Message");
		providerJPanel.add(jTextFieldMessage, BorderLayout.NORTH);

		JButton sendMsgJButton = new JButton("Send");
		sendMsgJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String chatMessage = jTextFieldMessage.getText();
				Chat chat = new Chat(chatMessage, agent.getProvider(), project.getClient(), true);
				project.chatUpdate(chat);
				agent.sendMessage(project.getClientAID(), chatMessage, project.getId(), Ontology.PROVIDER_TO_CLIENT);
				updateRightLabel(project.getName(), project.getDescription(), project.getProgress(),
						project.getChats());
			}
		});
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(sendMsgJButton, BorderLayout.NORTH);
		JButton progressJButton = new JButton("10% progress");
		southPanel.add(progressJButton, BorderLayout.SOUTH);
		providerJPanel.add(southPanel, BorderLayout.SOUTH);
		progressJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String messageText = "10%";
				project.setProgress(10);
				agent.sendMessage(project.getClientAID(), messageText, project.getId(), Ontology.REPORTING);
				updateRightLabel(project.getName(), project.getDescription(), project.getProgress(),
						project.getChats());
			}
		});
		return providerJPanel;
	}

	private JPanel getClientJPanel() {
		
		ClientAgent agent = (ClientAgent) this.myAgent;
		Project project = this.project;
		
		JPanel clientJPanel = new JPanel();
		clientJPanel.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(chatJLabel, BorderLayout.EAST);
		centerPanel.add(projectJLabel, BorderLayout.WEST);
		clientJPanel.add(centerPanel, BorderLayout.CENTER);

		updateRightLabel(project.getName(), project.getDescription(), project.getProgress(), project.getChats());

		JTextField jTextFieldMessage = new JHintTextField("Message");
		clientJPanel.add(jTextFieldMessage, BorderLayout.NORTH);

		JButton sendMsgJButton = new JButton("Send");
		sendMsgJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String chatMessage = jTextFieldMessage.getText();
				Chat chat = new Chat(chatMessage, project.getProvider(), agent.getClient(), true);
				project.chatUpdate(chat);
				agent.sendMessage(project.getProviderAID(), chatMessage, project.getId(), Ontology.ACLMESSAGE_CHAT);
				updateRightLabel(project.getName(), project.getDescription(), project.getProgress(),
						project.getChats());
			}
		});
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(sendMsgJButton, BorderLayout.NORTH);
		JButton doneJButton = new JButton("Pay after 100%!");
		southPanel.add(doneJButton, BorderLayout.SOUTH);
		clientJPanel.add(southPanel, BorderLayout.SOUTH);
		doneJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String rating = showRatingDialog(project, agent);
				System.out.print(rating);
				jFrame.dispose();
				String messageText = "Done";
				agent.sendMessage(project.getProviderAID(), messageText, project.getId(), Ontology.ACLMESSAGE_DONE);
			}
		});
		return clientJPanel;
	}

	/**
	 * Update right label.
	 *
	 * @param name        the name
	 * @param description the description
	 * @param progress    the progress
	 * @param chats       the message history
	 */
	public void updateRightLabel(String name, String description, int progress, ArrayList<Chat> chats) {
		updateLeftLabel(name, description, progress);
		StringBuilder text = new StringBuilder("<html>");
		for (Chat chat : chats)
			text.append(chat.getText()).append("<br/>");
		text.append("</html>");
		chatJLabel.setText(text.toString());
	}

	/**
	 * Update left label.
	 *
	 * @param name        the name
	 * @param description the description
	 * @param progress    the progress
	 */
	private void updateLeftLabel(String name, String description, int progress) {
		StringBuilder text = new StringBuilder("<html>");
		text.append("Name: ").append(name).append("<br/>");
		text.append("Description: ").append(description).append("<br/>");
		text.append("Progress: ").append(progress).append("<br/>").append("<br/>").append("<br/>").append("<br/>");
		text.append("</html>");
		projectJLabel.setText(text.toString());
	}

	/**
	 * Show rating dialog.
	 *
	 * @param project the project
	 * @param agent   the agent
	 * @return the string
	 */
	private String showRatingDialog(Project project, ClientAgent agent) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setSize(400, 400);
		JHintTextField ratingTextField = new JHintTextField("Rating from 1 to 5");
		ratingTextField.setPreferredSize(new Dimension(200, 24));

		JHintTextField commentTextField = new JHintTextField("Comment");
		commentTextField.setPreferredSize(new Dimension(200, 24));

		JButton jButton = new JButton("Done");

		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				agent.payMoney(project);
				agent.sendRating(project.getProviderAID(), ratingTextField.getText());

			}
		});

		panel.add(ratingTextField);
		panel.add(commentTextField);
		panel.add(jButton);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return ratingTextField.getText();
	}

	/**
	 * Show GUI.
	 */
	public void showGUI() {
		this.jFrame.setVisible(true);
	}

	/**
	 * Dispose GUI.
	 */
	public void disposeGUI() {
		this.jFrame.dispose();
	}
}
