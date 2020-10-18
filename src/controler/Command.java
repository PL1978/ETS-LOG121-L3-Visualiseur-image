/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Philippe Lepage

 Professeur : Vincent Lacasse
 Nom du fichier: Command.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/
package controler;

/**
 *Interface that defines implementing class as a command 
 *
 *@author Philippe Lepage
 */
public interface Command {

/**
 *Method to launch in order to execute the Command 
 */	
public void execute();

}
