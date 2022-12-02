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

import java.util.List;

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
	private static final long serialVersionUID = 1L;
	
	/** Section for class attributes. */
	private MainGUI mainGUI;

    
    /**
     * The constructor initializes the system.
     * Reads list of providers and clients from DB. 
     * Loads their data to the system. 
     */
    public SystemAgent() {
    	DBUtils.initDB();
    }
    
    /**
     * Gets a list of providers. 
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
     * Login.
     *
     * @param userName the user name
     * @param password the password
     * @param type the type
     */
    public void login(String userName, String password) {
    	String type = DBUtils.getUserType(userName, password);
        if (User.CLIENT.equalsIgnoreCase(type)) {            
           this.createAgent("Client:" + userName, "ca.ucalagary.seng696.g12.agents.ClientAgent");            
        } else if (User.PROVIDER.equalsIgnoreCase(type)){            
           this.createAgent("Provider:" + userName, "ca.ucalagary.seng696.g12.agents.ProviderAgent");            
        } else {
        	this.mainGUI.showWrongCredential();
        }
    }

    /**
     * Register.
     *
     * @param userName the user name
     * @param password the password
     * @param type the type
     * @param keywords the keywords
     */
    public void register(String userName, String password, String type, String keywords) {
        if (type.equals("provider")) {
          //  providers.add(new Provider(userName, password, type, keywords, 0));
        } else {
           // clients.add(new Client(userName, password, type, 0));
        }
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

			@Override
            public void action() {
                System.out.println("System Agent: " + getAID().getName() + " is ready.");
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
        System.out.println("System Agent: " + getAID().getName() + " is terminating.");
    }    
}
