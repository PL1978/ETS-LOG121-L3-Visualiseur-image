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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import controler.CommandExecuter;
import model.Memento;
import model.MementoCaretaker;

/**
 * This is the GUI for the menu at the top. It had 4 sections : File, Edit,
 * View, Help File have the commands : Load Image, open perspective, save and
 * exit Edit have the commands : Zoom in, zoom out and translate View have the
 * commands to switch the possibles views like thumbnail, perspective view Help
 * have the commands to show the possible shortcut and the about.
 * 
 * THIS CLASS WAS TAKEN AND ADDAPTED FROM https://bit.ly/34lZoLK.
 * 
 * @author Bradley How
 * @author Philippe Lepage
 */
public class WindowMenu extends JMenuBar {

	private static final String NO_SAVES_WARINING = "No Perspective saved";
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
	private static final String MENU_UNDO = "Undo";
	private static final String MENU_REDO = "Redo";
	private JMenuItem menuAddView, menuCloseView, menuLoadImage, menuManageMemento, menuSave, menuExit, menuZoom,
			menuTranslation, menuAbout, menuUndo, menuRedo;

	private MainWindow mainWindow;
	private int menuId;

	/**
	 *Instantiate form and takes id from MainWindow.
	 */
	public WindowMenu(MainWindow mainWindow) {

		menuId = MainWindow.getIdCounter();
		this.mainWindow = mainWindow;
		initComposants();

	}

	/**
	 *Instantiate form and uses given id.
	 */
	public WindowMenu(MainWindow mainWindow, int id) {

		menuId = id;
		this.mainWindow = mainWindow;
		initComposants();

	}

	/**
	 * calls the methods that initiates the menus, add menu items and add actionListener
	 */
	public void initComposants() {

		addMenuFile();
		addMenuEdit();
		addMenuView();
		addMenuHelp();
		addMenuItemListener();

	}

	/**
	 * adds the action listener on each of the item in the menu 
	 */
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

