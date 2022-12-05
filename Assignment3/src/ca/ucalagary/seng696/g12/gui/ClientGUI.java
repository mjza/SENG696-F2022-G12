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
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
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
		// Set the size and position of the GUI to the right-hand-side of the screen
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
		TableModel tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
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
		TableModel projectTableModel = new DefaultTableModel(projectColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable projectsJTable = new JTable(projectTableModel);
		projectsJTable.setFillsViewportHeight(true);
		this.projectsJTable = projectsJTable;
		this.updateProjectsJTableData();
		// Create the scroll pane and add the table to it.
		JScrollPane projectScrollPane = new JScrollPane(projectsJTable);
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
		JHintTextField projectProviderJTextField = new JHintTextField("Select a row in the provider table");
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
		// restrict to digits
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		JFormattedTextField projectBidJTextField = new JFormattedTextField(formatter);
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
				if (source.getRowCount() > 0) {
					int row = source.getSelectedRow();
					if (row >= 0) {
						String providerUserName = (String) source.getModel().getValueAt(row, 2);
						projectProviderJTextField.setText(providerUserName);
						selectedProviderAID = clientAgent.getProviderAID(providerUserName);
					}
				}
			}
		});
		// open project dialog
		projectsJTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable source = (JTable) e.getSource();
				if (source.getRowCount() > 0 && e.getClickCount() == 2) {
					int row = source.getSelectedRow();
					if (row >= 0) {
						String projectId = (String) source.getModel().getValueAt(row, 0);
						Project project = clientAgent.getProject(Integer.parseInt(projectId));
						ProjectGUI projectGUI = new ProjectGUI(clientAgent, project);
						projectGUI.showGUI();
					}
				}
			}
		});

		// submit button
		createProjectJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedProviderAID == null) {
					showSelectAProvider();
					return;
				}
				String projectName = projectNameJTextField.getText().trim();
				String projectDescription = projectDescriptionJTextField.getText().trim();
				String projectBid = projectBidJTextField.getText().trim().replaceAll("[^0-9]", "");
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
		JOptionPane.showMessageDialog(jFrame, "Please fill all active textboxes.", "Ops: Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show select A provider.
	 */
	public void showSelectAProvider() {
		JOptionPane.showMessageDialog(jFrame, "Please select a provider in the provider table first.", "Ops: Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Update provider table data.
	 *
	 * @param filter the filter
	 */
	public void updateProvidersJTableData(String filter) {
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
	public void updateProjectsJTableData() {
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
	 * Gets the j frame.
	 *
	 * @return the j frame
	 */
	public JFrame getjFrame() {
		return jFrame;
	}

	/**
	 * The main method just for test.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		ClientGUI clientGUI = new ClientGUI(new ClientAgent());
		clientGUI.showGUI();
	}
}
