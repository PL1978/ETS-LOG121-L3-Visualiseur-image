
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: 
 Bradley How
 Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: SavedPerspectivePanel.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * JPanel that contains the list of mementos to either load or delete. The panel
 * owns the mother JFrame close it when load or confirm delete is pressed
 *
 * CLASS TAKEN AND ADDAPTED FROM : https://bit.ly/3aPR8WU
 * 
 * @author Philippe Lepage
 * @see SavedPerspectiveFrame
 * @see ListSelectionListener
 */
public class SavedPerspectivePanel extends JPanel implements ListSelectionListener {

	private JList list;
	private DefaultListModel<String> listModel;

	private JButton loadButton, deleteButton, confirmDeleteButton, cancelDeleteButton;

	public final static String LOAD_TEXT = "Load";
	public final static String DELETE_TEXT = "Index delete";
	public final static String COMFIRM_DELETE_TEXT = "Confirm delete";
	public final static String CANCEL_DELETE_TEXT = "Cancel delete";

	private String toLoad;
	private List<String> toDelete;

	private JFrame motherWindow;

	/**
	 * Instantiate the list and adds items form the given String list.
	 * 
	 * @param mementos              : name of the saved mementos that will be shown
	 *                              in the list.
	 * @param savedPerspectiveFrame : mother window, used to close the Frame load or
	 *                              confirm delete are pressed.
	 */
	public SavedPerspectivePanel(List<String> mementos, JFrame savedPerspectiveFrame) {
		super(new BorderLayout());

		this.motherWindow = savedPerspectiveFrame;
		this.listModel = new DefaultListModel();
		this.toDelete = new LinkedList<String>();
		addItemsToModel(mementos);

		list = new JList(this.listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);

		loadButton = new JButton(LOAD_TEXT);
		loadButton.setActionCommand(LOAD_TEXT);
		loadButton.addActionListener(new LoadListener());

		confirmDeleteButton = new JButton(COMFIRM_DELETE_TEXT);
		confirmDeleteButton.setActionCommand(COMFIRM_DELETE_TEXT);
		confirmDeleteButton.addActionListener(new ConfirmDeleteListener());
		confirmDeleteButton.setEnabled(false);

		cancelDeleteButton = new JButton(CANCEL_DELETE_TEXT);
		cancelDeleteButton.setActionCommand(CANCEL_DELETE_TEXT);
		cancelDeleteButton.addActionListener(new CancelDeleteListener());
		cancelDeleteButton.setEnabled(false);

		deleteButton = new JButton(DELETE_TEXT);
		deleteButton.setActionCommand(DELETE_TEXT);
		deleteButton.addActionListener(new IndexDeleteListener());

		String name = listModel.getElementAt(list.getSelectedIndex()).toString();

		// Create a panel that uses BoxLayout.
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.add(deleteButton);
		addButtonSeperatorToPane(buttonPane);
		buttonPane.add(confirmDeleteButton);
		addButtonSeperatorToPane(buttonPane);
		buttonPane.add(cancelDeleteButton);
		addButtonSeperatorToPane(buttonPane);
		buttonPane.add(loadButton);

		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(listScrollPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.PAGE_END);
		this.setVisible(true);
	}

	/**
	 * Adds a separation used between buttons to a given JPanel
	 *
	 * @param buttonPane : JPanel where the separation will be added.
	 */
	private void addButtonSeperatorToPane(JPanel buttonPane) {
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
		buttonPane.add(Box.createHorizontalStrut(5));
	}

	/**
	 * Adds a the elements of the parameter's list to the List Model.
	 *
	 * @param items : items to add to the list model.
	 */
	private void addItemsToModel(List<String> items) {
		Iterator<String> mementosIter = items.iterator();

		while (mementosIter.hasNext()) {
			this.listModel.addElement(mementosIter.next());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * ActionListener used when the cancel delete button is pressed
	 */
	class CancelDeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// adds the item back to the list
			addItemsToModel(toDelete);
			// if all the items where removed
			loadButton.setEnabled(true);
			deleteButton.setEnabled(true);
			// cancel deletion
			toDelete.clear();
		}

	}

	/**
	 * ActionListener used when the confirm delete button is pressed. Confirm delete
	 * only has to close the mother JFrame since load and delete is triggered on the
	 * window's closing.
	 */
	class ConfirmDeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			motherWindow.dispatchEvent(new WindowEvent(motherWindow, WindowEvent.WINDOW_CLOSING));

		}

	}

	/**
	 * ActionListener used when the index Delete button is pressed
	 */
	class IndexDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			//adds selected item to the toDelete list
			int index = list.getSelectedIndex();
			toDelete.add((String) list.getSelectedValue());
			listModel.remove(index);
			int size = listModel.getSize();

			//disable buttons if the list is empty
			if (size == 0) {
				deleteButton.setEnabled(false);
				loadButton.setEnabled(false);

			} else {
				if (index == listModel.getSize()) {

					index--;
				}

				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);

			}

			//enable buttons if there is mementos to delete
			if (toDelete.size() > 0) {
				confirmDeleteButton.setEnabled(true);
				cancelDeleteButton.setEnabled(true);
			}
		}
	}

	/**
	 * ActionListener used when the index Load button is pressed.
	 * Sets the toLoad attribute and closes the window since load and delete is triggered on the
	 * window's closing.
	 */
	class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			toLoad = (String) list.getSelectedValue();
			motherWindow.dispatchEvent(new WindowEvent(motherWindow, WindowEvent.WINDOW_CLOSING));

		}
	}

	public String getToLoad() {
		return toLoad;
	}

	public List<String> getToDelete() {
		return toDelete;
	}

}
