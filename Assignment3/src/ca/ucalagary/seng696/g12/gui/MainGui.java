package ca.ucalagary.seng696.g12.gui;

import javax.swing.*;

import ca.ucalagary.seng696.g12.agents.SystemAgent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui {

    SystemAgent userManagersAgent;
    JFrame jFrame;

    public MainGui(SystemAgent userManagerAgent) {
        this.jFrame = new JFrame();

        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                userManagerAgent.killAgent(userManagerAgent.getLocalName());
            }
        });

        this.userManagersAgent = userManagerAgent;
        this.jFrame.setSize(600, 300);
        JPanel jPanel = new JPanel();
        BoxLayout layout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(layout);


//        HintTextField textField_userName = new HintTextField("UserName");
//        HintTextField textField_password = new HintTextField("Password");

//        String[] type = {User.CLIENT, User.PROVIDER};
//        JComboBox comboBox_userType = new JComboBox(type);

        JButton button_login = new JButton("Login");
        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserGui usergui = new UserGui(userManagerAgent);
                usergui.showGui();
            }
        });

        JButton button_register = new JButton("Register");
        
        button_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationGui registrationGui = new RegistrationGui(userManagerAgent);
                registrationGui.showGui();
            }
        });

        JButton button_guest = new JButton("Guest");
        button_guest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuestGui guestGui = new GuestGui(SystemAgent.getProviders());
                guestGui.showGui();
            }
        });
        JLabel label = new JLabel("Welcome to Match Making System");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_register.setAlignmentX(Component.CENTER_ALIGNMENT);
        button_guest.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel.add(Box.createRigidArea(new Dimension(10,50)));
        jPanel.add(label);
        jPanel.add(Box.createRigidArea(new Dimension(10,20)));
        jPanel.add(button_login);
        jPanel.add(Box.createRigidArea(new Dimension(10,10)));
        jPanel.add(button_register);
        jPanel.add(Box.createRigidArea(new Dimension(10,10)));
        jPanel.add(button_guest);

        Container contentPane = jFrame.getContentPane();
        contentPane.add(jPanel,BorderLayout.CENTER);
//        jFrame.add(jPanel);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public void showGui() {

        this.jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }

    public void showWrongCredential() {
        JOptionPane.showMessageDialog(jFrame, "Wrong Credential", "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        MainGui userGui = new MainGui(null);
        userGui.showGui();
    }
}
