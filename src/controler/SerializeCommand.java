/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: SerializeCommand.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package controler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.Memento;
import model.MementoCaretaker;
import model.ModelMediator;

/**
 *Command that Serialze the saved Memento of the given ModelMediator's Caretaker.
 *Allows to conserve the saved perspective data when the program is closed.
 *
 *@author Philippe Lepage
 *@see Command
 *@see ModelMediator
 *@see ObjectOutputStream
 */
public class SerializeCommand implements Command {

	/**
	 *Path where the data will be serialized.
	 *
	 *MODIFY CAUTIOUSLY
	 */
	public final static String filePath = "ser/saves.ser";

	private ModelMediator model;

	/**
	 *Instantiate class form.
	 *Sets the given parameters as attributes.
	 *
	 *@param model : the ModelMediator who's caretaker will have it's saved memento serialized
	 */
	protected SerializeCommand(ModelMediator model) {
		this.model = model;
	}

	@Override
	public void execute() {
		MementoCaretaker mc = this.model.getCaretaker();
		Memento mementoToSerialize;


		try {
			//Deletes the previously serialzed data since it's been deserialized upon the program's 
			//and we want to avoid data duplication.
			if (!Files.deleteIfExists(Paths.get(filePath))) {
				new File (filePath).createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			try {
				for (int i = 0;; i++) {

					mementoToSerialize = mc.getSave(i);
					out.writeObject(mementoToSerialize);

				}
			} catch (IllegalArgumentException iae) {
				out.close();
			}

		} catch (IOException e) {
			System.out.println("saves serialized");
		}

	}

}
