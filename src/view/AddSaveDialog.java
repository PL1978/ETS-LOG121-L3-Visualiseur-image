/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: AddSaveDialog.java
Date crée: 2020-03-29
Date dern. modif. 2020-04-02
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.NamingException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.CommandExecuter;
import model.MementoCaretaker;

/**
 * Dialog that asks for the name of the a save and verify if the name is unique. If so, launches the doSave Command.
 * Once the save command is launched, the serialize command is called.
 * 
 * @author Philippe Lepage
 * @see SaveCommand
 * @see SerializeCommand
 */
public class AddSaveDialog extends JDialog {

	private static final String ENTRY_TEXT = "Enter save name";
	private static final String NAMING_EXCEPTION_MESSAGE = "Every save must have a unique name";
	private JButton okButton, cancelButton;
	private JTextField entry;

	public final static int HEIGHT = 100;

	public final static int WIDTH = HEIGHT * 4;

	/**
	 * Instanciate the add save Dialog
	 */
	public AddSaveDialog() {
		super();
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		entry = new JTextField(3);

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MementoCaretaker cMed;
				String name=null;
				try {
					cMed = CommandExecuter.getInstance().getModel().getCaretaker();
					name = entry.getText();
					
					// Memento must have a name
					if (name.length() ==0 || name == null) throw new NamingException();
					
					int i = 0;
					
					// Checks if the name is unique
					while (true) {
						
						if (cMed.getSave(i).getName().equals(name)) throw new NamingException();
						i++;
					
					}
						
				} catch (IllegalArgumentException iae) {
					
					//If the end of the list is reached before a exact same name is found, the name is unique
					//and the save can be launched
					CommandExecuter.getInstance().doSave(name);
					CommandExecuter.getInstance().doSerialize();
					setVisible(false);
					
				} catch (NamingException e1) {
					JOptionPane.showMessageDialog(new JFrame(), NAMING_EXCEPTION_MESSAGE);
				}

			}

		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				entry.setText("");
				setVisible(false);
			}

		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
		entryPanel.add(new JLabel(ENTRY_TEXT));
		entryPanel.add(entry);

		Container container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(entryPanel);
		container.add(buttonPanel);

		setSize(WIDTH, HEIGHT);
		this.setVisible(true);
	}
}
