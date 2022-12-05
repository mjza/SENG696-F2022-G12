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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ca.ucalagary.seng696.g12.agents.ClientAgent;
import ca.ucalagary.seng696.g12.agents.EnhancedAgent;
import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.dictionary.Chat;
import ca.ucalagary.seng696.g12.dictionary.Ontology;
import ca.ucalagary.seng696.g12.dictionary.Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

/**
 * The Class ProjectGUI.
 */
public class ProjectGUI {

	/** The j frame. */
	private JFrame jFrame;

	/** The j label. */
	private JLabel projectInformationJLabel = new JLabel();

	/** The projects J table. */
	private JTable chatsJTable;

	/** The project. */
	private Project project = null;

	/** The my agent. */
	private EnhancedAgent myAgent = null;

	/**
	 * Instantiates a new project GUI. It is shown when the user is a Provider
	 * 
	 * @param agent   the agent
	 * @param project the project
	 */
	public ProjectGUI(ProviderAgent agent, Project project) {
		// Set class variables
		this.myAgent = agent;
		this.project = project;
		// Register yourself as the connection point
		project.bindGUI(this);
		// Set up the JFrame
		this.jFrame = new JFrame(
				"Project(" + project.getId() + "/" + project.getName() + ") of " + agent.getProvider().getName());
		this.decorateJFrame(true);
		// load panel
		this.jFrame.add(this.getProviderJPanel());
	}

	/**
	 * Instantiates a new project GUI.
	 *
	 * @param agent   the agent
	 * @param project the project
	 */
	public ProjectGUI(ClientAgent agent, Project project) {
		// Set class variables
		this.myAgent = agent;
		this.project = project;
		// Register yourself as the connection point
		project.bindGUI(this);
		// Set up the JFrame
		this.jFrame = new JFrame(
				"Project(" + project.getId() + "/" + project.getName() + ") of " + agent.getClient().getName());
		this.decorateJFrame(false);
		// load panel
		this.jFrame.add(this.getClientJPanel());
	}

