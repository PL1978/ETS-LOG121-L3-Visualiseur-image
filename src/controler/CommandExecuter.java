/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: CommandExecuter.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/

package controler;

import java.io.File;
import java.util.LinkedList;

import model.Memento;
import model.ModelMediator;
import observerpattern.Observer;

/**
 * This class act as a factory to instantiate commands. It also keeps track of
 * the launched commands in order to allow undo. Implemented as singleton,
 * access the instance through getInstance.
 * 
 * @author Philippe Lepage
 * @since 1.0
 * 
 */
public class CommandExecuter {

	// CommandExecuter Instance
	private static final CommandExecuter INSTANCE = new CommandExecuter();

	// keeps track of the executed commands
	private LinkedList<Command> commandHistory;

	// keeps track of the undone commands
	private LinkedList<Command> undone;

	// the model that will be affected be the launched commands
	private ModelMediator model;

	protected void clearLists() {
		this.undone = new LinkedList<Command>();
		this.commandHistory = new LinkedList<Command>();
	}

	/**
	 * Class constructor set to private because the class instantiates itself.
	 * Instantiates the attributes commandHistory : Stack<Command> and undone :
	 * Stack<Command> as new Stacks
	 */
	private CommandExecuter() {

		this.commandHistory = new LinkedList<Command>();
		this.undone = new LinkedList<Command>();
	}

	/**
	 * Getter of the attribute commandHistory : Stack<Command>
	 *
	 * @return : the attribute commandHistory : Stack<Command>
	 */
	protected LinkedList<Command> getCommandHistory() {
		return this.commandHistory;
	}

	/**
	 * Getter of the attribute undone : Stack<Command>
	 *
	 * @return : the attribute undone : Stack<Command>
	 */
	protected LinkedList<Command> getCommandUndone() {
		return this.undone;
	}

	/**
	 * Setter the attribute commandHistory : Stack<Command> with method's parameter
	 *
	 * @param undone : Stack<Command> instance that will be set as attribute
	 */
	public void setCommandHistory(LinkedList<Command> commandHistory) {
		this.commandHistory = commandHistory;
	}

	/**
	 * Setter the attribute undone : Stack<Command> with method's parameter
	 *
	 * @param undone : Stack<Command> instance that will be set as attribute
	 */
	public void setUndone(LinkedList<Command> undone) {
		this.undone = undone;
	}

	/**
	 * Sets the attribute model : ModelMediator. Attribute needs to be set for the
	 * commands to work.
	 *
	 * @param model : the model : ModelMediator that will be set as attribute
	 */
	public void setModel(ModelMediator model) {
		this.model = model;
	}

	/**
	 * Allows to access the instance of CommandExecuter implemented as singleton.
	 *
	 * @return CommandExecuter instance
	 */
	public static CommandExecuter getInstance() {
		return CommandExecuter.INSTANCE;
	}

	/**
	 * Getter that returns the model : ModelMediator instance. Allows the view
	 * package classes to get information when they are updated.
	 * 
	 * @return the ModelMediator only instance
	 */
	public ModelMediator getModel() {
		return model;
	}

	/**
	 * Takes the multiple translation created by a mouse drag and compresses it into
	 * one. Allows a easier implementation of the undo Command.
	 *
	 * @param viewId : the view identification that calls this method. The Id will
	 *               be used to identify the correct translation.
	 */
	public void packTranslation(int viewId) {
		int xTranslation = 0;
		int yTranslation = 0;
		TranslateCommand command = null;

		while (!commandHistory.isEmpty() && commandHistory.getLast() instanceof TranslateCommand) {

			command = (TranslateCommand) commandHistory.getLast();

			if (command.getPacked() == false && command.getViewId() == viewId) {

				commandHistory.remove(command);
				xTranslation += command.getXtranslation();
				yTranslation += command.getYtranslation();

			} else {

				break;
			}

		}
		commandHistory.addLast(new TranslateCommand(this.model, viewId, xTranslation, yTranslation, true));

	}

	// FACTORY section of the commandExecuter

	/**
	 * Instantiate and execute the L3oadImageCommand.
	 *
	 * @param imageFile : that file that represents the image that will be loaded.
	 */
	public void doLoadImage(File imageFile) {
		new LoadImageCommand(this.model, imageFile).execute();
	}

