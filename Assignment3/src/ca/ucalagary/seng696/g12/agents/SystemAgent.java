package ca.ucalagary.seng696.g12.agents;

import jade.core.behaviours.OneShotBehaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.ucalagary.seng696.g12.dictionary.Customer;
import ca.ucalagary.seng696.g12.dictionary.Provider;
import ca.ucalagary.seng696.g12.dictionary.User;
import ca.ucalagary.seng696.g12.gui.MainGui;

public class SystemAgent extends EnhancedAgent {

    /**
	 * The serial version must be increased by each update.  
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Section for class attributes
	 */
	private MainGui mainGui;
    private static List<Provider> providers = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    
    /**
     * The constructor initializes the system.
     * Reads list of providers and customers from DB. 
     * Loads their data to the system. 
     */
    public SystemAgent() {
        addMockUsers();
    }
    
    /**
     * Gets a list of providers. 
     * @return providers
     */
	public static List<Provider> getProviders() {
		return providers;
	}
	
	/**
	 * Gets a list of customers
	 * @return customers
	 */
	public static List<Customer> getCustomers() {
		return customers;
	}


	public static Provider getProvider(String name) {
        for (Provider provider : providers) {
            if (provider.getUsername().equals(name)) {
                return provider;
            }
        }
        return null;
    }

    public static Customer getCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    public static List<Provider> searchProvider(String text, List<Provider> providersList) {
        List<Provider> searchedProviders = new ArrayList<>();
        for (Provider provider : providersList) {
            if (provider.getUsername().contains(text) || provider.getSkill().contains(text) || provider.isPremium) {
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

    private void addMockUsers() {
        Provider p = new Provider("P2", "2", "provider", "PHP", 4);
        p.setPremium();
        providers.add(new Provider("P1", "1", "provider", "Java", 5));
        providers.add(p);
        providers.add(new Provider("P3", "3", "provider", "C", 3));

        customers.add(new Customer("C1", "1", "customer", 2));
        customers.add(new Customer("C2", "2", "customer", 6));
        customers.add(new Customer("C3", "3", "customer", 8));
    }

    public void register(String userName, String password, String role, String skill) {
        if (role.equals("provider")) {
            providers.add(new Provider(userName, password, role, skill, 0));
        } else {
            customers.add(new Customer(userName, password, role, 0));
        }
    }

    @Override
    protected void setup() {
    	
    	System.out.println("Hello! System Agent " + getAID().getName() + " is ready.");
    	
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("UserManagers-agent " + getAID().getName() + "is ready.");

                mainGui = new MainGui(SystemAgent.this);
                mainGui.showGui();

                Collections.sort(providers, (provider1, provider2) -> {
                    boolean b1 = provider1.isPremium;
                    boolean b2 = provider2.isPremium;
                    return (b1 != b2) ? (b1) ? -1 : 1 : 0;
                });

            }
        });
    }

    @Override
    protected void takeDown() {
        mainGui.dispose();
        System.out.println("UserManagers-agent " + getAID().getName() + "is terminating.");
    }

    public void login(String userName, String password, String role) {
        if (role.equals(User.CUSTOMER)) {
            Customer customer = SystemAgent.getCustomer(userName);
            if (customer != null){
                createAgent("Customer:" + customer.getUsername(), "ca.ucalagary.seng696.g12.agents.CustomerAgent");
            }
            else {
                mainGui.showWrongCredential();
            }
        } else {
            Provider provider = SystemAgent.getProvider(userName);
            if (provider != null) {
                createAgent("Provider:" + provider.getUsername(), "ca.ucalagary.seng696.g12.agents.ProviderAgent");
            } else {
                mainGui.showWrongCredential();
            }
        }
    }
}