/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: ZoomDialogue.java
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
import controler.ZoomCommand;
import model.ModelMediator;

/**
 * Dialog that asks for zoom value and launches the zoom command
 * 
 *@author Philippe Lepage
 *@see ZoomCommand
 */
public class ZoomDialog extends JDialog{

	private JButton okButton, cancelButton;
	private JTextField entry;
	private int menuId;

	
	public final static int HEIGHT = 100;

	public final static int WIDTH = HEIGHT*2;
	
	/**
	 *Instantiate class form. Sets given menuId as attribute. Calls methods that
	 * initiate Components and actionListener.
	 *
	 * @param menuId : identification used to identify the Command that will be launched
	 */
	public ZoomDialog(int menuId) {
		super();
		this.menuId= menuId;
		initComponents();
		initActionListener();
		

	}
private void initActionListener() {
	okButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int zoom=0;
			try {
				zoom = Integer.parseInt(entry.getText());
				setVisible(false);
				
				if (zoom <= 0) throw new NumberFormatException();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(new JFrame(),"Zoom must be an integer greather than 0");
			}
			
			if (zoom > 0) CommandExecuter.getInstance().doZoom(menuId, zoom, true);
			
		}
		
	});
	
	cancelButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {	
			entry.setText("");	
			setVisible(false);
		}
		
	});
}
private void initComponents() {
	okButton = new JButton("OK");
	cancelButton = new JButton("Cancel");
	entry = new JTextField(3);

	
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	buttonPanel.add(cancelButton);
	buttonPanel.add(okButton);
	
	JPanel entryPanel = new JPanel();
	entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
	entryPanel.add(new JLabel("Enter zoom value"));
	entryPanel.add(entry);

	Container container = getContentPane();
	container.setLayout(new BoxLayout (container, BoxLayout.Y_AXIS));
	container.add(entryPanel);
	container.add(buttonPanel);
	
	setSize(WIDTH, HEIGHT);
	this.setVisible(true);
}
}
