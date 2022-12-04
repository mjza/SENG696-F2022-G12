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
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

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
	 * Agent exist.
	 *
	 * @param agentName the agent name
	 * @return true, if successful
	 */
	public boolean agentExist(String agentName) {
		AgentContainer controller = getContainerController();
		try {
			controller.getAgent(agentName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Register a service.
	 *
	 * @param serviceName the service name
	 */
	protected void registerService(String serviceName) {
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
	 * Search for service.
	 *
	 * @param serviceName the service name
	 * @return the sets the
	 */
	protected Set<AID> searchForService(String serviceName) {
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
	 * Creates the agent.
	 *
	 * @param name      the name
	 * @param className the class name
	 * @throws ControllerException
	 */
	protected void createAgent(String name, Class<?> agentClass) {
		String className = agentClass.getCanonicalName();
		System.out.println("Creating the agent " + name + " as " + className);
		AID agentID = new AID(name, AID.ISLOCALNAME);
		AgentContainer controller = getContainerController();
		try {
			AgentController agent = controller.createNewAgent(name, className, null);
			agent.start();
			System.out.println("Agent created: " + agentID);
		} catch (StaleProxyException e) {
			System.err.println("The agent is already exist!");
		}
	}

	/**
	 * Kill agent the current agent.
	 *
	 */
	public void killAgent() {
		System.out.println("Killing the agent " + getLocalName());
		AgentContainer controller = getContainerController();
		try {
			AgentController agent = controller.getAgent(getLocalName());
			agent.kill();
			System.out.println("Agent killed: " + getLocalName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Take down the agent.
	 */
	protected void takeDown() {
		System.out.println("Taking down the agent " + getLocalName());
		try {
			DFService.deregister(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}