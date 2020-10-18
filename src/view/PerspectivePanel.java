/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: 
 Bradley How
 Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: PerspectivePanel.java
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
import java.awt.Point;
import java.awt.event.ActionEvent;

import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;

import javax.swing.JPanel;

import javax.swing.KeyStroke;

import javax.swing.event.MouseInputAdapter;

import controler.CommandExecuter;
import model.ModelMediator;
import observerpattern.Observer;

/**
 * JPanel that prints an image through a perspective. Observes the
 * ModelMediator.
 *
 * @author Philippe Lepage
 * @author Bradley How
 *
 * @see Observer
 * @see CommandExecuter
 */
public class PerspectivePanel extends JPanel implements Observer {

	private static final String ZOOM_OUT_SHORTCUT = "control O";
	private static final String ZOOM_IN_SHORTCUT = "control I";
	private static final String REDO_SHORTCUT = "control shift Z";
	private static final String UNDO_SHORTCUT = "control Z";
	private static final long serialVersionUID = 1L;
	private Dimension panelDimension;
	private int viewId;

	public static final String zoomInMapObject = "ZOOMIN";
	public static final String zoomOutMapObject = "ZOOMOUT";
	public static final String undoMapObject = "UNDO";
	public static final String redoMapObject = "REDO";

	/**
	 * Instantiate form and uses MainWindow id. Calls a method that creates a new
	 * Perspective and subscribes this panel as Observer.
	 */
	public PerspectivePanel() {

		CommandExecuter.getInstance().doAddPerspective(MainWindow.getIdCounter(), this);

		initComposant(MainWindow.getIdCounter());

	}

	/**
	 * Instantiate form and uses given id. Calls a method that subscribes this panel
	 * as Observer.
	 */
	public PerspectivePanel(int id) {

		CommandExecuter.getInstance().doAttachObserver(id, this);

		initComposant(id);

	}

	/**
	 * Initiate components of this panel and calls methods that adds various action
	 * listener.
	 */
	private void initComposant(int id) {
		this.viewId = id;
		ModelMediator med = CommandExecuter.getInstance().getModel();
		panelDimension = new Dimension((int) (MainWindow.DIMENSION.getWidth() / 2),
				(int) (MainWindow.DIMENSION.height * 8 / 10));
		setPreferredSize(panelDimension);

		addTrasnlationListener();
		addKeyboardListener();
	}

	/**
	 *Adds keyBinding to launch command using shortcut 
	 *Shortcuts are listed as class constants.
	 */
	private void addKeyboardListener() {
		// UNDO keyListener
		this.getInputMap().put(KeyStroke.getKeyStroke(UNDO_SHORTCUT), undoMapObject);
		this.getActionMap().put(undoMapObject, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandExecuter.getInstance().doUndo(viewId);

			}

		});

		// REDO keyListener
		this.getInputMap().put(KeyStroke.getKeyStroke(REDO_SHORTCUT), redoMapObject);
		this.getActionMap().put(redoMapObject, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandExecuter.getInstance().doRedo(viewId);

			}

		});

		// ZOOMIN keyListener
		this.getInputMap().put(KeyStroke.getKeyStroke(ZOOM_IN_SHORTCUT), zoomInMapObject);
		this.getActionMap().put(zoomInMapObject, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandExecuter.getInstance().doZoom(viewId, 110, false);
				;
			}

		});

		// ZOOMOUT keyListener
		this.getInputMap().put(KeyStroke.getKeyStroke(ZOOM_OUT_SHORTCUT), zoomOutMapObject);
		this.getActionMap().put(zoomOutMapObject, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				CommandExecuter.getInstance().doZoom(viewId, 90, false);
				;
			}

		});

	}

	/**
	 *Adds actionListener the the mouse on this panel. 
	 *Translation will be launched if the user clicks the panel and drags it.
	 */
	private void addTrasnlationListener() {

		MouseInputAdapter clickDragListener = new MouseInputAdapter() {
			private Point origin;

			@Override
			public void mousePressed(MouseEvent me) {
				this.origin = me.getPoint();

			}

			@Override
			public void mouseReleased(MouseEvent me) {
				this.origin = null;
				CommandExecuter.getInstance().packTranslation(viewId);

			}

			@Override
			public void mouseDragged(MouseEvent me) {
				Point coordonates = me.getPoint();

				CommandExecuter.getInstance().doTranslation(viewId, coordonates.x - origin.x, coordonates.y - origin.y);

				this.origin = me.getPoint();

			}

		};

		this.addMouseListener(clickDragListener);
		this.addMouseMotionListener(clickDragListener);

	}
	@Override
	public void update() {
		repaint();
	}

	/**
	 *Paints the area designated by the associated perspective 
	 */
	public void paint(Graphics g) {
		super.paint(g);
		ModelMediator med = CommandExecuter.getInstance().getModel();

		Image img = med.getImage();
		Dimension dimension;
		Point coordinate;

		if (img != null) {
			dimension = med.getPerspectiveDimension(viewId);
			coordinate = med.getPerspectivePosition(viewId);
			g.drawImage(img, coordinate.x, coordinate.y, dimension.width, dimension.height, null);
		}

	}

	public int getViewId() {
		return viewId;
	}

}
