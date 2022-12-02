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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuestGUI {

    JFrame jFrame;
    DefaultListModel<String> providersList;

    List<Provider> currentProviders = new ArrayList<>();

    public GuestGUI(List<Provider> providers) {
        System.out.println("number of providers: ");
        System.out.println(providers.size());

        jFrame = new JFrame("Welcome Guest user");
        jFrame.setSize(600, 600);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setSize(600, 600);

        providersList = new DefaultListModel<>();
        for (Provider provider : providers) {
            currentProviders.add(provider);
            String text = provider.getInfo();
            providersList.addElement(text);
        }
        JList<String> list = new JList<>(providersList);

        list.setCellRenderer(new ProviderListField(currentProviders));

        JPanel providerPanel = new JPanel();
        providerPanel.setLayout(new BorderLayout());
        providerPanel.add(new JLabel("Providers:"),BorderLayout.NORTH);
        providerPanel.add(list,BorderLayout.CENTER);
        leftPanel.add(providerPanel, BorderLayout.CENTER);

        HintTextField searchTextField = new HintTextField("Search Provider");
        searchTextField.setSize(new Dimension(200, 24));
        leftPanel.add(searchTextField, BorderLayout.NORTH);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            public void removeUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            public void insertUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            private void searchProviders(DocumentEvent e) {
                String searchedText = searchTextField.getText();
                if (searchedText.isEmpty()) {
                    providersList.removeAllElements();
                    for (Provider provider : currentProviders) {
                        providersList.addElement(provider.getInfo());
                    }
                } else {
                    providersList.removeAllElements();
                    List<Provider> searchedProviders = SystemAgent.searchProvider(searchedText);
                    for (Provider provider : searchedProviders) {
                        providersList.addElement(provider.getInfo());
                    }
                }
            }
        });


        jPanel.add(leftPanel, BorderLayout.CENTER);

        
        jFrame.add(jPanel);
    }

    public void showGUI() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }
}
