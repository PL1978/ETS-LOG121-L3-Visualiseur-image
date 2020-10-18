/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: AddPerspectiveCommand.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package controler;

import observerpattern.Observer;

import java.awt.Dimension;

import model.ModelMediator;
import model.Perspective;

/**
 *Command that adds a perspective to the parameter model : ModelMediator.
 *The command also adds an Observer and link the two with the viewId that called the command.
 *
 *@author Philippe Lepage
 *@see Command
 *@see ModelMediator
 *@see Observer
 */
class AddPerspectiveCommand implements Command {

	private ModelMediator model;
	private int keyId;
	private Observer o;
	
    /**
     * Instantiate class form.
     * Sets the parameters as attributes
     *
     * @param model : the ModelMediator on which a the perspective and observer will be added.
     * @param keyId : Identification of the view that calls this command. Used to map the perspective to the Observer.
     * @param o :  Observer that will be added to the ModelMediator and then linked to the created perspective. 
     */
	protected AddPerspectiveCommand(ModelMediator model, int keyId, Observer o) {

		this.model = model;
		this.keyId = keyId;
		this.o = o;
	}

@Override
public void execute() {
	int width =0;
	int height =0;
	
	if (this.model.getImage() != null) {
	width = this.model.getImage().getWidth();
	height = this.model.getImage().getHeight();
	}
	
	Perspective p = new Perspective(new Dimension(width, height));
	
	
	this.model.addPerspective(keyId, p);
	this.model.attachObserver(keyId, o);
	
}

}
