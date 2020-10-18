/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: SaveCommand.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package controler;

import java.util.LinkedList;


import model.*;

/**
 * Command that saves a Perspective current attributes state.
 * Also saves the CommandExecuter's commandHistory : Stack<Command> and undoneHistory<Command>.
 * Do so by calling the given ModelMediator's method "createMemento".
 * The method returns a memento that is saved by the ModelMediator's Caretaker through addSave method.
 *
 * @author Philippe Lepage
 * @see Command
 * @see ModelMediator
 * @see CommandExecuter
 * @see MementoCaretaker
 */
public class SaveCommand implements Command {

    private ModelMediator model;
	private String name;
    

    /**
     * Instantiate class form.
     * Sets the given parameters as attributes.
     *
     * @param model : ModelMediator on which a memento will be created
     * @param name : name given to the memento
     */
    protected SaveCommand(ModelMediator model, String name) {
        this.model = model;
        this.name = name;
        
    }

    @Override
    public void execute() {
     
        this.model.getCaretaker().addSave(this.model.createMemento(this.name));
    }

   
}
