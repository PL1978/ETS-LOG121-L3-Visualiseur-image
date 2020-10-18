/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: 
 Bradley How
 Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: PerspectiveFrame.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

import controler.CommandExecuter;

/**
 *JFrame that contains the PerspectivePanel and the MainWindowMenu. 
 *It passes down the MainWindow to the WindowMenu.
 *
 *@author Philippe Lepage
 *@see MainWindow
 *@see WindowMenu
 *@see PerspectivePanel
 */
public class PerspectiveFrame extends JFrame {

	private Dimension panelDimension;
	private WindowMenu menu;
	private int viewId;

	/**
	 *Instantiate form and uses MainWindow id.
	 */
	public PerspectiveFrame(String title, MainWindow mainWindow) {
		
		this.viewId = MainWindow.getIdCounter();
		menu = new WindowMenu(mainWindow);
		initComposant(title);
		
	}
	
	/**
	 *Instantiate form and uses given id.
	 */
	public PerspectiveFrame(String title, MainWindow mainWindow, int id) {
		
		this.viewId = id;
		menu = new WindowMenu(mainWindow, id);
		initComposant(title);
		
	}

	/**
	 *Initiates the basic components of the JFrame.
	 */
	private void initComposant(String title) {
		
		setVisible(true);
		setTitle(title);
		panelDimension = new Dimension((int) (MainWindow.DIMENSION.getWidth() / 2),
				(int) (MainWindow.DIMENSION.height * 8 / 10));
		setSize(panelDimension);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setJMenuBar(menu);
	}
	
	public int getViewId() {
		return viewId;
	}

}
