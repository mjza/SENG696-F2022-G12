package ca.ucalagary.seng696.g12.gui;

import javax.swing.*;

import ca.ucalagary.seng696.g12.agents.ClientAgent;
import ca.ucalagary.seng696.g12.agents.ProviderAgent;
import ca.ucalagary.seng696.g12.dictionary.Project;
import ca.ucalagary.seng696.g12.settings.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectDetailGUI {

    JFrame jFrame;
    JLabel label = new JLabel();
    JLabel jLabel = new JLabel("The next label");

    ProjectDetailGUI(ClientAgent agent, Project project) {

        project.connectGUI(this);

        jFrame = new JFrame("Welcome " + agent.getLocalName());
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(label, BorderLayout.EAST);
        centerPanel.add(jLabel, BorderLayout.WEST);
        jPanel.add(centerPanel,BorderLayout.CENTER);

        updateRightLabel(project.getName(), project.getDescription(), project.getProgress(), project.getMessagesHistory());


        JTextField jTextFieldMessage = new HintTextField("Message");
        jPanel.add(jTextFieldMessage, BorderLayout.NORTH);

//        jPanel.add(new JButton("Next"),BorderLayout.SOUTH);
        JButton jButtonSend = new JButton("send");
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String messageText = jTextFieldMessage.getText();
                project.chatUpdate(messageText);
                agent.sendMessage(project.getProvider(), messageText, project.getName(), Constants.CHAT);
                updateRightLabel(project.getName(), project.getDescription(), project.getProgress(), project.getMessagesHistory());

            }
        });
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(jButtonSend, BorderLayout.NORTH);
        JButton doneButton = new JButton("Done");
        southPanel.add(doneButton, BorderLayout.SOUTH);
        jPanel.add(southPanel, BorderLayout.SOUTH);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rating = showDialog(project, agent);
                jFrame.dispose();
                String messageText = "Done";
                agent.sendMessage(project.getProvider(), messageText, project.getName(), Constants.DONE);
            }
        });
        this.jFrame.add(jPanel);
    }

    public void updateRightLabel(String name, String description, int progress, ArrayList<String> messageHistory) {
        updateLeftLabel(name, description, progress);
        StringBuilder text = new StringBuilder("<html>");
        for (String s : messageHistory)
            text.append(s).append("<br/>");
        text.append("</html>");
        label.setText(text.toString());
    }

    private void updateLeftLabel(String name, String description, int progress) {
        StringBuilder text = new StringBuilder("<html>");
        text.append("Name: ").append(name).append("<br/>");
        text.append("Description: ").append(description).append("<br/>");
        text.append("Progress: ").append(progress).append("<br/>").append("<br/>").append("<br/>").append("<br/>");
        text.append("</html>");
        jLabel.setText(text.toString());
    }

    private String showDialog(Project project, ClientAgent agent) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(400, 400);
        HintTextField ratingTextField = new HintTextField("Rating from 1 to 5");
        ratingTextField.setPreferredSize(new Dimension(200, 24));

        HintTextField commentTextField = new HintTextField("Comment");
        commentTextField.setPreferredSize(new Dimension(200, 24));

        JButton jButton = new JButton("Done");


        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.out.println("Done" + ratingTextField.getText() + commentTextField.getText());
                agent.markProjectDone(project);
                agent.sendRating(project.getProvider(), ratingTextField.getText());
                
            }
        });

        panel.add(ratingTextField);
        panel.add(commentTextField);
        panel.add(jButton);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return ratingTextField.getText();
    }

    ProjectDetailGUI(ProviderAgent agent, Project project) {

        project.connectGUI(this);

        jFrame = new JFrame("Welcome " + agent.getLocalName());
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(label, BorderLayout.EAST);
        centerPanel.add(jLabel, BorderLayout.WEST);
        jPanel.add(centerPanel,BorderLayout.CENTER);


        updateRightLabel(project.getName(), project.getDescription(), project.getProgress(), project.getMessagesHistory());

        JTextField jTextFieldMessage = new HintTextField("Message");
        jPanel.add(jTextFieldMessage, BorderLayout.NORTH);

//        jPanel.add(new JButton("Next"),BorderLayout.SOUTH);
        JButton jButtonSend = new JButton("send");
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String messageText = jTextFieldMessage.getText();
                project.chatUpdate(messageText);
                agent.sendMessage(project.getClient(), messageText, project.getName(), Constants.PROVIDER_SEND_MESSAGE);
                updateRightLabel(project.getName(), project.getDescription(), project.getProgress(), project.getMessagesHistory());

            }
        });
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(jButtonSend, BorderLayout.NORTH);
        JButton progressButton = new JButton("10% progress");
        southPanel.add(progressButton, BorderLayout.SOUTH);
        jPanel.add(southPanel, BorderLayout.SOUTH);
        progressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageText = "10";
                project.progress(10);
                agent.sendMessage(project.getClient(), messageText, project.getName(), Constants.PROGRESS_);
                updateRightLabel(project.getName(), project.getDescription(), project.getProgress(), project.getMessagesHistory());
            }
        });
        this.jFrame.add(jPanel);
    }


    public void showGUI() {
        jFrame.setVisible(true);
    }

    public void disposeGUI() {
        jFrame.dispose();
    }
}
