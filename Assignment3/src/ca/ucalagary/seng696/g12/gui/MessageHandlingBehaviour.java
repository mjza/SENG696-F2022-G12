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
//package ase;
//
//import jade.core.Agent;
//import jade.core.behaviours.CyclicBehaviour;
//import jade.lang.acl.ACLMessage;
//import jade.lang.acl.MessageTemplate;
//
//public class MessageHandlingBehaviour extends CyclicBehaviour{
//	Agent myAgent;
//	public MessageHandlingBehaviour(Agent myAgent) {
//		super(myAgent);
//		this.myAgent = myAgent;
//	}
//	public void action() {
//		ACLMessage msg, reply;
//        MessageTemplate template;
//
//        //listening for chat messages
//        template = MessageTemplate.MatchConversationId("CHAT");
//        msg = myAgent.receive(template);
//        if (msg != null){
//            String content = msg.getContent();
//            System.out.println("Received a chat message:" + content);
//            reply = msg.createReply();
//            MessageGUI msgGUI = new MessageGUI(myAgent, reply, msg, false);
//            msgGUI.showGUI();
//        }
//	}
//}
