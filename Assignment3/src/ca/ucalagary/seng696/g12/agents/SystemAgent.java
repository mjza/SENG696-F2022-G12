package ca.ucalagary.seng696.g12.agents;

import jade.core.behaviours.OneShotBehaviour;

import java.util.ArrayList;
import java.util.Collections;
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
    
    /** The providers. */
    private static List<Provider> providers = new ArrayList<>();
    
    /** The clients. */
    private static List<Client> clients = new ArrayList<>();
    
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
		return providers;
	}
	
	/**
	 * Gets a list of clients.
	 *
	 * @return clients
	 */
	public static List<Client> getClients() {
		return clients;
	}

	/**
	 * Gets the provider.
	 *
	 * @param name the name
	 * @return the provider
	 */
	public static Provider getProvider(String username) {
        return DBUtils.getProvider(username);
    }

    /**
     * Gets the client.
     *
     * @param name the name
     * @return the client
     */
    public static Client getClient(String name) {
        for (Client client : clients) {
            if (client.getUsername().equals(name)) {
                return client;
            }
        }
        return null;
    }

    /**
     * Search provider.
     *
     * @param text the text
     * @param providersList the providers list
     * @return the list
     */
    public static List<Provider> searchProvider(String text, List<Provider> providersList) {
        List<Provider> searchedProviders = new ArrayList<>();
        for (Provider provider : providersList) {
            if (provider.getUsername().contains(text) || provider.getKeywords().contains(text) || provider.isPremium) {
                searchedProviders.add(provider);
            }
        }
        Collections.sort(searchedProviders, (provider1, provider2) -> {
            boolean b1 = provider1.isPremium;
            boolean b2 = provider2.isPremium;
            return (b1 != b2) ? (b1) ? -1 : 1 : 0;
        });
        return searchedProviders;
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
     * Login.
     *
     * @param userName the user name
     * @param password the password
     * @param type the type
     */
    public void login(String userName, String password) {
    	String type = DBUtils.getUserType(userName, password);
        if ("C".equalsIgnoreCase(type)) {            
           createAgent("Client:" + userName, "ca.ucalagary.seng696.g12.agents.ClientAgent");            
        } else if ("P".equalsIgnoreCase(type)){            
           createAgent("Provider:" + userName, "ca.ucalagary.seng696.g12.agents.ProviderAgent");            
        } else {
        	mainGUI.showWrongCredential();
        }
    }

    /**
     * Setup.
     */
    @Override
    protected void setup() {
    	
    	System.out.println("System Agent " + getAID().getName() + " is ready.");
    	
        addBehaviour(new OneShotBehaviour() {
            /**
			 * The serial version must be increased by each update
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void action() {
                System.out.println("UserManagers-agent " + getAID().getName() + "is ready.");

                mainGUI = new MainGUI(SystemAgent.this);
                mainGUI.showGUI();
                
                Collections.sort(providers, (provider1, provider2) -> {
                    boolean b1 = provider1.isPremium;
                    boolean b2 = provider2.isPremium;
                    return (b1 != b2) ? (b1) ? -1 : 1 : 0;
                });

            }
        });
    }

    /**
     * Take down.
     */
    @Override
    protected void takeDown() {
        mainGUI.dispose();
        System.out.println("UserManagers-agent " + getAID().getName() + "is terminating.");
    }    
}
