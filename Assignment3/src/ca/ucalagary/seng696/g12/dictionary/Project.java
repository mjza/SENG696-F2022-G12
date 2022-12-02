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
package ca.ucalagary.seng696.g12.dictionary;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

import ca.ucalagary.seng696.g12.gui.ProjectDetailGUI;

public class Project {
    private String name;
    private String description;
    private int progress = 0;
    private int bid;
    private ArrayList<String> messagesHistory = new ArrayList<>();
    private AID provider;
    private AID client;
    private String deadline;
    private ProjectDetailGUI projectDetailGUI;
    private boolean done = false;

    public Project(String name, String description, int bid, AID provider, AID client, String deadline) {
        this.name = name;
        this.bid = bid;
        this.description = description;
        this.provider = provider;
        this.client = client;
        this.deadline = deadline;
    }

    public void setDone() {
        done = true;
    }

    public void setName(String newName) {
        this.name = newName;
    }


    public ArrayList<String> getMessagesHistory() {
        return messagesHistory;
    }

    public String getName() {
        if (done) {
            return "" + name + " (done)";
        }
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }


    public String toString() {
        if (done) {
            return "" + name + " (done)";
        }
        return "" + name + ":" + description + ":" + bid + ":" + deadline;
    }

    public String getContract() {
        return "Contract for project: " + toString();
    }

    public String getRejectionMessage(AID sender) {
        return sender.getLocalName() + " has rejected " + toString();
    }


    public boolean isFinal() {
        return progress == 100;
    }

    public int getProgress() {
        return progress;
    }

    public void progress(int progressPercentage) {
        int permittedProgress = 100 - this.progress;
        if (progressPercentage < permittedProgress) {
            progress += progressPercentage;
        }
        if (projectDetailGUI != null) {
            projectDetailGUI.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(), this.getMessagesHistory());
        }
    }

    public void chatUpdate(String chatMessage) {
        messagesHistory.add(chatMessage);
        if (projectDetailGUI != null) {
            projectDetailGUI.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(), this.getMessagesHistory());
        }
    }

    public int getBid() {
        return bid;
    }

    public AID getProvider() {
        return provider;
    }

    public AID getClient() {
        return client;
    }

    public void connectGUI(ProjectDetailGUI projectDetailGUI) {
        this.projectDetailGUI = projectDetailGUI;
    }

    public void disposeGUI() {
        if(this.projectDetailGUI!=null){
            this.projectDetailGUI.disposeGUI();
        }
    }
}

