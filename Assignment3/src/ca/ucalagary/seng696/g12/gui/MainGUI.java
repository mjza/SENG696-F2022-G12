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

    /**
     * Instantiates a new main GUI.
     *
     * @param systemAgent the system agent
     */
    public MainGUI(SystemAgent systemAgent) {
    	// Initiate the agent
        this.systemAgent = systemAgent;
    	// Initiate the jFrame
        this.jFrame = new JFrame();
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
        // Set the main panel 
        JPanel jPanel = new JPanel();
        //BoxLayout layout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        //jPanel.setLayout(layout);
        jPanel.setLayout(new GridBagLayout());
        jFrame.getContentPane().add(jPanel,BorderLayout.CENTER);
        // A container for ordering items in rows 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;//set the x location of the grid for the next component
        gbc.gridy = 0;//set the y location of the grid for the next component
        // Set header 
        JLabel title = new JLabel("B2B Match Making System");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel.add(title, gbc);
        // Username lable
        JLabel usenameLabel = new JLabel("Username:");
        usenameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usenameLabel.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 1;//change the y location      
        jPanel.add(usenameLabel, gbc);        
        // Username textbox
        HintTextField userNameTextField = new HintTextField("Email");
        userNameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameTextField.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 2;//change the y location
        jPanel.add(userNameTextField, gbc);
        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 3;//change the y location      
        jPanel.add(passwordLabel, gbc);
        // Password textbox        
        JPasswordField passwordTextField = new JPasswordField();        
        passwordTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordTextField.setPreferredSize(new Dimension(200, 24));
        gbc.gridy = 4;//change the y location
        jPanel.add(passwordTextField, gbc);
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
        jPanel.add(loginBtn, gbc);
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
        jPanel.add(registerBtn, gbc);
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
        jPanel.add(guestBtn, gbc);        
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

    /**
     * Show wrong credential.
     */
    public void showWrongCredential() {
        JOptionPane.showMessageDialog(jFrame, "Wrong Credential", "ERROR",
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
