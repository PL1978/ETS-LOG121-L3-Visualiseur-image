/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: TranslateDialog.java
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.CommandExecuter;
import controler.TranslateCommand;
import controler.ZoomCommand;
import model.ModelMediator;

/**
 * Dialog that asks for translation values and calls the translate command
 * 
 * @author Philippe Lepage
 * @see CommandExecuter
 */
public class TranslateDialog extends JDialog {

	private static final String CANCEL_BUTTON_TEXT = "Cancel";
	private static final String OK_BUTTON_TEXT = "OK";
	private static final String ENTRY_WARNING = "Translation values must be integers";
	private JButton okButton, cancelButton;
	private JTextField xEntry;
	private JTextField yEntry;
	private int menuId;

	public final static int HEIGHT = 150;

	public final static int WIDTH = (int) (HEIGHT * 1.5);

	/**
	 * Instantiate class form. Sets given menuId as attribute. Calls methods that
	 * initiate Components and actionListener
	 * 
	 * @param menuId : identification used to identify the Command that will be launched
	 */
	public TranslateDialog(int menuId) {
		super();

		this.menuId = menuId;

		initComponents();
		initActionListener();

	}

	/**
	 * Initiate Action Listener on buttons
	 */
	private void initActionListener() {
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer x = null;
				Integer y = null;
				try {
					x = Integer.parseInt(xEntry.getText());
					y = Integer.parseInt(yEntry.getText());
					setVisible(false);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(new JFrame(), ENTRY_WARNING);
				}
				// *-1 because it's counter intuitive that a positive translation will actually
				// move the image to the right
				if (x != null && y != null)
					CommandExecuter.getInstance().doPackedTranslation(menuId, x * -1, y * -1, true);

			}

		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
	}
	/**
	 * Initiate components of the class.
	 */
	private void initComponents() {
		okButton = new JButton(OK_BUTTON_TEXT);
		cancelButton = new JButton(CANCEL_BUTTON_TEXT);
		xEntry = new JTextField(3);
		yEntry = new JTextField(3);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);

		JPanel xEntryPanel = new JPanel();
		xEntryPanel.setLayout(new BoxLayout(xEntryPanel, BoxLayout.X_AXIS));
		xEntryPanel.add(new JLabel("Enter x value"));
		xEntryPanel.add(xEntry);

		JPanel yEntryPanel = new JPanel();
		yEntryPanel.setLayout(new BoxLayout(yEntryPanel, BoxLayout.X_AXIS));
		yEntryPanel.add(new JLabel("Enter y value"));
		yEntryPanel.add(yEntry);

		Container container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(xEntryPanel);
		container.add(yEntryPanel);
		container.add(buttonPanel);

		setSize(WIDTH, HEIGHT);
		this.setVisible(true);
	}
}
