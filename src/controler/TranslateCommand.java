/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: TranslateCommand.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package controler;

import java.awt.Point;

import model.ModelMediator;

/**
 *Command that changes the display position of a given ModelMediator's Perspective.
 *This command adds itself in the correct list upon execution.
 *
 *@author Philippe Lepage
 *@see Command
 *@see Undoable
 *@see Serializable
 *@see ModelMediator
 */
public class TranslateCommand implements Command, Undoable, java.io.Serializable  {

	private int xTranslation;
	private int yTranslation;
	private ModelMediator model;
	private boolean packed;
	private int viewId;

	/**
	 *Instantiate class form.
	 *Sets the given parameters as attributes.
	 *The packed attributed will be set as false by default.
	 *
	 *@param model : the ModelMediator who's perspective position will be changed
	 *@param viewId : The identification of the view that calls the command. Matches the index of the perspective to affect.
	 *@param xTranslation : Cartesian direction where the X value of Perspective's display position will be moved
	 *@param yTranslation : Cartesian direction where the Y value of Perspective's display position will be moved
	 */
	protected TranslateCommand(ModelMediator model, int viewId,int xTranslation, int yTranslation) {
		this.model = model;
		this.viewId= viewId;
		this.xTranslation = xTranslation;
		this.yTranslation = yTranslation;
		this.packed = false;
	}
	
	/**
	 *Instantiate class form.
	 *Sets the given parameters as attributes.
	 *
	 *@param model : the ModelMediator who's perspective position will be changed
	 *@param viewId : The identification of the view that calls the command. Matches the index of the perspective to affect.
	 *@param xTranslation : Cartesian direction where the X value of Perspective's display position will be moved
	 *@param yTranslation : Cartesian direction where the Y value of Perspective's display position will be moved
	 *@param packed : describe if the current translation is a complete translation by itself.
	 */
	protected TranslateCommand(ModelMediator model,int viewId, int xTranslation, int yTranslation, boolean packed) {
		this.model = model;
		this.viewId= viewId;
		this.xTranslation = xTranslation;
		this.yTranslation = yTranslation;
		this.packed = packed;
	}

	@Override
	public void execute() {
	
		this.model.setPerspectivePosition(viewId, computeNewPosition(this.xTranslation,this.yTranslation));
			
		CommandExecuter.getInstance().getCommandHistory().addLast(this);
		
	}

	@Override
	public void undo() {
		
		this.model.setPerspectivePosition(viewId, computeNewPosition(this.xTranslation*-1,this.yTranslation*-1));
		
		CommandExecuter.getInstance().getCommandUndone().addLast(this);
	}

	
	/**
	 *Calculates the new Cartesian Point of the display Position
	 */
	private Point computeNewPosition(int x,int y) {
		int newX = this.model.getPerspectivePosition(viewId).x+x;
		int newY = this.model.getPerspectivePosition(viewId).y+y;
		
		return new Point(newX, newY);
	}
	
	public boolean getPacked () {
		return this.packed;
	}

	public int getXtranslation() {
		return xTranslation;
	}

	public int getYtranslation() {
		return yTranslation;
	}

	@Override
	public int getViewId() {
		return viewId;
	}
	
}
