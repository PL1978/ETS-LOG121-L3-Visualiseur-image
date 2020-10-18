/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: Perspective.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package model;

import java.awt.Dimension;
import java.awt.Point;
import java.io.*;

/**
 *Class that represents the printed area by the views.
 *The area is defined by a position and a size.
 * 
 *@author Philippe Lepage
 *@see Dimension
 *@see Point
 */
public class Perspective implements Serializable {

    private Point displayPosition;
    private Dimension dimension;

    private static final long serialVersionUID = 15L;

    public Perspective() {
    	this.dimension = new Dimension();
    	this.displayPosition = new Point();
    }
    
    public Perspective(Dimension d) {
    	this.dimension = d;
    	this.displayPosition = new Point();
    }

    protected Perspective clonePerspective() {
    	Perspective clone = new Perspective(this.dimension);
    	clone.setDisplayPosition(this.displayPosition);
    	return clone;
    }
    
    protected Dimension getDimension() {
        return dimension;
    }

    protected void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    protected Point getDisplayPosition() {
        return displayPosition;
    }

    protected void setDisplayPosition(Point position) {
        this.displayPosition = position;
    }
}
