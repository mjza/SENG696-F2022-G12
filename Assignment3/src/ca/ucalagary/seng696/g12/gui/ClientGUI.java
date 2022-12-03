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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ClientGUI {

    JFrame jFrame;
    AID selectedProvider = null;
    DefaultListModel<String> providersList;
    List<Project> projects;
    DefaultListModel<String> projectsListModel;
    JLabel creditLabel;
    List<Provider> currentProviders = new ArrayList<>();
    ClientAgent myAgent;

    public ClientGUI(ClientAgent myAgent, Set<AID> providers, List<Project> projects) {
        this.myAgent = myAgent;
        System.out.println("number of providers: ");
        System.out.println(providers.size());

        jFrame = new JFrame("Welcome " + myAgent.getLocalName());
        jFrame.setSize(1000, 600);
        this.projects = projects;

        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                myAgent.killAgent(myAgent.getLocalName());
            }
        });

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setSize(600, 600);

        providersList = new DefaultListModel<>();


        for (AID provider : providers) {
            Provider provider1 = SystemAgent.getProvider(provider.getLocalName().split(":")[1]);
            currentProviders.add(provider1);
            String text = provider1.getInfo();
            providersList.addElement(text);
        }
        JList<String> list = new JList<>(providersList);

        list.setCellRenderer(new ListCellRenderer(currentProviders));

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    System.out.println(list.getSelectedIndex());
                    for (AID provider : providers) {
                        Provider currentProvider = SystemAgent.getProvider(provider.getLocalName().split(":")[1]);
                        if (currentProvider.getInfo().equals(list.getSelectedValue())) {
                            selectedProvider = provider;
                        }
                    }
                }
            }
        });

        JPanel providerPanel = new JPanel();
        providerPanel.setLayout(new BorderLayout());
        providerPanel.add(new JLabel("Providers:"),BorderLayout.NORTH);
        providerPanel.add(list,BorderLayout.CENTER);
        leftPanel.add(providerPanel, BorderLayout.SOUTH);

        HintTextField searchTextField = new HintTextField("Search Provider");
        searchTextField.setSize(new Dimension(200, 24));
        leftPanel.add(searchTextField, BorderLayout.NORTH);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            public void removeUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            public void insertUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            private void searchProviders(DocumentEvent e) {
                String searchedText = searchTextField.getText();
                if (searchedText.isEmpty()) {
                    providersList.removeAllElements();
                    for (Provider provider : currentProviders) {
                        providersList.addElement(provider.getInfo());
                    }
                } else {
                    providersList.removeAllElements();
                    List<Provider> searchedProviders = SystemAgent.searchProvider(searchedText);
                    for (Provider provider : searchedProviders) {
                        if(provider.isPremium){
                        }
                        providersList.addElement(provider.getInfo());
                    }
                }
            }
        });

        projectsListModel = new DefaultListModel<>();

        for (Project project : this.projects) {
            projectsListModel.addElement(project.getName());
        }

        JList<String> projectList = new JList<>(projectsListModel);

        projectList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList<?> list = (JList<?>) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    ProjectDetailGUI projectDetailGUI = new ProjectDetailGUI(myAgent, ClientGUI.this.projects.get(index));
                    projectDetailGUI.showGUI();
                    System.out.println("Clicked: " + index);
                }
            }
        });
        JPanel projectPanel = new JPanel();
        projectPanel.setLayout(new BorderLayout());
        projectPanel.add(new JLabel("Projects"),BorderLayout.NORTH);
        projectPanel.add(projectList, BorderLayout.CENTER);
        leftPanel.add(projectPanel, BorderLayout.CENTER);
        jPanel.add(leftPanel, BorderLayout.WEST);

        JTextArea jTextAreaDescription = new JTextArea("Project Description");
        jTextAreaDescription.setRows(20);
        jTextAreaDescription.setColumns(20);

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        JTextField jTextFieldName = new HintTextField("Name");
        jPanel1.add(jTextFieldName, BorderLayout.NORTH);
        jPanel1.add(jTextAreaDescription, BorderLayout.CENTER);

        jPanel.add(jPanel1, BorderLayout.CENTER);

        HintTextField bid = new HintTextField("BID:");
        bid.setPreferredSize(new Dimension(200, 24));
        JButton jButtonSend = new JButton("Create");

        JPanel jPanelNewMessage = new JPanel();
        jPanelNewMessage.add(bid, BorderLayout.CENTER);
        jPanelNewMessage.add(jButtonSend, BorderLayout.WEST);


        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePanel.setPreferredSize(new Dimension(200, 200));
        jPanelNewMessage.add(datePanel, BorderLayout.SOUTH);

        jPanel.add(jPanelNewMessage, BorderLayout.SOUTH);


        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProvider == null) {
                    return;
                }
                Project project = new Project(jTextFieldName.getText(), jTextAreaDescription.getText(),
                        Integer.parseInt(bid.getText()), selectedProvider, myAgent.getAID(), model.getYear() + "/" + model.getMonth() + "/" + model.getDay());
                System.out.println(jTextFieldName.getText() + "  " + project.toString());
                myAgent.sendProposal(project, selectedProvider);
            }
        });
        

        jPanelNewMessage.add(bid, BorderLayout.CENTER);
        jPanelNewMessage.add(jButtonSend, BorderLayout.SOUTH);

        creditLabel = new JLabel("Your credit: "+ myAgent.getCredit());
        jPanelNewMessage.add(creditLabel, BorderLayout.WEST);

        jPanel.add(jPanelNewMessage, BorderLayout.SOUTH);

        jFrame.add(jPanel);
    }

    public void showGUI() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }

    public void addProject(Project project) {
        this.projects.add(project);
        projectsListModel.addElement(project.getName());
    }

    public void updateCredit() {
        creditLabel.setText("Your credit: " + myAgent.getCredit());
    }
    public void updateProjects(List<Project> projects){
        System.out.println("UPDATING PROJECTS");
        this.projects = projects;
        projectsListModel.clear();
        for (Project project : this.projects) {
            projectsListModel.addElement(project.getName());
        }
    }
}
