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
import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.dictionary.Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class RatingGUI.
 */
public class RatingGUI {

	/** The j frame. */
	private JFrame jFrame;

	/** The j label. */
	private JLabel projectInformationJLabel = new JLabel();

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
	public RatingGUI(ProviderAgent agent, Project project) {
		// Set class variables
		this.myAgent = agent;
		this.project = project;
		// Set up the JFrame
		this.jFrame = new JFrame("Rating Project(" + project.getId() + "/" + project.getName() + ") of "
				+ agent.getProvider().getName());
		this.decorateJFrame(true);
		// load panel
		this.jFrame.add(this.getProviderJPanel());
		this.jFrame.pack();
		this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Instantiates a new project GUI.
	 *
	 * @param agent   the agent
	 * @param project the project
	 */
	public RatingGUI(ClientAgent agent, Project project) {
		// Set class variables
		this.myAgent = agent;
		this.project = project;
		// Set up the JFrame
		this.jFrame = new JFrame(
				"Rating Project(" + project.getId() + "/" + project.getName() + ") of " + agent.getClient().getName());
		this.decorateJFrame(false);
		// load panel
		this.jFrame.add(this.getClientJPanel());
		this.jFrame.pack();
		this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		int width = 480;
		int height = 360;
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

	/**
	 * Gets the provider J panel.
	 *
	 * @return the provider J panel
	 */
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
		// new panel for form
		JPanel formJPanel = new JPanel();
		formJPanel.setLayout(new BorderLayout());
		providerJPanel.add(formJPanel, BorderLayout.CENTER);
		// Rating
		JHintTextField ratingTextField = new JHintTextField("Rating from 1 to 5");
		ratingTextField.setPreferredSize(new Dimension(200, 24));
		formJPanel.add(ratingTextField, BorderLayout.WEST);
		// Comment
		JHintTextField commentTextField = new JHintTextField("Comment");
		commentTextField.setPreferredSize(new Dimension(200, 24));
		formJPanel.add(commentTextField, BorderLayout.EAST);
		//
		JButton submitJButton = new JButton("Submit");
		submitJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jFrame.dispose();
				agent.sendRating(project.getClientAID(), ratingTextField.getText());
			}
		});
		providerJPanel.add(submitJButton, BorderLayout.SOUTH);
		//
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
		// new panel for form
		JPanel formJPanel = new JPanel();
		formJPanel.setLayout(new BorderLayout());
		clientJPanel.add(formJPanel, BorderLayout.CENTER);
		// Rating
		JHintTextField ratingTextField = new JHintTextField("Rating from 1 to 5");
		ratingTextField.setPreferredSize(new Dimension(200, 24));
		formJPanel.add(ratingTextField, BorderLayout.WEST);
		// Comment
		JHintTextField commentTextField = new JHintTextField("Comment");
		commentTextField.setPreferredSize(new Dimension(200, 24));
		formJPanel.add(commentTextField, BorderLayout.EAST);
		//
		JButton submitJButton = new JButton("Submit");
		submitJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jFrame.dispose();
				agent.sendRating(project.getProviderAID(), ratingTextField.getText());
			}
		});
		clientJPanel.add(submitJButton, BorderLayout.SOUTH);
		//
		return clientJPanel;
	}

	/**
	 * Update project information.
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
