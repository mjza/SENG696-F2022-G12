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
import ca.ucalagary.seng696.g12.dictionary.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationGUI {

    JFrame jFrame;
    SystemAgent systemAgent;

    public RegistrationGUI(SystemAgent systemAgent) {

        this.jFrame = new JFrame();
//        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//                super.windowClosing(windowEvent);
//                systemAgent.killAgent(systemAgent.getLocalName());
//            }
//        });

        this.systemAgent = systemAgent;
        this.jFrame.setSize(600, 600);
        JPanel jpanel = new JPanel();
        GroupLayout layout = new GroupLayout(jpanel);
        jpanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        HintTextField textField_userName = new HintTextField("UserName");
        HintTextField textField_password = new HintTextField("Password");


        String[] type = {User.PROVIDER, User.CLIENT};
        JComboBox comboBox_userType = new JComboBox(type);

        String[] keywordss = {"Java", "PHP", "Python", "Go", "C"};
        JComboBox comboBox_keywordss = new JComboBox(keywordss);

        JButton button_register = new JButton("Register");


        comboBox_userType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox_userType.getSelectedIndex() == 1) {
                    comboBox_keywordss.setVisible(false);
                } else {
                    comboBox_keywordss.setVisible(true);
                }
            }
        });

        textField_userName.setSize(new Dimension(200, 40));
        textField_password.setSize(new Dimension(200, 40));

        JTextArea jTextArea = new JTextArea("Agreement: This is the demo for the ABSE course");
        jTextArea.setRows(30);
        JCheckBox jCheckBox_agree = new JCheckBox("I agree");


        button_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jCheckBox_agree.isSelected()) {
                    JOptionPane.showMessageDialog(jFrame, "Please read the agreement and select I agree", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                systemAgent.register(textField_userName.getText(), textField_password.getText(), comboBox_userType.getSelectedItem().toString(), comboBox_keywordss.getSelectedItem().toString());
                System.out.println("User: " + textField_userName.getText() + " is registered successfully");
                dispose();
            }
        });
//        textField_userName.setAlignmentX(Component.CENTER_ALIGNMENT);
//        textField_password.setAlignmentX(Component.CENTER_ALIGNMENT);
//        comboBox_userType.setAlignmentX(Component.CENTER_ALIGNMENT);
//        comboBox_keywordss.setAlignmentX(Component.CENTER_ALIGNMENT);
//        jTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
//        jCheckBox_agree.setAlignmentX(Component.CENTER_ALIGNMENT);
//        button_register.setAlignmentX(Component.CENTER_ALIGNMENT);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(textField_userName)
                        .addComponent(textField_password)
                        .addComponent(button_register)
                        .addComponent(jTextArea))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(comboBox_userType)
                                        .addComponent(comboBox_keywordss)
                                        .addComponent(jCheckBox_agree))

        ));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(textField_userName)
                        .addComponent(comboBox_userType))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(textField_password)
                                .addComponent(comboBox_keywordss))
                                        .addComponent(jTextArea)
                                        .addComponent(jCheckBox_agree)
                                        .addComponent(button_register)
        ));





       this.jFrame.add(jpanel);
    }

    public void showGUI() {
        this.jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }
}
