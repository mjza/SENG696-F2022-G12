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

import jade.core.AID;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import ca.ucalagary.seng696.g12.agents.ClientAgent;
import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.dictionary.Provider;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * The Class ClientGUI.
 */
public class ClientGUI {

	/** The j frame. */
	private JFrame jFrame;

	/** The providers list. */
	private JTable providersJTable;

	/** The projects J table. */
	private JTable projectsJTable;

	/** The selected provider. */
	AID selectedProviderAID = null;

	/** The client agent. */
	ClientAgent clientAgent;

	/**
	 * Instantiates a new client GUI.
	 *
	 * @param clientAgent the client agent
	 */
	public ClientGUI(ClientAgent clientAgent) {
		this.clientAgent = clientAgent;
		// Initiate the jFrame
		this.jFrame = new JFrame("B2B Match Making System: " + clientAgent.getLocalName());
		// set icon
		this.jFrame.setIconImage(SystemAgent.getIcon());
		// Set the size and position of the GUI to the half right-hand-side of the
		// screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.jFrame.getGraphicsConfiguration());
		int taskBarHeight = scnMax.bottom;
		this.jFrame.setSize(screenSize.width / 2, screenSize.height - taskBarHeight);
		this.jFrame.setLocation(screenSize.width / 2, 0);
		// Kill the agent when user closes the window
		this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
				clientAgent.killAgent();
			}
		});
		// set the content in jFrame
		this.jFrame.add(this.getClientJPanel());
	}

	/**
	 * Gets the client J panel.
	 *
	 * @return the client J panel
	 */
	public JPanel getClientJPanel() {
		JPanel clientJPanel = new JPanel();
		clientJPanel.setLayout(new BoxLayout(clientJPanel, BoxLayout.Y_AXIS));
		// A sign for client window
		clientJPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));

		// A panel for search on providers
		JPanel searchPanel = new JPanel();
		clientJPanel.add(searchPanel);
		searchPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 10, 0); // padding
		JLabel label = new JLabel("Filter providers:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		searchPanel.add(label, gbc);
		JTextField searchTextField = new JTextField();
		label.setLabelFor(searchTextField);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		searchPanel.add(searchTextField, gbc);

		// A panel for providers table
		JPanel providerPanel = new JPanel();
		clientJPanel.add(providerPanel);
		providerPanel.setLayout(new BorderLayout());
		providerPanel.add(new JLabel("List of Providers:"), BorderLayout.NORTH);
		String[] columnNames = Provider.getColumns(false);
		TableModel tableModel = new DefaultTableModel(columnNames, 0);
		JTable providersJTable = new JTable(tableModel);
		providersJTable.setFillsViewportHeight(true);
		this.providersJTable = providersJTable;
		this.updateProvidersJTableData(null);
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(providersJTable);
		// Add the scroll pane to center of guest panel.
		providerPanel.add(scrollPane, BorderLayout.CENTER);

		// A panel for projects table
		JPanel projectPanel = new JPanel();
		clientJPanel.add(projectPanel);
		projectPanel.setLayout(new BorderLayout());
		projectPanel.add(new JLabel("List of Projects:"), BorderLayout.NORTH);
		String[] projectColumnNames = Project.getColumns(false);
		TableModel projectTableModel = new DefaultTableModel(projectColumnNames, 0);
		JTable projectJTable = new JTable(projectTableModel);
		projectJTable.setFillsViewportHeight(true);
		this.projectsJTable = projectJTable;
		this.updateProjectsJTableData();
		// Create the scroll pane and add the table to it.
		JScrollPane projectScrollPane = new JScrollPane(projectJTable);
		// Add the scroll pane to center of guest panel.
		projectPanel.add(projectScrollPane, BorderLayout.CENTER);

		// A panel for biding
		JPanel createProjectPanel = new JPanel();
		createProjectPanel.setBorder(new EmptyBorder(10, 10, 20, 10));
		clientJPanel.add(createProjectPanel);
		createProjectPanel.setLayout(new GridBagLayout());
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 0, 10);
		createProjectPanel.add(new JLabel("Create a new project", SwingConstants.CENTER), gbc);
		//
		JLabel projectNameLabel = new JLabel("Project Name:");
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		createProjectPanel.add(projectNameLabel, gbc);
		JTextField projectNameJTextField = new JTextField();
		projectNameLabel.setLabelFor(projectNameJTextField);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		createProjectPanel.add(projectNameJTextField, gbc);
		//
		JLabel projectDescriptionJLabel = new JLabel("Project Description:");
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		createProjectPanel.add(projectDescriptionJLabel, gbc);
		JTextField projectDescriptionJTextField = new JTextField();
		projectDescriptionJLabel.setLabelFor(projectDescriptionJTextField);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		createProjectPanel.add(projectDescriptionJTextField, gbc);
		//
		JLabel projectProviderJLabel = new JLabel("Project provider:");
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		createProjectPanel.add(projectProviderJLabel, gbc);
		HintTextField projectProviderJTextField = new HintTextField("Select a row in the provider table");
		projectProviderJTextField.setEditable(false);
		projectProviderJLabel.setLabelFor(projectProviderJTextField);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		createProjectPanel.add(projectProviderJTextField, gbc);
		//
		JLabel projectDeadlineJLabel = new JLabel("Project Deadline:");
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		createProjectPanel.add(projectDeadlineJLabel, gbc);
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		JDatePanelImpl projectDeadlineDatePanel = new JDatePanelImpl(model, properties);
		projectDeadlineDatePanel.setPreferredSize(new Dimension(200, 200));
		projectDeadlineJLabel.setLabelFor(projectDeadlineDatePanel);
		gbc.gridx = 1;
		gbc.weightx = 0;
		createProjectPanel.add(projectDeadlineDatePanel, gbc);
		//
		JLabel projectBidJLabel = new JLabel("Project Bid:");
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		createProjectPanel.add(projectBidJLabel, gbc);
		JTextField projectBidJTextField = new JTextField();
		projectBidJLabel.setLabelFor(projectBidJTextField);
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		createProjectPanel.add(projectBidJTextField, gbc);
		//
		JButton createProjectJButton = new JButton("Create the project");
		createProjectJButton.setPreferredSize(new Dimension(100, 22));
		gbc.gridx = 1;
		gbc.gridy++;
		gbc.weightx = 1.0;
		createProjectPanel.add(createProjectJButton, gbc);

		// Event handlers
		searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			private void filterProviders(DocumentEvent e) {
				String searchedText = searchTextField.getText();
				if (searchedText.isEmpty()) {
					updateProvidersJTableData(null);
				} else {
					updateProvidersJTableData(searchedText);
				}
			}

			public void changedUpdate(DocumentEvent e) {
				this.filterProviders(e);
			}

			public void removeUpdate(DocumentEvent e) {
				this.filterProviders(e);
			}

			public void insertUpdate(DocumentEvent e) {
				this.filterProviders(e);
			}

		});
		// set the provider for new project
		providersJTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable source = (JTable) e.getSource();
				int row = source.getSelectedRow();
				String providerUserName = (String) source.getModel().getValueAt(row, 2);
				projectProviderJTextField.setText(providerUserName);
				selectedProviderAID = clientAgent.getProviderAID(providerUserName);
			}
		});

		createProjectJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedProviderAID == null) {
					return;
				}
				String projectName = projectNameJTextField.getText().trim();
				String projectDescription = projectDescriptionJTextField.getText().trim();
				String projectBid = projectBidJTextField.getText().trim();
				Date projectDeadLine = model.getValue();
				AID clientAID = clientAgent.getAID();
				AID providerAID = selectedProviderAID;

				if (projectName != null && projectDescription != null && projectBid != null && projectDeadLine != null
						&& projectName.length() > 0 && projectDescription.length() > 0 && projectBid.length() > 0) {
					Project project = new Project(projectName, projectDescription, Integer.parseInt(projectBid),
							providerAID, clientAID, projectDeadLine);
					clientAgent.sendProposal(project, providerAID);
				} else {
					showMissingData();
				}

			}

		});

		return clientJPanel;

	}

	/**
	 * Show missing data.
	 */
	public void showMissingData() {
		JOptionPane.showMessageDialog(jFrame, "Please fill all active textboxes.", "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Update provider table data.
	 *
	 * @param filter the filter
	 */
	private void updateProvidersJTableData(String filter) {
		if (this.providersJTable != null) {
			List<Provider> providers = clientAgent.getProviders(filter);
			String[] columnNames = Provider.getColumns(false);
			String[][] stringArray = providers.stream().sorted(Comparator.comparingInt(Provider::getPremium).reversed())
					.map(provider -> provider.toArray(false)).toArray(String[][]::new);
			DefaultTableModel tableModel = (DefaultTableModel) this.providersJTable.getModel();
			tableModel.setDataVector(stringArray, columnNames);
			tableModel.fireTableDataChanged();
		}
	}

	/**
	 * Update projects J table data.
	 */
	private void updateProjectsJTableData() {
		if (this.projectsJTable != null) {
			List<Project> projects = clientAgent.getProjects();
			String[] columnNames = Project.getColumns(false);
			String[][] stringArray = projects.stream()
					.sorted(Comparator.comparingLong(Project::getTimestamp).reversed())
					.map(project -> project.toArray(false)).toArray(String[][]::new);
			DefaultTableModel tableModel = (DefaultTableModel) this.projectsJTable.getModel();
			tableModel.setDataVector(stringArray, columnNames);
			tableModel.fireTableDataChanged();
		}
	}

	/**
	 * Gets the client J panel.
	 *
	 * @return the client J panel
	 */
	public JPanel getClientJPanel2() {
		/*
		 * Set<AID> providers = null ; //= clientAgent.getProviders();
		 * 
		 * JPanel clientJPanel = new JPanel(); clientJPanel.setLayout(new
		 * BorderLayout()); clientJPanel.setBorder(BorderFactory.createMatteBorder(1, 1,
		 * 1, 1, Color.BLUE));
		 * 
		 * JPanel leftPanel = new JPanel(); leftPanel.setLayout(new BorderLayout());
		 * leftPanel.setSize(600, 600);
		 * 
		 * providersList = new DefaultListModel<>();
		 * 
		 * for (AID provider : providers) { Provider provider1 =
		 * SystemAgent.getProvider(provider.getLocalName().split(":")[1]);
		 * currentProviders.add(provider1); String text = provider1.getInfo();
		 * providersList.addElement(text); } JList<String> list = new
		 * JList<>(providersList);
		 * 
		 * list.setCellRenderer(new ListCellRenderer(currentProviders));
		 * 
		 * list.addListSelectionListener(new ListSelectionListener() {
		 * 
		 * @Override public void valueChanged(ListSelectionEvent e) { if
		 * (!e.getValueIsAdjusting()) { System.out.println(list.getSelectedIndex()); for
		 * (AID provider : providers) { Provider currentProvider =
		 * SystemAgent.getProvider(provider.getLocalName().split(":")[1]); if
		 * (currentProvider.getInfo().equals(list.getSelectedValue())) { = provider; } }
		 * } } });
		 * 
		 * JPanel providerPanel = new JPanel(); providerPanel.setLayout(new
		 * BorderLayout()); providerPanel.add(new JLabel("Providers:"),
		 * BorderLayout.NORTH); providerPanel.add(list, BorderLayout.CENTER);
		 * leftPanel.add(providerPanel, BorderLayout.SOUTH);
		 * 
		 * HintTextField searchTextField = new HintTextField("Search Provider");
		 * searchTextField.setSize(new Dimension(200, 24));
		 * leftPanel.add(searchTextField, BorderLayout.NORTH);
		 * searchTextField.getDocument().addDocumentListener(new DocumentListener() {
		 * public void changedUpdate(DocumentEvent e) { searchProviders(e); }
		 * 
		 * public void removeUpdate(DocumentEvent e) { searchProviders(e); }
		 * 
		 * public void insertUpdate(DocumentEvent e) { searchProviders(e); }
		 * 
		 * private void searchProviders(DocumentEvent e) { String searchedText =
		 * searchTextField.getText(); if (searchedText.isEmpty()) {
		 * providersList.removeAllElements(); for (Provider provider : currentProviders)
		 * { providersList.addElement(provider.getInfo()); } } else {
		 * providersList.removeAllElements(); List<Provider> searchedProviders =
		 * SystemAgent.searchProvider(searchedText); for (Provider provider :
		 * searchedProviders) { if (provider.isPremium()) { }
		 * providersList.addElement(provider.getInfo()); } } } });
		 * 
		 * projectsListModel = new DefaultListModel<>();
		 * 
		 * for (Project project : this.projects) {
		 * projectsListModel.addElement(project.getName()); }
		 * 
		 * JList<String> projectList = new JList<>(projectsListModel);
		 * 
		 * projectList.addMouseListener(new MouseAdapter() { public void
		 * mouseClicked(MouseEvent evt) { JList<?> list = (JList<?>) evt.getSource(); if
		 * (evt.getClickCount() == 2) { int index =
		 * list.locationToIndex(evt.getPoint()); ProjectGUI projectDetailGUI = new
		 * ProjectGUI(clientAgent, ClientGUI.this.projects.get(index));
		 * projectDetailGUI.showGUI(); System.out.println("Clicked: " + index); } } });
		 * JPanel projectPanel = new JPanel(); projectPanel.setLayout(new
		 * BorderLayout()); projectPanel.add(new JLabel("Projects"),
		 * BorderLayout.NORTH); projectPanel.add(projectList, BorderLayout.CENTER);
		 * leftPanel.add(projectPanel, BorderLayout.CENTER); clientJPanel.add(leftPanel,
		 * BorderLayout.WEST);
		 * 
		 * JTextArea jTextAreaDescription = new JTextArea("Project Description");
		 * jTextAreaDescription.setRows(20); jTextAreaDescription.setColumns(20);
		 * 
		 * JPanel jPanel1 = new JPanel(); jPanel1.setLayout(new BorderLayout());
		 * 
		 * JTextField jTextFieldName = new HintTextField("Name");
		 * jPanel1.add(jTextFieldName, BorderLayout.NORTH);
		 * jPanel1.add(jTextAreaDescription, BorderLayout.CENTER);
		 * 
		 * clientJPanel.add(jPanel1, BorderLayout.CENTER);
		 * 
		 * HintTextField bid = new HintTextField("BID:"); bid.setPreferredSize(new
		 * Dimension(200, 24)); JButton jButtonSend = new JButton("Create");
		 * 
		 * JPanel jPanelNewMessage = new JPanel(); jPanelNewMessage.add(bid,
		 * BorderLayout.CENTER); jPanelNewMessage.add(jButtonSend, BorderLayout.WEST);
		 * 
		 * UtilDateModel model = new UtilDateModel(); Properties p = new Properties();
		 * p.put("text.today", "Today"); p.put("text.month", "Month");
		 * p.put("text.year", "Year"); JDatePanelImpl datePanel = new
		 * JDatePanelImpl(model, p); datePanel.setPreferredSize(new Dimension(200,
		 * 200)); jPanelNewMessage.add(datePanel, BorderLayout.SOUTH);
		 * 
		 * clientJPanel.add(jPanelNewMessage, BorderLayout.SOUTH);
		 * 
		 * jButtonSend.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { if (selectedProvider
		 * == null) { return; } Project project = new Project(jTextFieldName.getText(),
		 * jTextAreaDescription.getText(), Integer.parseInt(bid.getText()),
		 * selectedProvider, clientAgent.getAID(), model.getValue());
		 * System.out.println(jTextFieldName.getText() + "  " + project.compact());
		 * clientAgent.sendProposal(project, selectedProvider); } });
		 * 
		 * jPanelNewMessage.add(bid, BorderLayout.CENTER);
		 * jPanelNewMessage.add(jButtonSend, BorderLayout.SOUTH);
		 * 
		 * clientJPanel.add(jPanelNewMessage, BorderLayout.SOUTH);
		 * 
		 * return clientJPanel;
		 */
		return null;
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
	 * Adds the project.
	 *
	 * @param project the project
	 */
	public void addProject(Project project) {
		// this.projects.add(project);
		// projectsListModel.addElement(project.getName());
	}

	/**
	 * Update projects.
	 *
	 * @param projects the projects
	 */
	public void updateProjects(List<Project> projects) {
		// this.projects = projects;
		// projectsListModel.clear();
		// for (Project project : this.projects) {
		// projectsListModel.addElement(project.getName());
		// }
	}

	public static void main(String[] args) {
		ClientGUI clientGUI = new ClientGUI(new ClientAgent());
		clientGUI.showGUI();
	}
}
