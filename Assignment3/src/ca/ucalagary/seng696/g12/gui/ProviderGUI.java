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

import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.dictionary.Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The Class ProviderGUI.
 */
public class ProviderGUI {

	/** The j frame. */
	JFrame jFrame;
	
	/** The projects list model. */
	DefaultListModel<String> projectsListModel;
	
	/** The premium label. */
	JLabel premiumLabel;
	
	/** The provider agent. */
	ProviderAgent providerAgent;

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
		this.jFrame.setSize(screenSize.width/2, screenSize.height - taskBarHeight);
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
	
	private JPanel getProviderJPanel() {
		JPanel providerJPanel = new JPanel();
		providerJPanel.setLayout(new BorderLayout());
		
		providerJPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));

		premiumLabel = new JLabel();

		updatePremium();

		JPanel jPanelNewMessage = new JPanel();

		jPanelNewMessage.add(premiumLabel, BorderLayout.SOUTH);

		providerJPanel.add(jPanelNewMessage, BorderLayout.CENTER);

		if (!providerAgent.getProvider().isPremium()) {
			JButton premiumButton = new JButton("Go premium!");
			premiumButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (providerAgent.goPremium()) {
						premiumButton.setVisible(false);
					}
				}
			});
			jPanelNewMessage.add(premiumButton, BorderLayout.SOUTH);
		}
		JButton withdrawButton = new JButton("Witdraw service agreement");
		withdrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
						providerAgent.withdraw();
			}
		});
		jPanelNewMessage.add(withdrawButton, BorderLayout.SOUTH);
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setSize(100, 400);

		projectsListModel = new DefaultListModel<>();

		for (Project project : this.providerAgent.getProjects()) {
			projectsListModel.addElement(project.getName());
		}

		JList<String> projectList = new JList<>(projectsListModel);
		projectList.setFixedCellHeight(50);
		projectList.setFixedCellWidth(100);

		projectList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					ProjectGUI projectDetailGUI = new ProjectGUI(providerAgent, providerAgent.getProjects().get(index));
					projectDetailGUI.showGUI();
					System.out.println("Clicked: " + index);
				}
			}
		});

		leftPanel.add(projectList, BorderLayout.CENTER);
		providerJPanel.add(leftPanel, BorderLayout.WEST);
		return providerJPanel;
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
		//projectsListModel.addElement(project.getName());
		//this.projects.add(project);
	}


	/**
	 * Update premium.
	 */
	public void updatePremium() {
		premiumLabel.setText("You are" + (providerAgent.getProvider().isPremium() ? " " : " not ") + "a premium user");
	}

	/**
	 * Update projects.
	 *
	 * @param projects the projects
	 */
	public void updateProjects(List<Project> projects) {		
		projectsListModel.clear();
		for (Project project : this.providerAgent.getProjects()) {
			projectsListModel.addElement(project.getName());
		}
	}
}
