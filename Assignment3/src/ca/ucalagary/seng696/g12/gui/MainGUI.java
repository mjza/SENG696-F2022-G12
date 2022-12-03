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

import ca.ucalagary.seng696.g12.agents.SystemAgent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class MainGUI.
 */
public class MainGUI {

    /** The user managers agent. */
    SystemAgent systemAgent;
    
    /** The j frame. */
    JFrame jFrame;
    
    /** The login J panel. */
    JPanel loginJPanel;

    /**
     * Instantiates a new main GUI.
     *
     * @param systemAgent the system agent
     */
    public MainGUI(SystemAgent systemAgent) {
    	// Initiate the agent
        this.systemAgent = systemAgent;
    	// Initiate the jFrame
        this.jFrame = new JFrame("B2B Match Making System");
        // Set size of the frame to full screen
        this.jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);        
        this.jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Kill the agent on close 
        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                if(systemAgent != null)
                	systemAgent.killAgent(systemAgent.getLocalName());
            }
        });
        jFrame.getContentPane().add(this.getLoginJPanel(),BorderLayout.CENTER);        
    }

    /**
     * Show GUI.
     */
    public void showGUI() {
    	// Set the location to center 
        this.jFrame.setLocationRelativeTo(null);
    	// Show the jFrame
        this.jFrame.setVisible(true);
    }

    /**
     * Dispose.
     */
    public void dispose() {
        this.jFrame.dispose();
    }
    
    private JPanel getLoginJPanel() {
    	if(this.loginJPanel != null)
    		return this.loginJPanel;
    	JPanel loginJPanel = new JPanel();
    	this.loginJPanel = loginJPanel;
        loginJPanel.setLayout(new GridBagLayout());        
        // A container for ordering items in rows 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;//set the x location of the grid for the next component
        gbc.gridy = 0;//set the y location of the grid for the next component
        // Set header 
        JLabel title = new JLabel("Login to B2B MMS");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginJPanel.add(title, gbc);
        // Username lable
        JLabel usenameLabel = new JLabel("Username:");
        usenameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usenameLabel.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 1;//change the y location      
        loginJPanel.add(usenameLabel, gbc);        
        // Username textbox
        JTextField userNameTextField = new JTextField();
        userNameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameTextField.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 2;//change the y location
        loginJPanel.add(userNameTextField, gbc);
        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 3;//change the y location      
        loginJPanel.add(passwordLabel, gbc);
        // Password textbox        
        JPasswordField passwordTextField = new JPasswordField();        
        passwordTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordTextField.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 4;//change the y location
        loginJPanel.add(passwordTextField, gbc);
        // Login button 
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		String userName = userNameTextField.getText().trim();
        		String password = new String(passwordTextField.getPassword()).trim();
                if(userName != null && password != null && userName.length() > 0 && password.length()>0) {
                	if(systemAgent != null)
                		systemAgent.login(userName, password);
                }
                else {
                	showEmptyCredential();
                }
            }
        });
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 5;//change the y location
        loginJPanel.add(loginBtn, gbc);
        // Register button
        JButton registerBtn = new JButton("Register");        
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationGUI registrationGUI = new RegistrationGUI(systemAgent);
                registrationGUI.showGUI();
            }
        });
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setPreferredSize(new Dimension(200, 24));
        //jPanel.add(Box.createRigidArea(new Dimension(10,10)));
        gbc.gridy = 6;//change the y location
        loginJPanel.add(registerBtn, gbc);
        // Guest login 
        JButton guestBtn = new JButton("Visit as a guest");
        guestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuestGUI guestGUI = new GuestGUI(SystemAgent.getProviders());
                guestGUI.showGUI();
            }
        });        
        guestBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        guestBtn.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 7;//change the y location
        loginJPanel.add(guestBtn, gbc);        
        return loginJPanel;
    } 

    /**
     * Show wrong credential.
     */
    public void showWrongCredential() {
        JOptionPane.showMessageDialog(jFrame, "Please check your username and password.", "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Show empty credential.
     */
    public void showEmptyCredential() {
        JOptionPane.showMessageDialog(jFrame, "Please fill the username and passowrd.", "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        MainGUI userGUI = new MainGUI(null);
        userGUI.showGUI();
    }
}
