package ca.ucalagary.seng696.g12.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import ca.ucalagary.seng696.g12.dictionary.Provider;

/**
 * The Class ProviderListField.
 */
public class ListCellRenderer extends DefaultListCellRenderer {
	/**
	 * The serial version must be increased by each update.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The providers. */
	private List<Provider> providers;
	


	/**
	 * Instantiates a new provider list field.
	 *
	 * @param providers the providers
	 */
	public ListCellRenderer(List<Provider> providers) {
		super();
		this.providers = providers;
	}



	/**
	 * Gets the list cell renderer component.
	 *
	 * @param list the list
	 * @param value the value
	 * @param index the index
	 * @param isSelected the is selected
	 * @param cellHasFocus the cell has focus
	 * @return the list cell renderer component
	 */
	public Component getListCellRendererComponent(JList<?> list,
												  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus)
    {
        JLabel lbl = (JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
		if (providers.get(index).isPremium) {
			lbl.setForeground(Color.BLUE);
		}
        else {
        	lbl.setForeground(Color.BLACK);
        }
        return lbl;
    }
}
