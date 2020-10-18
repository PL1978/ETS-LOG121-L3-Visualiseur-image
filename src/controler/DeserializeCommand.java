/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: DeserializeCommand.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package controler;

import java.io.*;


import model.Memento;
import model.ModelMediator;

/**
 * Command that will read the Memento saved by the SerializeCommand and add it to Mediator Caretaker.
 * This command should be launched only once, upon the program's initialization.
 * This command should not be saved in the commandHistory : Stack<Command>.
 *
 * @author Philippe Lepage
 * @see ModelMediator
 * @see Command
 * @see ObjectInputStream
 */
public class DeserializeCommand implements Command {

    //this model's caretaker will receive de deserialized memento
    private ModelMediator model;

    /**
     * Instantiate class form.
     * Sets the parameter model : ModelMediator as attribute.
     *
     * @param model : the ModelMediator who's caretaker will receive de deserialized memento
     */
    protected DeserializeCommand(ModelMediator model) {
        this.model = model;
    }

    @Override
    public void execute() {
        Memento mementoToAdd;

        FileInputStream fos = null;
        ObjectInputStream out = null;
        //Adds memento to the model's caretaker until the end of the file is reached
        try {
            fos = new FileInputStream(SerializeCommand.filePath);
            out = new ObjectInputStream(fos);
            while (true) {

                mementoToAdd = (Memento) out.readObject();

                this.model.getCaretaker().addSave(mementoToAdd);
            }

        } catch (ClassNotFoundException | IOException eof) {
         
        }

    }

}

