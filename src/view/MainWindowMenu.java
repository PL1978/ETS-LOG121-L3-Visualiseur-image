/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: 
 Bradley How
 Philippe Lepage

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


import controler.CommandExecuter;
import model.Memento;
import model.MementoCaretaker;


/**
 * This is the GUI for the menu at the top.
 * It had 4 sections : File, Edit, View, Help
 * File have the commands : Load Image, open perspective, save and exit
 * Edit have the commands : Zoom in, zoom out and translate
 * View have the commands to switch the possibles views like thumbnail, perspective view
 * Help have the commands to show the possible shortcut and the about.
 * 
 * @author Bradley How
 * @author Philippe Lepage
 */
public class MainWindowMenu extends JMenuBar {


	private static final long serialVersionUID = 1L;
	private static final String MENU_FILE_TITLE = "File";
	private static final String MENU_FILE_LOAD_IMAGE = "Open image";
	private static final String MENU_MANAGE_MEMENTO = "Manage saves";
	private static final String MENU_FILE_SAVE = "Save";
	private static final String MENU_FILE_EXIT = "Close program";
	private static final String MENU_EDIT_TITLE = "Edit";
	private static final String MENU_EDIT_ZOOM = "Zoom";
	private static final String MENU_EDIT_TRANSLATE = "Translate";
	private static final String MENU_HELP_TITLE = "Help";
	private static final String MENU_ABOUT_TITLE = "About";
	private static final String MENU_VIEW_TITLE = "View";
	private static final String MENU_ADD_VIEW = "Add View";
	private static final String MENU_CLOSE_VIEW = "Close View";
	private static final String MENU_OPEN_THUMBNAIL = "Open thumbnail";
	private JMenuItem menuAddView, menuCloseView, menuLoadImage, menuManageMemento, menuSave, menuExit, menuZoom, menuTranslation, menuAbout, menuOpenThumbnail;
	
	private MainWindow mainWindow;
	private int menuId;

	public MainWindowMenu(MainWindow mainWindow) {

		menuId = MainWindow.getIdCounter();
		this.mainWindow = mainWindow;
		initComposants();
		
	}
	
	public MainWindowMenu(MainWindow mainWindow, int id) {

		menuId = id;
		this.mainWindow = mainWindow;
		initComposants();
		
	}
	
	public void initComposants() {
		
		
		addMenuFile();
		addMenuEdit();
		addMenuView();
		addMenuHelp();
		addMenuItemListener();
		
	}

