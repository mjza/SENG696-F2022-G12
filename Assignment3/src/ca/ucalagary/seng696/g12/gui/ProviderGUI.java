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
import ca.ucalagary.seng696.g12.dictionary.Project;
import jade.wrapper.ControllerException;

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
	
	/** The projects. */
	List<Project> projects;
	
	
	/** The premium label. */
	JLabel premiumLabel;
	
	/** The my agent. */
	ProviderAgent myAgent;

	/**
	 * Instantiates a new provider GUI.
	 *
	 * @param myAgent the my agent
	 * @param projects the projects
	 */
	public ProviderGUI(ProviderAgent myAgent, List<Project> projects) {
		this.myAgent = myAgent;
		jFrame = new JFrame("Welcome " + myAgent.getLocalName());

		this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
				myAgent.killAgent(myAgent.getLocalName());
			}
		});
		this.projects = projects;
		jFrame.setSize(400, 400);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());

		premiumLabel = new JLabel();

		updatePremium();

		JPanel jPanelNewMessage = new JPanel();

		jPanelNewMessage.add(premiumLabel, BorderLayout.SOUTH);

		jPanel.add(jPanelNewMessage, BorderLayout.CENTER);

		if (!myAgent.getProvider().isPremium()) {
			JButton premiumButton = new JButton("Go premium!");
			premiumButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (myAgent.goPremium()) {
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
						myAgent.withdraw();
			}
		});
		jPanelNewMessage.add(withdrawButton, BorderLayout.SOUTH);
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setSize(100, 400);

		projectsListModel = new DefaultListModel<>();

		for (Project project : this.projects) {
			projectsListModel.addElement(project.getName());
		}

		JList<String> projectList = new JList<>(projectsListModel);
		projectList.setFixedCellHeight(50);
		projectList.setFixedCellWidth(100);

		projectList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					ProjectGUI projectDetailGUI = new ProjectGUI(myAgent, projects.get(index));
					projectDetailGUI.showGUI();
					System.out.println("Clicked: " + index);
				}
			}
		});

		leftPanel.add(projectList, BorderLayout.CENTER);
		jPanel.add(leftPanel, BorderLayout.WEST);

		jFrame.add(jPanel);
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
		projectsListModel.addElement(project.getName());
		this.projects.add(project);
	}


	/**
	 * Update premium.
	 */
	public void updatePremium() {
		premiumLabel.setText("You are" + (myAgent.getProvider().isPremium() ? " " : " not ") + "a premium user");
	}

	/**
	 * Update projects.
	 *
	 * @param projects the projects
	 */
	public void updateProjects(List<Project> projects) {
		System.out.println("UPDATING PROJECTS");
		this.projects = projects;
		projectsListModel.clear();
		for (Project project : this.projects) {
			projectsListModel.addElement(project.getName());
		}
	}
}
