/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: LoadSavedCommand.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package controler;


import java.io.IOException;
import java.util.LinkedList;

import model.Memento;
import model.ModelMediator;


/**
 *Command that loads a given memento into the ModelMediator's data.
 *Do so by calling the given ModelMediator's method : setMemento(Memento m)
 *
 *@author Philippe Lepage
 *@see Memento
 *@see ModelMediator
 *@see Command
 */
public class LoadSavedCommand implements Command{
private Memento saved;
private ModelMediator model;

	
	/**
	 *Instantiate class form.
	 *Sets the given parameters as attributes.
	 *
	 *@param model : ModelMediator on which a memento will be loaded
	 *@param saved : Memento that will be loaded on the parameter model : ModelMediator
	 */
	protected LoadSavedCommand(ModelMediator model ,Memento saved) {
		this.saved=saved;
		this.model=model;
	}


	@Override
	public void execute() {
		try {
			this.model.setMemento(this.saved);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
