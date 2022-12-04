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
package ca.ucalagary.seng696.g12.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class EnhancedAgent.
 */
public class EnhancedAgent extends Agent {
	/**
	 * The serial version must be increased by each update.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Search for service.
	 *
	 * @param serviceName the service name
	 * @return the sets the
	 */
	protected Set<AID> searchForService(String serviceName) {
		System.out.println("Enhanced agent launched");
		Set<AID> foundAgents = new HashSet<>();
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceName.toLowerCase());
		dfd.addServices(sd);

		SearchConstraints sc = new SearchConstraints();
		sc.setMaxResults(Long.valueOf(-1));

		try {
			DFAgentDescription[] results = DFService.search(this, dfd, sc);
			for (DFAgentDescription result : results) {
				foundAgents.add(result.getName());
			}
			return foundAgents;
		} catch (FIPAException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Take down.
	 */
	protected void takeDown() {
		System.out.println("Taking down" + getLocalName());
		try {
			DFService.deregister(this);
		} catch (Exception ex) {
		}
	}

	/**
	 * Register.
	 *
	 * @param serviceName the service name
	 */
	protected void register(String serviceName) {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getLocalName());
		sd.setType(serviceName.toLowerCase());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Creates the agent.
	 *
	 * @param name      the name
	 * @param className the class name
	 */
	protected void createAgent(String name, String className) {
		System.out.println("creating agent " + name + " " + className);
		AID agentID = new AID(name, AID.ISLOCALNAME);
		AgentContainer controller = getContainerController();
		try {
			AgentController agent = controller.createNewAgent(name, className, null);
			agent.start();
			System.out.println("Agent created: " + agentID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kill agent.
	 *
	 * @param name the name
	 */
	public void killAgent(String name) {
		AID agentID = new AID(name, AID.ISLOCALNAME);
		AgentContainer controller = getContainerController();
		try {
			AgentController agent = controller.getAgent(name);
			agent.kill();
			System.out.println("Agent killed: " + agentID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}