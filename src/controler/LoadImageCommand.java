/* *****************************************************
 Cours:  LOG121
 Session: H2020
 Groupe:  02
 Projet: Laboratoire #3
 Étudiant(e)s: Bradley How

 Professeur : Vincent Lacasse
 Nom du fichier: LoadImage.java
 Date crée: 2020-03-29
 Date dern. modif. 2020-03-29
 *******************************************************
 Historique des modifications
 *******************************************************
 2020-03-29 Version initiale (et1)
 *******************************************************/

package controler;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


import javax.imageio.ImageIO;


import model.ModelMediator;


/**
 * This command is to load the image to the model.
 * 
 * @author Philippe Lepage
 * @author Bradley How
 * @since 1.0
 *  
 */
public class LoadImageCommand implements Command {
	private ModelMediator med;
	private File selectedFile;
	
    /**
     * Instantiate class form.
     * Sets the parameter the parameters as attributes.
     *
     * @param med : the ModelMediator who's image attribute will be set.
     * @param felctedFile : file selected by the user that will be read.
     */
	protected LoadImageCommand(ModelMediator med,File selectedFile) {
		this.med = med;
		this.selectedFile = selectedFile;
	}
	
	public void execute() {

			BufferedImage img = null;
			
			try {
				
				img = ImageIO.read(this.selectedFile);
				int width = img.getWidth();
				int height = img.getHeight();
				Iterator<Integer> keys = this.med.getPerspectiveMap().keySet().iterator();
				
				while (keys.hasNext()){
				int key = keys.next();	
					med.setPerspectiveDimension(key, new Dimension(width,height));
					med.setPerspectivePosition(key, new Point());
				}
				
				med.setImage(img);
				med.setImagePath(selectedFile.getAbsolutePath());
				
				CommandExecuter.getInstance().clearLists();
				
			} catch (IOException e1) {
			
			}

		
	}

}
