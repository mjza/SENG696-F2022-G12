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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.dictionary.Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

/**
 * The Class ProviderGUI.
 */
public class ProviderGUI {

	/** The j frame. */
	private JFrame jFrame;

	/** The projects J table. */
	private JTable projectsJTable;

	/** The premium label. */
	private JLabel premiumJLabel = new JLabel();

	/** The provider agent. */
	private ProviderAgent providerAgent;

	/**
	 * Instantiates a new provider GUI.
	 *
	 * @param providerAgent the my agent
	 */
	public ProviderGUI(ProviderAgent providerAgent) {
		this.providerAgent = providerAgent;
		// Initiate the jFrame
		this.jFrame = new JFrame("B2B Match Making System: " + providerAgent.getLocalName());
		// set icon
		this.jFrame.setIconImage(SystemAgent.getIcon());
		// Set the size and position of the GUI to the left-hand-side of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(this.jFrame.getGraphicsConfiguration());
		int taskBarHeight = scnMax.bottom;
		this.jFrame.setSize(screenSize.width / 2, screenSize.height - taskBarHeight);
		this.jFrame.setLocation(0, 0);
		// Kill the agent when user closes the window
		this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
				providerAgent.killAgent();
			}
		});
		// set the content in jFrame
		this.jFrame.add(this.getProviderJPanel());
	}

	/**
	 * Gets the provider J panel.
	 *
	 * @return the provider J panel
	 */
	private JPanel getProviderJPanel() {
		JPanel providerJPanel = new JPanel();
		providerJPanel.setLayout(new BorderLayout());
		// A provider sign
		providerJPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
		// Get text for premium status
		updatePremium();
		premiumJLabel.setHorizontalAlignment(JLabel.CENTER);
		premiumJLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		providerJPanel.add(premiumJLabel, BorderLayout.NORTH);

		// A panel for projects table
		JPanel projectPanel = new JPanel();
		providerJPanel.add(projectPanel, BorderLayout.CENTER);
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

		// Show upgrade and downgrade buttons
		JPanel subscriptionJPanel = new JPanel();
		providerJPanel.add(subscriptionJPanel, BorderLayout.SOUTH);
		if (!providerAgent.getProvider().isPremium()) {
			JButton upgradeButton = new JButton("Upgrade to Premium");
			upgradeButton.setBackground(new Color(34, 139, 34));
			upgradeButton.setOpaque(true);
			upgradeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (providerAgent.upgrade2Premium()) {
						upgradeButton.setVisible(false);
					}
				}
			});
			subscriptionJPanel.add(upgradeButton, BorderLayout.CENTER);
		}
		JButton downgradeButton = new JButton("Downgrade to Client");
		downgradeButton.setBackground(new Color(220, 20, 60));
		downgradeButton.setOpaque(true);
		downgradeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				providerAgent.downgrade2Client();
			}
		});
		subscriptionJPanel.add(downgradeButton, BorderLayout.CENTER);

		// open project dialog
		projectsJTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable source = (JTable) e.getSource();
				if (source.getRowCount() > 0 && e.getClickCount() == 2) {
					int row = source.getSelectedRow();
					String projectName = (String) source.getModel().getValueAt(row, 1);
					Project project = providerAgent.getProject(projectName);
					ProjectGUI projectGUI = new ProjectGUI(providerAgent, project);
					projectGUI.showGUI();
				}
			}
		});

		return providerJPanel;
	}

	/**
	 * Update projects J table data.
	 */
	public void updateProjectsJTableData() {
		if (this.projectsJTable != null) {
			List<Project> projects = providerAgent.getProjects();
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
		jFrame.setVisible(true);
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
		// TODO: remove this function
		// projectsListModel.addElement(project.getName());
		// this.projects.add(project);
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
	 * Update premium.
	 */
	public void updatePremium() {
		String verb = providerAgent.getProvider().isPremium() ? " " : " not ";
		premiumJLabel.setText("<<You are" + verb + "a premium user>>");
	}

	/**
	 * Update projects.
	 *
	 * @param projects the projects
	 */
	public void updateProjects(List<Project> projects) {
		// TODO: check this function
		// projectsListModel.clear();
		// for (Project project : this.providerAgent.getProjects()) {
		// projectsListModel.addElement(project.getName());
		// }
	}
}
