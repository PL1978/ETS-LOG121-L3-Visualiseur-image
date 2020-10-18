/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Bradley How

 Professeur : Vincent Lacasse
 Nom du fichier: MainWindowMenu.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/

package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import controler.CommandExecuter;
import model.ModelMediator;
import observerpattern.Observer;

/**
 * This view is an smaller image of the model.  
 * 
 * @author Bradley How
 */

public class ThumbnailView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Dimension panelDimension;
	private int viewId;
	
	public ThumbnailView() {
		Dimension mainPanel = MainWindow.DIMENSION;
		viewId = MainWindow.getIdCounter();
		panelDimension = new Dimension((int)(mainPanel.width/10),(int)(mainPanel.width/10));
		setPreferredSize(panelDimension);
		CommandExecuter.getInstance().doAttachObserver(viewId, this);
	}

	public void update() {
		repaint();
	}
	
	/**
	 * Paint the image in a Cartesian coordinate system.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		ModelMediator med = CommandExecuter.getInstance().getModel();
		Image img = med.getImage();

		if (img != null) {
			g.drawImage(img, 0,  0,
					panelDimension.width, panelDimension.height, null);
		}
	}
}
