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

import ca.ucalagary.seng696.g12.agents.SystemAgent;
import ca.ucalagary.seng696.g12.dictionary.Provider;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class GuestGUI.
 */
public class GuestGUI {

	/** The j frame. */
	private JFrame jFrame;

	/** The providers. */
	private List<Provider> providers;

	/** The providers list. */
	private JTable providersJTable;

	/**
	 * Instantiates a new guest GUI.
	 *
	 * @param providers the providers
	 */
	public GuestGUI(List<Provider> providers) {
		// Set the class providers
		this.providers = providers;
		// The main frame
		this.jFrame = new JFrame("B2B Match Making System: Guest user");
		// Set size of the frame to full screen
		this.jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Set the panel inside the frame
		jFrame.getContentPane().add(this.getGuestJPanel(), BorderLayout.CENTER);
	}

	/**
	 * Gets the guest J panel.
	 *
	 * @return the guest J panel
	 */
	private JPanel getGuestJPanel() {
		
		JPanel guestJPanel = new JPanel();
		guestJPanel.setLayout(new BorderLayout());
		
		JPanel providerPanel = new JPanel();
        providerPanel.add(new JLabel("List of Providers:"), BorderLayout.NORTH);
		String[] columnNames = Provider.getColumns();
		TableModel tableModel = new DefaultTableModel(columnNames, 0);
		JTable providersJTable = new JTable(tableModel);
        providersJTable.setFillsViewportHeight(true); 
        this.providersJTable = providersJTable;
        this.updateTableData(null);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(providersJTable);
        //Add the scroll pane to center of guest panel.
      	providerPanel.add(scrollPane, BorderLayout.CENTER); 
		guestJPanel.add(providerPanel, BorderLayout.CENTER);
		
		HintTextField searchTextField = new HintTextField("Search Provider");
		guestJPanel.add(searchTextField, BorderLayout.NORTH);
		searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			/**
			 * Filter providers.
			 *
			 * @param e the e
			 */
			private void filterProviders(DocumentEvent e) {
				String searchedText = searchTextField.getText();
				if (searchedText.isEmpty()) {
					updateTableData(null);
				} else {
					updateTableData(searchedText);
				}
			}
			
			public void changedUpdate(DocumentEvent e) {
				this.filterProviders(e);
			}

			public void removeUpdate(DocumentEvent e) {
				this.filterProviders(e);
			}

			public void insertUpdate(DocumentEvent e) {
				this.filterProviders(e);
			}
			
		});
			return guestJPanel;
	}
	
/**
	 * Update table data.
	 *
	 * @param filter the filter
	 */
		private void updateTableData(String filter) {
		if(this.providersJTable != null) {
			List<Provider> providers = SystemAgent.searchProvider(filter);
			String[] columnNames = Provider.getColumns();
			String[][] stringArray = providers.stream().map(p -> p.toArray()).toArray(String[][]::new);
			DefaultTableModel tableModel = (DefaultTableModel) this.providersJTable.getModel();			
			tableModel.setDataVector(stringArray, columnNames);
			tableModel.fireTableDataChanged();
		}
	}

	/**
	 * Show GUI.
	 */
	public void showGUI() {
		jFrame.setVisible(true);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		this.jFrame.dispose();
	}
}
