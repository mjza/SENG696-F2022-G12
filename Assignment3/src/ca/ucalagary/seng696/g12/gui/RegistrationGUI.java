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
import javax.swing.text.NumberFormatter;

import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.databases.DBUtils;
import ca.ucalagary.seng696.g12.dictionary.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * The Class RegistrationGUI.
 */
public class RegistrationGUI {

	/** The j frame. */
	JFrame jFrame;

	JPanel registerJPanel;

	String type = User.PROVIDER;

	/**
	 * Instantiates a new registration GUI.
	 */
	public RegistrationGUI() {
		// The main frame
		this.jFrame = new JFrame("B2B Match Making System: Register a new user");
		// set icon
		this.jFrame.setIconImage(SystemAgent.getIcon());
		// Set size of the frame to full screen
		this.jFrame.setSize(1024, 768);
		this.jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Set the panel inside the frame
		this.jFrame.getContentPane().add(this.getRegisterJPanel(), BorderLayout.NORTH);
	}

	/**
	 * Gets the register J panel.
	 *
	 * @return the register J panel
	 */
	private JPanel getRegisterJPanel() {
		if (this.registerJPanel != null)
			return this.registerJPanel;
		JPanel registerJPanel = new JPanel();
		this.registerJPanel = registerJPanel;
		registerJPanel.setLayout(new GridBagLayout());
		// A container for ordering items in rows
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;// set the x location of the grid for the next component
		gbc.gridy = 0;// set the y location of the grid for the next component
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 20, 20, 10); // padding
		gbc.weighty = 1.0; // increase vertical space

		// Type lable
		JLabel typeLabel = new JLabel("Type:");
		typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(typeLabel, gbc);
		// Type box
		JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JRadioButton jRadioButton1 = new JRadioButton("I am a provider");
		jRadioButton1.setBounds(120, 30, 120, 50);
		jRadioButton1.setSelected(true);
		JRadioButton jRadioButton2 = new JRadioButton("I am a client");
		jRadioButton2.setBounds(250, 30, 80, 50);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(jRadioButton1);
		buttonGroup.add(jRadioButton2);
		typePanel.add(jRadioButton1);
		typePanel.add(jRadioButton2);
		gbc.gridx++;// set the x location of the grid
		gbc.weightx = 0; // to fill whole width
		registerJPanel.add(typePanel, gbc);

		// Name lable
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(nameLabel, gbc);
		// Name textbox
		JTextField nameTextField = new JTextField();
		nameLabel.setLabelFor(nameTextField);
		gbc.gridx++;// set the x location of the grid
		gbc.weightx = 1.0; // to fill whole width
		registerJPanel.add(nameTextField, gbc);

		// Username lable
		JLabel usenameLabel = new JLabel("Email:");
		usenameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(usenameLabel, gbc);
		// Username textbox
		JTextField userNameTextField = new JTextField();
		usenameLabel.setLabelFor(userNameTextField);
		gbc.gridx = 1;// set the x location of the grid
		gbc.weightx = 1.0; // to fill whole width
		registerJPanel.add(userNameTextField, gbc);

		// Password label
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(passwordLabel, gbc);
		// Password textbox
		JTextField passwordTextField = new JTextField();
		passwordLabel.setLabelFor(passwordTextField);
		gbc.gridx = 1;// set the x location of the grid
		gbc.weightx = 1; // to fill whole width
		registerJPanel.add(passwordTextField, gbc);

		// Keywords label
		JLabel keywordsLabel = new JLabel("Keywords:");
		keywordsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(keywordsLabel, gbc);
		// Keywords textbox
		JTextField keywordsTextField = new JTextField();
		keywordsLabel.setLabelFor(keywordsTextField);
		gbc.gridx = 1;// set the x location of the grid
		gbc.weightx = 1; // to fill whole width
		registerJPanel.add(keywordsTextField, gbc);

		// Website label
		JLabel websiteJLable = new JLabel("Website:");
		websiteJLable.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(websiteJLable, gbc);
		// Website textbox
		JTextField websiteTextField = new JTextField();
		websiteJLable.setLabelFor(websiteTextField);
		gbc.gridx = 1;// set the x location of the grid
		gbc.weightx = 1; // to fill whole width
		registerJPanel.add(websiteTextField, gbc);

		// Compensation label
		JLabel compensationJLable = new JLabel("Compensation:");
		compensationJLable.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(compensationJLable, gbc);
		// Compensation textbox
		// restrict to digits
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		JFormattedTextField compensationTextField = new JFormattedTextField(formatter);
		compensationJLable.setLabelFor(compensationTextField);
		gbc.gridx = 1;// set the x location of the grid
		gbc.weightx = 1; // to fill whole width
		registerJPanel.add(compensationTextField, gbc);

