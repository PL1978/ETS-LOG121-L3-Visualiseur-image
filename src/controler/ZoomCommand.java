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

import java.awt.Dimension;

import model.ModelMediator;

/**
 * Command that affects the display dimension of the given ModelMediator's
 * perspective.
 *
 * @author Philippe Lepage
 * @see Command
 * @see Undoable
 * @see Serializable
 * @see ModelMediator
 */
public class ZoomCommand implements Command, Undoable, java.io.Serializable {

	public static final double NORMAL_ZOOM_REFERENCE = 100;

	private double scaleFactor;
	private ModelMediator model;

	private int width;
	private int height;

	private int viewId;

	/**
	 * Instantiate class form. Sets the given parameters as attributes.
	 *
	 * @param model  : the ModelMediator who's perspective dimension will be changed
	 * @param zoom   : percentage that must be greater than 0 where 100 is default
	 *               size
	 * @param viewId : The identification of the view that calls the command.
	 *               Matches the index of the perspective to affect.
	 * @param absolute : True : the zoom will be calculated with the image size. False : the perspective size.
	 */
	protected ZoomCommand(ModelMediator model, int viewId, int zoom, boolean absolute) {

		this.scaleFactor = ((zoom - NORMAL_ZOOM_REFERENCE) / NORMAL_ZOOM_REFERENCE);
		this.model = model;
		this.viewId = viewId;

		if (absolute) {
			width = this.model.getImage().getWidth();
			height = this.model.getImage().getHeight();
		} else {
			width = this.model.getPerspectiveDimension(viewId).width;
			height = this.model.getPerspectiveDimension(viewId).height;
		}
	}

	@Override
	public void execute() {

		this.model.setPerspectiveDimension(viewId, computeDimension(scaleFactor));
		CommandExecuter.getInstance().getCommandHistory().addLast(this);

	}

	@Override
	public void undo() {

		this.model.setPerspectiveDimension(viewId, new Dimension(this.width, this.height));
		CommandExecuter.getInstance().getCommandUndone().addLast(this);
	}

	/**
	 * Calculates the new Display Dimension
	 */
	private Dimension computeDimension(double scaleFactor) {

		int newWidth = (int) (this.width + (this.width * scaleFactor));
		int newHeight = (int) (this.height + (this.height * scaleFactor));

		return new Dimension(newWidth, newHeight);
	}

	@Override
	public int getViewId() {
		return viewId;
	}

}