				JFrame windowToClose = mainWindow.getPerspectiveMap().get(menuId);
				windowToClose.dispatchEvent(new WindowEvent(windowToClose, WindowEvent.WINDOW_CLOSING));
			}

		});

		menuManageMemento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manageSavesAction();
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

		menuLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				HashMap<Integer, JFrame> viewList = mainWindow.getPerspectiveMap();

				try {
					CommandExecuter.getInstance().doLoadImage(openFileChoser());

					closeAllWindowsBut(menuId);

				} catch (IllegalArgumentException iae) {
				}

			}
		});

		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		menuZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ZoomDialog(menuId);

			}
		});

		menuTranslation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TranslateDialog(menuId);
			}
		});

		menuUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandExecuter.getInstance().doUndo(menuId);
			}

		});
		menuRedo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandExecuter.getInstance().doRedo(menuId);
			}

		});
	}

	/**
	 * Creates the view menu and adds it's items
	 * View menu is used to add and close views
	 */
	private void addMenuView() {
		JMenu menuView = new JMenu(MENU_VIEW_TITLE);

		this.menuAddView = new JMenuItem(MENU_ADD_VIEW);
		menuView.add(menuAddView);

		this.menuCloseView = new JMenuItem(MENU_CLOSE_VIEW);
		menuView.add(menuCloseView);

		add(menuView);
	}

	/**
	 * Creates the file menu and adds it's items
	 * File menu is used to load images, manage saves, add a save and exit the program
	 */
	public void addMenuFile() {
		JMenu menuFile = new JMenu(MENU_FILE_TITLE);

		this.menuLoadImage = new JMenuItem(MENU_FILE_LOAD_IMAGE);
		this.menuManageMemento = new JMenuItem(MENU_MANAGE_MEMENTO);
		this.menuSave = new JMenuItem(MENU_FILE_SAVE);
		this.menuExit = new JMenuItem(MENU_FILE_EXIT);

		menuFile.add(menuLoadImage);
		menuFile.add(menuManageMemento);
		menuFile.add(menuSave);
		menuFile.add(menuExit);

		add(menuFile);
	}

	/**
	 * Creates the menu view and adds it's items
	 * Edit menu is used to zoom ,translate, undo and redo.
	 */
	public void addMenuEdit() {
		JMenu menuEdit = new JMenu(MENU_EDIT_TITLE);

		this.menuZoom = new JMenuItem(MENU_EDIT_ZOOM);
		this.menuTranslation = new JMenuItem(MENU_EDIT_TRANSLATE);
		this.menuUndo = new JMenuItem(MENU_UNDO);
		this.menuRedo = new JMenuItem(MENU_REDO);
		
		
		menuEdit.add(menuZoom);
		menuEdit.add(menuTranslation);
		menuEdit.add(menuUndo);
		menuEdit.add(menuRedo);

		add(menuEdit);

	}

	/**
	 * Creates the menu help and the one item
	 * Help menu gives information about the developpers of this program.
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

	/**
	 *Opens a JFileChooser to select an image that will be loaded.
	 *
	 * @return the file selected by the user.
	 */
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

	/**
	 * Series of action done when the Manage Saves item is pressed.
	 */
	private void manageSavesAction() {
		LinkedList<Memento> mementoObjectList = new LinkedList<Memento>();
		LinkedList<String> mementoName = new LinkedList<String>();
		MementoCaretaker cMed = CommandExecuter.getInstance().getModel().getCaretaker();

		try {
			int i = 0;
			//Adds mementos and memento names to a list until all the saves are reached
			while (true) {
				mementoObjectList.add(cMed.getSave(i));
				mementoName.add(mementoObjectList.get(i).getName());
				i++;
			}
		} catch (IllegalArgumentException iae) {
			//All the saves are reached when IllegalArgument is thrown by MementoCaretaker
		}

		
		if (!mementoName.isEmpty()) {
			
			SavedPerspectiveFrame loadMementoDialog = new SavedPerspectiveFrame(mementoName);

			//Get the selection of the user when the window is closing
			loadMementoDialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					List<String> toDelete = loadMementoDialog.getToDelete();
					if (!toDelete.isEmpty()) {
						manageMementoToDelete(toDelete, mementoObjectList);
					}
					String toLoad = loadMementoDialog.getToLoad();
					if (toLoad != null) {
						manageMementoToLoad(toLoad, mementoObjectList);
					}
				}
			});
		} else {
			JOptionPane.showMessageDialog(new JFrame(), NO_SAVES_WARINING);
		}

	}

	/**
	 *Method that closes all the views except for the one given as parameter.
	 *This method does not affect the thumbnail.
	 *
	 * @param exception : id of the view that will remain open
	 */
	private void closeAllWindowsBut(int exception) {
		HashMap<Integer, JFrame> viewList = mainWindow.getPerspectiveMap();

		// Copy the key set to avoid ConcurrentModificationException
		Set<Integer> setCopy = new HashSet<Integer>(viewList.keySet());

		if (exception >= 0)
			setCopy.remove(exception);

		Iterator<Integer> keyIter = setCopy.iterator();
		JFrame window;

		while (keyIter.hasNext()) {
			window = viewList.get(keyIter.next());
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}

	}

	/**
	 * Method that deletes the saves selected by the user by calling the deleteSave method.
	 * 
	 * @param toDelete : List of the name of the mementos to delete
	 * @param mementos : Complete list of the mementos. Used to identify the memento to delete.
	 */
	private void manageMementoToDelete(List<String> toDelete, List<Memento> mementos) {
		// Avoid exception by deleting and iterating
		List<Memento> clone = new LinkedList<Memento>(mementos);
		Iterator<Memento> mIter = clone.iterator();

		Memento actual;
		while (mIter.hasNext()) {
			actual = mIter.next();
			//Delete a memento if it's name is in the list of mementos to delete
			if (toDelete.contains(actual.getName())) {
				CommandExecuter.getInstance().doDeleteSave(actual);
			}
		}
		CommandExecuter.getInstance().doSerialize();
	}

	/**
	 *Method that loads the memento selected by the user by calling the loadSaved method.
	 * 
	 *@param toLoad : name of the memento to load
	 *@param mementos : Complete list of the mementos. Used to identify the memento to load.
	 */
	private void manageMementoToLoad(String toLoad, List<Memento> mementos) {
		Iterator<Memento> mIter = mementos.iterator();

		Memento actual = null;
		while (mIter.hasNext()) {

			actual = mIter.next();
			
			if (actual.getName().equals(toLoad)) {
				//When the memento is found all the windows needs to be closed
				//closing the windows will delete the associated perspective
				closeAllWindowsBut(-1);
				
				//The command will create the saved perspective
				CommandExecuter.getInstance().doLoadSaved(actual);

				Set<Integer> key = CommandExecuter.getInstance().getModel().getPerspectiveMap().keySet();

				//Opens new windows with ids matching the loaded perspective's ids
				for (int clef : key) {
					mainWindow.addView(clef);
				}

				break;
			}
		}
	}
}