		// resume label
		JLabel resumeLabel = new JLabel("Resume/CV:");
		resumeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(resumeLabel, gbc);
		// resume textArea
		JTextArea resumeTextArea = new JTextArea("Type your resume here ... ");
		resumeTextArea.setRows(17);
		resumeLabel.setLabelFor(resumeTextArea);
		gbc.gridx = 1;// set the x location of the grid
		gbc.weightx = 1; // to fill whole width
		registerJPanel.add(resumeTextArea, gbc);

		// Name lable
		JLabel premiumLabel = new JLabel("I am premium:");
		premiumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 0; // to not fill whole width
		registerJPanel.add(premiumLabel, gbc);
		// Name textbox
		JCheckBox premiumCheckBox = new JCheckBox();
		premiumLabel.setLabelFor(premiumCheckBox);
		gbc.gridx++;// set the x location of the grid
		gbc.weightx = 1.0; // to fill whole width
		registerJPanel.add(premiumCheckBox, gbc);

		// Submit button
		JButton submitButton = new JButton("Submit");
		gbc.gridx = 1;// set the x location of the grid
		gbc.gridy++;// change the y location
		gbc.weightx = 1; // to not fill whole width
		registerJPanel.add(submitButton, gbc);

		submitButton.addActionListener(new ActionListener() {
			/**
			 * Action performed.
			 *
			 * @param e the e
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText().trim();
				String userName = userNameTextField.getText().trim();
				String password = passwordTextField.getText().trim();
				String keywords = keywordsTextField.getText().trim();
				String website = websiteTextField.getText().trim();
				String compensation = compensationTextField.getText().trim().replaceAll("[^0-9]", "");
				String resume = resumeTextArea.getText().trim();
				Boolean isPremium = premiumCheckBox.isSelected();
				if (User.CLIENT.equals(type)) {
					if (name != null && userName != null && password != null && name.length() > 0
							&& userName.length() > 0 && password.length() > 0) {
						RegistrationGUI.this.register(type, name, userName, password, null, null, null, null, null);
					} else {
						showMissingData();
					}
				} else {
					if (name != null && userName != null && password != null && keywords != null && website != null
							&& compensation != null && name.length() > 0 && userName.length() > 0
							&& password.length() > 0 && keywords.length() > 0 && compensation.length() > 0
							&& website.length() > 0) {
						RegistrationGUI.this.register(type, name, userName, password, keywords, resume, isPremium,
								website, compensation);
					} else {
						showMissingData();
					}
				}

			}
		});

		// Adding Listener to JButtons.
		jRadioButton1.addActionListener(new ActionListener() {
			// Anonymous class.
			public void actionPerformed(ActionEvent e) {
				if (jRadioButton1.isSelected()) {
					type = User.PROVIDER;
					keywordsTextField.setEnabled(true);
					websiteTextField.setEnabled(true);
					compensationTextField.setEnabled(true);
					resumeTextArea.setEnabled(true);
					premiumCheckBox.setEnabled(true);
				}
				System.out.println("Type: " + type);
			}
		});
		jRadioButton2.addActionListener(new ActionListener() {
			// Anonymous class.
			public void actionPerformed(ActionEvent e) {
				if (jRadioButton2.isSelected()) {
					type = User.CLIENT;
					keywordsTextField.setEnabled(false);
					websiteTextField.setEnabled(false);
					compensationTextField.setEnabled(false);
					resumeTextArea.setEnabled(false);
					premiumCheckBox.setEnabled(false);
				}
				System.out.println("Type: " + type);
			}
		});

		return registerJPanel;
	}

	/**
	 * Register.
	 *
	 * @param type     the type
	 * @param name     the name
	 * @param userName the user name
	 * @param password the password
	 * @param keywords the keywords
	 * @param resume   the resume
	 */
	public void register(String type, String name, String userName, String password, String keywords, String resume,
			Boolean isPremium, String website, String compensation) {
		if ("P".equals(type)) {
			// add a provider
			DBUtils.addProvider(name, userName, password, 0);
			int id = DBUtils.getUserId(userName);
			int iCompensation = Integer.parseInt(compensation);
			DBUtils.addResume(id, keywords, resume, isPremium, website, iCompensation);
		} else {
			// add a client
			DBUtils.addClient(name, userName, password, 0);
		}
		this.hideGUI();
	}

	/**
	 * Show missing data.
	 */
	public void showMissingData() {
		JOptionPane.showMessageDialog(jFrame, "Please fill all active textboxes.", "Ops: Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show GUI.
	 */
	public void showGUI() {
		this.jFrame.setVisible(true);
	}

	public void hideGUI() {
		this.jFrame.setVisible(false);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		this.jFrame.dispose();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		RegistrationGUI registrationGUI = new RegistrationGUI();
		registrationGUI.showGUI();
	}
}
