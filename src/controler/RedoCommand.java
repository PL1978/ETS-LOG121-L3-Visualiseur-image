/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: RedoCommand.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package controler;

import java.util.LinkedList;

/**
 * Command that Re-execute the last command that was undone. Uses the viewId
 * attribute in order to re-execute the correct command.
 *
 * @author Philippe Lepage
 * @see Command
 * @see ComandExecuter
 */
public class RedoCommand implements Command {

	private int viewId;

	/**
	 * Instantiate Command form.
	 *
	 * @param viewId : viewId of the view that calls this command. Matches the
	 *               viewId command that needs to be re-executed.
	 */
	protected RedoCommand(int viewId) {
		this.viewId = viewId;
	}

	@Override
	public void execute() {
		LinkedList<Command> undoneList = CommandExecuter.getInstance().getCommandUndone();
		Command command = null;

		// Starting at the end of the list, finds the first command that matches the
		// viewId.
		for (int i = undoneList.size() - 1; i >= 0; i--) {
			command = undoneList.get(i);
			if (((Undoable) command).getViewId() == this.viewId) {

				// Removes the command from the undone list
				CommandExecuter.getInstance().getCommandUndone().remove(command);
				// Executing the command puts it back in the history list
				command.execute();
				break;
			}
		}
	}

}
