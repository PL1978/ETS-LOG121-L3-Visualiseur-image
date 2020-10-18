/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: 
 Bradley How
 Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: MainWindow.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/

package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controler.CommandExecuter;
import model.ModelMediator;

/**
 * MainWindow is the GUI of this project.
 * 
 * THIS CLASS WAS TAKEN AND ADDAPTED FROM https://bit.ly/34lZoLK.
 * 
 * @author Philippe Lepage
 * @author Bradley How
 * @since 1.0
 *
 */
public class MainWindow extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private static int idCounter;
	public static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension panelDimension;
	private JPanel thumbnailView;
	private HashMap<Integer, JFrame> perspectiveFrameMap;
	private List<PerspectivePanel> perspectivePanelList;

	
	/**
	 * The start of the GUI with a thread  
	 * @param args
	 */
	public static void main(String[] args) {
		
		CommandExecuter.getInstance().setModel(new ModelMediator());
		CommandExecuter.getInstance().doDeserialize();

		java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new MainWindow();
	            }
	        });
		
	}
	
	public MainWindow() {
		idCounter=0;
		perspectiveFrameMap = new HashMap<Integer, JFrame>();
		perspectivePanelList = new LinkedList<PerspectivePanel>();
		
		
		
		openThumbnail();
		initComponents();
		addViewAndPerspective();
	
		
	}
	
	/**
	 *Adds new view and subscribes it as an observer to a new persecutive.
	 */
	public void addViewAndPerspective() {
		PerspectiveFrame view = new PerspectiveFrame("View " + idCounter, this);
		PerspectivePanel perspective = new PerspectivePanel();
		
		initViewComponents(idCounter, view, perspective);
		
		idCounter++;
	}
	
	/**
	 *Add new view and subscribes it to an existing perspective. The perspective will be the one associated to the given id.
	 *
	 *@param id : id of the perspective on which the new view will be subscribed. 
	 */
	public void addView(int id) {
		PerspectiveFrame view = new PerspectiveFrame("View " + id, this,id);
		PerspectivePanel perspective = new PerspectivePanel(id);
		
		initViewComponents(id , view, perspective);
		
		idCounter = id++;
	}
	
	/**
	 *Initiates the components and the windowListener needed for every new view.
	 *
	 */
	private void initViewComponents(int id, PerspectiveFrame view, PerspectivePanel perspective) {
		this.perspectiveFrameMap.put(id,view);
		this.perspectivePanelList.add(perspective);
		view.add(perspective);
		view.setLocation(this.perspectivePanelList.size()*20,this.perspectivePanelList.size()*20);
		
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {	
				int idToRemove = perspective.getViewId();
				
				CommandExecuter.getInstance().doDeletePerspective(idToRemove);
				perspectivePanelList.remove(perspective);
				perspectiveFrameMap.remove(idToRemove);
			}
		});
	}
	
	/**
	 * Initiate the basic components of the GUI
	 */
	public void initComponents() {		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("LOG121 - Laboratoire 3");
		panelDimension = new Dimension((int)DIMENSION.width/10,(int)DIMENSION.width/10);
		setSize(panelDimension);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setLocation(0, (int)DIMENSION.height*8/10);
		
	}

	/**
	 * Creates a thumbnail and adds it to the GUI 
	 */
	public void openThumbnail() {
		thumbnailView = new ThumbnailView();
		idCounter++;
		
		WindowMenu mainWindowMenu = new WindowMenu(this);
		setJMenuBar(mainWindowMenu);
		add(thumbnailView);
	}
	
	/**
	 *  returns the attribute perspectiveFrameMap
	 */
	public HashMap<Integer, JFrame> getPerspectiveMap() {
		return perspectiveFrameMap;
	}
	
	/**
	 *  
	 */
	public static int getIdCounter() {
		return idCounter;
	}
}
