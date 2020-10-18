/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: DeleteSaveCommand.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package controler;

import model.Memento;
import model.ModelMediator;

/**
 * Command that removes the given Memento from the given ModelMediator's MementoCaretaker.
 * 
 * @author Philippe Lepage
 * @see Command
 * @see ModelMediator
 * @see Memento
 */
public class DeleteSaveCommand implements Command {

	private ModelMediator model;
	private Memento toDelete;

    /**
     * Instantiate class form.
     * Sets the parameter model as attributes.
     *
     * @param model : the ModelMediator that will be used to access the MementoCaretaker.
     * @param viewId : Memento that will be removed from the MementoCaretaker.
     */
	protected DeleteSaveCommand (ModelMediator model, Memento toDelete) {
		this.model = model;
		this.toDelete = toDelete;
	}
	
	@Override
	public void execute() {
		this.model.getCaretaker().removeSave(toDelete);
		
	}

}