	private void addMenuItemListener() {
		menuAddView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {		
				mainWindow.addViewAndPerspective();
			}
			
		});
		
		menuCloseView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {		
				
				JFrame windowToClose = mainWindow.getViewDialogMap().get(menuId);
				windowToClose.dispatchEvent(new WindowEvent(windowToClose, WindowEvent.WINDOW_CLOSING));
			}
			
		});
		
		menuManageMemento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			managePerspectiveAction();
			}

			
		});
		
		menuSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (CommandExecuter.getInstance().getModel().getImage() != null) {
				new AddSaveDialog();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "There is no perspective to save");
				}
			}
			
		});
	
		menuLoadImage.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						HashMap<Integer, JFrame> viewList = mainWindow.getViewDialogMap();
						
						try {
						CommandExecuter.getInstance().doLoadImage(openFileChoser());				
						
						closeAllWindowsBut(menuId);
						
						
						} catch (IllegalArgumentException iae) {}
						
					}
				});
	
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	
		menuZoom.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new ZoomDialog(menuId);
					
					}
				});
	
		menuTranslation.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new TranslateDialog(menuId);
					}
				});
		
		menuOpenThumbnail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.openThumbnail();	
			}
			
		});
	}
	
	private void addMenuView() {
		JMenu menuView = new JMenu(MENU_VIEW_TITLE);
		
		this.menuAddView = new JMenuItem(MENU_ADD_VIEW);
		menuView.add(menuAddView);
		
		
		
		this. menuCloseView = new JMenuItem(MENU_CLOSE_VIEW);
		menuView.add(menuCloseView);
		
		this.menuOpenThumbnail = new JMenuItem(MENU_OPEN_THUMBNAIL);
		menuView.add(menuOpenThumbnail);
		
		add(menuView);
	}
	/**
	 * To add the file menu 
	 */
	public void addMenuFile() {
		JMenu menuFile = new JMenu(MENU_FILE_TITLE);

		this. menuLoadImage = new JMenuItem(MENU_FILE_LOAD_IMAGE);
		this. menuManageMemento = new JMenuItem(MENU_MANAGE_MEMENTO);
		this. menuSave = new JMenuItem(MENU_FILE_SAVE);
		this. menuExit = new JMenuItem(MENU_FILE_EXIT);
		
		menuFile.add(menuLoadImage);
		menuFile.add(menuManageMemento);
		menuFile.add(menuSave);
		menuFile.add(menuExit);

		add(menuFile);
	}
	/**
	 * To add the edit menu
	 */
	public void addMenuEdit() {
		JMenu menuEdit = new JMenu(MENU_EDIT_TITLE);
		
		this.menuZoom = new JMenuItem(MENU_EDIT_ZOOM);
		this. menuTranslation = new JMenuItem(MENU_EDIT_TRANSLATE);
		
		menuEdit.add(menuZoom);
		menuEdit.add(menuTranslation);
		
		add(menuEdit);
		
	}
	/**
	 * To add the help Menu
	 * 
	 */
	public void addMenuHelp() {
		JMenu menuHelp = new JMenu(MENU_HELP_TITLE);
		this.menuAbout = new JMenuItem(MENU_ABOUT_TITLE);
		menuHelp.add(menuAbout);
		menuAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"<html><p>Image viewer.</p>" + "<br>"
								+ "<p>&copy; &nbsp; 2020 &nbsp; How Chen Nian, Bradley Henry</p>"
								+ "<p>&copy; &nbsp; 2020 &nbsp; Lepage, Philippe</p>"
								+ "<p>&copy; &nbsp; 2020 &nbsp; Somma, Gabriel</p>"
								+ "<p>&copy; &nbsp; 2020 &nbsp; Rimoux, Quentin</p>" + "<br>"
								+ "<p>&Eacute;cole de technologie sup&eacute;rieure</p></html>");
			}
		});
		add(menuHelp);
	}
	
	private File openFileChoser() {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Select image to load");
		fileChooser.setAcceptAllFileFilterUsed(false);
		// Filter
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file", ImageIO.getReaderFileSuffixes());
		fileChooser.addChoosableFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(null);
		File selectedFile = null;
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			
		
		}
	return selectedFile;
	}
	private void managePerspectiveAction() {
		LinkedList<Memento> mementoObjectList = new LinkedList<Memento>();
		LinkedList<String> mementoName = new LinkedList<String>();	
		MementoCaretaker cMed = CommandExecuter.getInstance().getModel().getCaretaker();
		
		try {
				int i =0;
				while (true) {
					mementoObjectList.add(cMed.getSave(i));
					mementoName.add(mementoObjectList.get(i).getName());
					i++;
				}
			} catch (IllegalArgumentException iae) {
				
			}
			
			if (!mementoName.isEmpty()) {
			SavedPerspectiveView loadMementoDialog = new SavedPerspectiveView(mementoName);
			
				
				
			loadMementoDialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {	
					List<String> toDelete = loadMementoDialog.getToDelete();
					if (!toDelete.isEmpty()) {
						manageMementoToDelete(toDelete, mementoObjectList);
					}
					String toLoad = loadMementoDialog.getToLoad();
					if (toLoad !=null) {
						manageMementoToLoad(toLoad, mementoObjectList);
					}
				}
			});
			} else {
				JOptionPane.showMessageDialog(new JFrame(),"No Perspective saved");
			}
				
			}
	private void closeAllWindowsBut(int exception) {
		HashMap<Integer, JFrame> viewList = mainWindow.getViewDialogMap();		
		
		//Copy the key set to avoid ConcurrentModificationException
		Set<Integer> setCopy = new HashSet<Integer>(viewList.keySet());
		
		if (exception>=0) setCopy.remove(exception);
		
		
		Iterator<Integer> keyIter = setCopy.iterator();
		JFrame window;
		
		while (keyIter.hasNext()) { 
		window = viewList.get(keyIter.next());		
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}	
			
	
	}
	private void manageMementoToDelete(List<String> toDelete, List<Memento> mementos) {
		//Avoid exception by deleting and iterating
		List<Memento> clone = new LinkedList<Memento>(mementos);
		Iterator<Memento> mIter = clone.iterator();
		
		Memento actual;
		while (mIter.hasNext()) {
			actual =mIter.next();
			if (toDelete.contains(actual.getName())) {
				CommandExecuter.getInstance().doDeleteSave(actual);
			}
		}
		CommandExecuter.getInstance().doSerialize();
	}
	
	private void manageMementoToLoad(String toLoad, List<Memento> mementos) {
		Iterator<Memento> mIter = mementos.iterator();
		
		Memento actual=null;
		while (mIter.hasNext()) {
			
			actual =mIter.next();
			if (actual.getName().equals(toLoad)) {
				System.out.println(actual.getName());
				closeAllWindowsBut(-1);
				CommandExecuter.getInstance().doLoadSaved(actual);
				
				
				Set<Integer> key = CommandExecuter.getInstance().getModel().getPerspectiveMap().keySet();
				
				for (int clef : key) {
					mainWindow.addView(clef);
				}
	
				break;
			}
		}			
	}
}
