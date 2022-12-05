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

import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.ControllerException;

import java.awt.Image;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;

import ca.ucalagary.seng696.g12.databases.DBUtils;
import ca.ucalagary.seng696.g12.dictionary.Client;
import ca.ucalagary.seng696.g12.dictionary.Provider;
import ca.ucalagary.seng696.g12.dictionary.User;
import ca.ucalagary.seng696.g12.gui.MainGUI;

/**
 * The Class SystemAgent.
 */
public class SystemAgent extends EnhancedAgent {

	/**
	 * The serial version must be increased by each update.
	 */
	private static final long serialVersionUID = 2L;

	/** Section for class attributes. */
	private MainGUI mainGUI;

	/**
	 * The constructor initializes the system. Reads list of providers and clients
	 * from DB. Loads their data to the system.
	 */
	public SystemAgent() {
		DBUtils.initDB();
	}

	/**
	 * Gets a list of providers.
	 * 
	 * @return providers
	 */
	public static List<Provider> getProviders() {
		return DBUtils.getProviders(null);
	}

	/**
	 * Gets the provider.
	 *
	 * @param userName the userName
	 * @return the provider
	 */
	public static Provider getProvider(String userName) {
		return DBUtils.getProvider(userName);
	}

	/**
	 * Gets the client.
	 *
	 * @param userName the userName
	 * @return the client
	 */
	public static Client getClient(String userName) {
		return DBUtils.getClient(userName);
	}

	/**
	 * Search provider.
	 *
	 * @param keyword the keyword
	 * @return the list
	 */
	public static List<Provider> searchProvider(String keyword) {
		List<Provider> providers = DBUtils.getProviders(keyword);
		return providers;
	}

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public static Image getIcon() {
		URL resource = SystemAgent.class.getResource("/ca/ucalagary/seng696/g12/images/icon.png");
		ImageIcon img = new ImageIcon(resource);
		return img.getImage();
	}

	/**
	 * Login.
	 *
	 * @param userName the user name
	 * @param password the password
	 * @throws ControllerException 
	 */
	public void login(String userName, String password) {
		String type = DBUtils.getUserType(userName, password);
		String agentName; 
		Class<?> agentClass;
		if (User.CLIENT.equalsIgnoreCase(type)) {
			agentClass = ClientAgent.class;
			agentName = this.generateAgentName(userName, agentClass);			
		} else if (User.PROVIDER.equalsIgnoreCase(type)) {
			agentClass = ProviderAgent.class;
			agentName = this.generateAgentName(userName, agentClass);			
		} else {
			this.mainGUI.showWrongCredential();
			return;
		}
		// After forming the agent name
		if(this.agentExist(agentName)) {
			this.mainGUI.showAgentAlreadyExist();
			return;
		}
		this.createAgent(agentName, agentClass);
	}

	/**
	 * Setup.
	 */
	@Override
	protected void setup() {
		addBehaviour(new OneShotBehaviour() {
			/**
			 * The serial version must be increased by each update
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Action.
			 */
			@Override
			public void action() {
				System.out.println("SystemAgent: " + getAID().getName() + " is ready.");
				// Show the main frame
				mainGUI = new MainGUI(SystemAgent.this);
				mainGUI.showGUI();
			}
		});
	}

	/**
	 * Take down.
	 */
	@Override
	protected void takeDown() {
		this.mainGUI.dispose();
		super.takeDown();
	}
}