	/**
	 * Instantiate and execute the TranslationCommand.
	 *
	 * @param x      : X value towards where the view will be moved.
	 * @param y      : Y value towards where the view will be moved.
	 * @param viewId : Identification of the view that calls the command. Used to
	 *               affect the correct perspective.
	 */
	public void doTranslation(int viewId, int x, int y) {
		new TranslateCommand(this.model, viewId, x, y).execute();
	}

	/**
	 * Instantiate and execute the TranslationCommand.
	 *
	 * @param x      : X value towards where the view will be moved.
	 * @param y      : Y value towards where the view will be moved.
	 * @param viewId : Identification of the view that calls the command. Used to
	 *               affect the correct perspective.
	 * @param packed : describe if the current translation is a complete translation
	 *               by itself.
	 */
	public void doPackedTranslation(int viewId, int x, int y, boolean packed) {
		new TranslateCommand(this.model, viewId, x, y, packed).execute();
	}

	/**
	 * Instantiate and execute the TranslationCommand.
	 *
	 * @param x      : X value towards where the view will be moved.
	 * @param y      : Y value towards where the view will be moved.
	 * @param viewId : Identification of the view that calls the command. Used to
	 *               affect the correct perspective.
	 * @param packed : describe if the current translation is a complete translation
	 *               by itself.
	 */
	public void doZoom(int viewId, int zoom, boolean absolute) {
		new ZoomCommand(this.model, viewId, zoom, absolute).execute();
	}

	/**
	 * Instantiate and execute the UndoCommand.
	 *
	 * @param viewId : Identification of the view that calls the command. Used to
	 *               identify the correct command to Undo.
	 */
	public void doUndo(int viewId) {
		new UndoCommand(viewId).execute();
	}

	/**
	 * Instantiate and execute the RedoCommand.
	 *
	 * @param viewId : Identification of the view that calls the command. Used to
	 *               identify the correct command to Redo.
	 */
	public void doRedo(int viewId) {
		new RedoCommand(viewId).execute();
	}

	/**
	 * Instantiate and execute the SerializeCommand. Launch this method when the
	 * program is closing.
	 */
	public void doSerialize() {
		new SerializeCommand(this.model).execute();
		;
	}

	/**
	 * Instantiate and execute the DeserializeCommand. Launch this method when the
	 * program is opening.
	 * 
	 */
	public void doDeserialize() {
		new DeserializeCommand(this.model).execute();
		;
	}

	/**
	 * Instantiate and execute the LoadSavedCommand.
	 * 
	 * @param viewId : Identification of the view that calls the command. The id
	 *               will be used to identify the saved commands.
	 * @param saved  : Selected Memento that will be loaded from the saved memento.
	 */
	public void doLoadSaved(Memento saved) {
		new LoadSavedCommand(this.model, saved).execute();
		;
	}

	/**
	 * Instantiate and execute the SaveCommand.
	 * 
	 * @param name : name given by the user to the save. 
	 */
	public void doSave(String name) {
		new SaveCommand(this.model, name).execute();
		;
	}
	
	/**
	 * Instantiate and execute the AddPerspectiveCommand. 
	 * 
	 * @param viewId : Identification of the view that calls this method. Used to map the perspective to the Observer.
     * @param o :  Observer that will be added to the ModelMediator and then linked to the created perspective. 
     */
	public void doAddPerspective(int viewId, Observer o) {
		new AddPerspectiveCommand(this.model, viewId, o).execute();
	}
	
	/**
	 * Instantiate and execute the DeletePerspectiveCommand.
	 * 
	 * @param viewId : Identification of the perspective and the observer that will be deleted
     */
	public void doDeletePerspective(int viewId) {
		new DeletePerspectiveCommand(this.model, viewId).execute();
	}
	
	/**
	 * Instantiate and execute the AttachObserverCommand.
	 * 
	 * @param keyId : Identification of the view that calls this method. Used to map the perspective to the Observer.
     * @param o :  Observer that will be added to the ModelMediator and then linked to the created perspective. 
     */
	public void doAttachObserver(int viewId, Observer o) {
		new AttachObserverCommand(this.model, viewId, o).execute();
	}
	
	/**
	 * Instantiate and execute the AttachObserverCommand.
	 * 
	 * @param model : the ModelMediator that will be used to access the MementoCaretaker.
     * @param viewId : Memento that will be removed from the MementoCaretaker.
     */
	public void doDeleteSave(Memento toDelete) {
		new DeleteSaveCommand(this.model, toDelete).execute();
	}
}
