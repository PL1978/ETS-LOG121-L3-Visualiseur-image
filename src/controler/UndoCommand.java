/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: UndoCommand.java
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
 * Command that undo the last executed command. Do so by executing the opposite
 * value the given command.
 *
 * @author Philippe Lepage
 * @see Command
 * @see ComandExecuter
 */
public class UndoCommand implements Command {
	private int viewId;

	/**
	 * Instantiate Command form.
	 *
	 * @param viewId : viewId of the view that calls this command. Matches the
	 *               viewId command that needs to be re-executed.
	 */
	protected UndoCommand(int viewId) {
		this.viewId = viewId;
	}

	@Override
	public void execute() {
		LinkedList<Command> history = CommandExecuter.getInstance().getCommandHistory();
		Undoable command = null;

		// Starting at the end of the list, finds the first command that matches the
		// viewId.
		for (int i = history.size() - 1; i >= 0; i--) {
			command = (Undoable) history.get(i);
			if (command.getViewId() == this.viewId) {

				// Removes the command from the history list
				CommandExecuter.getInstance().getCommandHistory().remove(command);
				// Undoes the command
				command.undo();
				break;
			}
		}

	}

}
