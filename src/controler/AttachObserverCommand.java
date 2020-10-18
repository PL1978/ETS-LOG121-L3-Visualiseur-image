/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: AttachObserverCommand.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package controler;

import model.ModelMediator;
import observerpattern.*;

/**
 *Command that adds the given observer to the given ModelMediator.
 *
 *@author Philippe Lepage
 *@see Command
 *@see Observer
 *@see ModelMediator
 */
public class AttachObserverCommand implements Command{

	
	private ModelMediator model;
	private int keyId;
	private Observer o;
	
    /**
     * Instantiate class form.
     * Sets the parameters as attributes
     *
     * @param model : the ModelMediator on which a the observer will be added.
     * @param keyId : Identification of the view that calls this command. Used to map the Observer.
     * @param o :  Observer that will be added to the ModelMediator.
     */
	protected AttachObserverCommand(ModelMediator model, int keyId, Observer o) {

		this.model = model;
		this.keyId = keyId;
		this.o = o;
	}

	@Override
	public void execute() {
		this.model.attachObserver(keyId, o);
		
	}
}
