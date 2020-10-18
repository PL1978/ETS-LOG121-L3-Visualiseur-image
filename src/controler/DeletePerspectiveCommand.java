/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: DeletePerspectiveCommand.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package controler;

import java.util.LinkedList;

import model.ModelMediator;
import observerpattern.Observer;

/**
 *Command that deletes the perspective and the observer associated to the given viewId
 *
 *@author Philippe Lepage
 *@see Command
 *@see ModelMediator
 */
public class DeletePerspectiveCommand implements Command{

	private ModelMediator model;
	private int viewId;
	
	
    /**
     * Instantiate class form.
     * Sets the parameter model as attributes.
     *
     * @param model : the ModelMediator on which a perspective and observer will be deleted.
     * @param viewId : identification of Perspective and Observer that will be deleted.
     */
	protected DeletePerspectiveCommand(ModelMediator model, int viewId) {

		this.model = model;
		this.viewId = viewId;

	}



	@Override
	public void execute() {
		this.model.deleteObserver(this.viewId);
		this.model.removePerspective(this.viewId);
		cleanCommandList(CommandExecuter.getInstance().getCommandHistory(), this.viewId);
		cleanCommandList(CommandExecuter.getInstance().getCommandUndone(), this.viewId);
		
	}

	/**
	 * Removes the command with the given viewId from the given list. 
	 * 
	 * @param commandList : the on which the commands will be removed.
	 * @param viewId : Commands with this viewId will be removed.
	 */
	private void cleanCommandList(LinkedList<Command> commandList, int viewId) {
		Undoable command;
		for (int i =0; i<commandList.size();i++) {
		command = (Undoable)commandList.get(i);	
			if (command.getViewId()==viewId) {
				commandList.remove(command);
			}
		}
	}
}