	/**
	 * Decorate J frame.
	 *
	 * @param isProvider the is provider
	 */
	private void decorateJFrame(boolean isProvider) {
		// set icon
		this.jFrame.setIconImage(SystemAgent.getIcon());
		// Set the size and position of the GUI to the right-hand-side of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.jFrame.getGraphicsConfiguration());
		int taskBarHeight = scnMax.bottom;
		int width = screenSize.width / 3;
		int height = (screenSize.height - taskBarHeight) / 2;
		int offset = isProvider ? 0 : (screenSize.width / 2);
		int posX = (((screenSize.width / 2) - width) / 2) + offset;
		int posY = ((screenSize.height - taskBarHeight) - height) / 2;
		this.jFrame.setSize(width, height);
		this.jFrame.setLocation(posX, posY);
		// Do not kill the agent
		this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
			}
		});
	}

	private JPanel getProviderJPanel() {
		ProviderAgent agent = (ProviderAgent) this.myAgent;
		Project project = this.project;
		// main panel
		JPanel providerJPanel = new JPanel();
		providerJPanel.setLayout(new BorderLayout());
		// A provider sign
		providerJPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
		// place the project info
		JPanel projectJPanel = new JPanel();
		projectJPanel.setLayout(new BorderLayout());
		providerJPanel.add(projectJPanel, BorderLayout.NORTH);
		// Title
		JLabel titleJLabel = new JLabel("<<Project Details>>", SwingConstants.CENTER);
		projectJPanel.add(titleJLabel, BorderLayout.NORTH);
		projectJPanel.add(projectInformationJLabel, BorderLayout.CENTER);		
		this.updateProjectInformation();
		// A panel for chat table
		JPanel chatTableJPanel = new JPanel();
		providerJPanel.add(chatTableJPanel, BorderLayout.CENTER);
		chatTableJPanel.setLayout(new BorderLayout());
		chatTableJPanel.add(new JLabel("Chat history:"), BorderLayout.NORTH);
		String[] chatColumnNames = Chat.getColumns();
		TableModel chatTableModel = new DefaultTableModel(chatColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable chatsJTable = new JTable(chatTableModel);
		chatsJTable.setFillsViewportHeight(true);
		this.chatsJTable = chatsJTable;
		this.updateChatsJTableData();
		// Create the scroll pane and add the table to it.
		JScrollPane chatTableScrollPane = new JScrollPane(chatsJTable);
		// Add the scroll pane to center of guest panel.
		chatTableJPanel.add(chatTableScrollPane, BorderLayout.CENTER);
		// New message panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		providerJPanel.add(bottomPanel, BorderLayout.SOUTH);
		//
		JTextField messageJTextField = new JHintTextField("Message");
		bottomPanel.add(messageJTextField, BorderLayout.CENTER);
		//
		JButton sendMsgJButton = new JButton("Send");
		bottomPanel.add(sendMsgJButton, BorderLayout.EAST);
		//
		JButton progressJButton = new JButton("10% progress");
		bottomPanel.add(progressJButton, BorderLayout.SOUTH);
		if(project.isDone()) {
			progressJButton.setEnabled(false);
		}
		// Event handler for send button
		sendMsgJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String chatMessage = messageJTextField.getText();
				Chat chat = new Chat(chatMessage, agent.getProvider(), project.getClient(), true);
				project.chatUpdate(chat);
				agent.sendMessage(project.getClientAID(), chatMessage, project.getId(), Ontology.PROVIDER_TO_CLIENT);
				updateChatsJTableData();
			}
		});
		// Event handler for progress
		progressJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String chatMessage = "10% progress";
				Chat chat = new Chat(chatMessage, agent.getProvider(), project.getClient(), true);				
				if (project.setProgress(10)) {
					project.chatUpdate(chat);
					agent.sendMessage(project.getClientAID(), chatMessage, project.getId(), Ontology.REPORTING);
					updateChatsJTableData();
					updateChatsJTableData();
				} else {
					progressJButton.setEnabled(false);
				}
			}
		});
		return providerJPanel;
	}

	/**
	 * Gets the client J panel.
	 *
	 * @return the client J panel
	 */
	private JPanel getClientJPanel() {
		ClientAgent agent = (ClientAgent) this.myAgent;
		Project project = this.project;
		// main panel
		JPanel clientJPanel = new JPanel();
		clientJPanel.setLayout(new BorderLayout());
		// A client sign
		clientJPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
		// place the project info
		JPanel projectJPanel = new JPanel();
		projectJPanel.setLayout(new BorderLayout());
		clientJPanel.add(projectJPanel, BorderLayout.NORTH);
		// Title
		JLabel titleJLabel = new JLabel("<<Project Details>>", SwingConstants.CENTER);
		projectJPanel.add(titleJLabel, BorderLayout.NORTH);
		projectJPanel.add(projectInformationJLabel, BorderLayout.CENTER);
		this.updateProjectInformation();
		// A panel for chat table
		JPanel chatTableJPanel = new JPanel();
		clientJPanel.add(chatTableJPanel, BorderLayout.CENTER);
		chatTableJPanel.setLayout(new BorderLayout());
		chatTableJPanel.add(new JLabel("Chat history:"), BorderLayout.NORTH);
		String[] chatColumnNames = Chat.getColumns();
		TableModel chatTableModel = new DefaultTableModel(chatColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable chatsJTable = new JTable(chatTableModel);
		chatsJTable.setFillsViewportHeight(true);
		this.chatsJTable = chatsJTable;
		this.updateChatsJTableData();
		// Create the scroll pane and add the table to it.
		JScrollPane chatTableScrollPane = new JScrollPane(chatsJTable);
		// Add the scroll pane to center of guest panel.
		chatTableJPanel.add(chatTableScrollPane, BorderLayout.CENTER);
		// New message panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		clientJPanel.add(bottomPanel, BorderLayout.SOUTH);
		//
		JTextField messageJTextField = new JHintTextField("Message");
		bottomPanel.add(messageJTextField, BorderLayout.CENTER);
		//
		JButton sendMsgJButton = new JButton("Send");
		bottomPanel.add(sendMsgJButton, BorderLayout.EAST);
		//
		JButton payJButton = new JButton("Pay after 100%!");
		bottomPanel.add(payJButton, BorderLayout.SOUTH);
		if(project.isPaid()) {
			payJButton.setEnabled(false);
		}
		// Event handler for send button
		sendMsgJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String chatMessage = messageJTextField.getText();
				Chat chat = new Chat(chatMessage, project.getProvider(), agent.getClient(), true);
				project.chatUpdate(chat);
				agent.sendMessage(project.getProviderAID(), chatMessage, project.getId(), Ontology.ACLMESSAGE_CHAT);
				updateChatsJTableData();
			}
		});
		// Event handler for progress
		payJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jFrame.dispose();
				agent.payMoney(project);
				String messageText = "Done";
				agent.sendMessage(project.getProviderAID(), messageText, project.getId(), Ontology.ACLMESSAGE_DONE);
				RatingGUI ratingGUI = new RatingGUI(agent, project);
				ratingGUI.showGUI();			
			}
		});
		return clientJPanel;
	}

	/**
	 * Update project information.
	 *
	 * @param name        the name
	 * @param description the description
	 * @param progress    the progress
	 */
	public void updateProjectInformation() {
		StringBuilder text = new StringBuilder("<html><p>");		
		text.append("Id: <i>").append(this.project.getId()).append("</i><br/>");
		text.append("Name: <i>").append(this.project.getName()).append("</i><br/>");
		text.append("Description: <i>").append(this.project.getDescription()).append("</i><br/>");
		text.append("Progress: <i>").append(this.project.getProgress()).append("</i><br/>");
		text.append("Deadline: <i>").append(this.project.getDeadline().toString()).append("</i><br/>");
		text.append("<hr/>").append("<br/>");
		text.append("</p></html>");
		projectInformationJLabel.setText(text.toString());
	}

	/**
	 * Update chats J table data.
	 */
	public void updateChatsJTableData() {
		if (this.chatsJTable != null) {
			List<Chat> chats = this.project.getChats();
			String[] columnNames = Chat.getColumns();
			String[][] stringArray = chats.stream().sorted(Comparator.comparingLong(Chat::getTimestamp).reversed())
					.map(chat -> chat.toArray()).toArray(String[][]::new);
			DefaultTableModel tableModel = (DefaultTableModel) this.chatsJTable.getModel();
			tableModel.setDataVector(stringArray, columnNames);
			tableModel.fireTableDataChanged();
		}
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
