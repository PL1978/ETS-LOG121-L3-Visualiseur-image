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

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import model.ModelMediator;

/**
 * This command is to load the image to the model.
 * 
 * @author Bradley How
 * @since 1.0
 *  
 */
public class LoadImage implements Command {
	private ModelMediator med;
	private File selectedFile;
	
	public LoadImage(ModelMediator med, File selectedFile) {
		this.med = med;
		this.selectedFile = selectedFile;
	}
	
	/**
	 * This method will open a JFileChooser with a images filter. If the file is valid, it will put in the model and put the dimension to 0,0. 
	 * @see controler.Command#execute()
	 */
	
	public void execute() {

			BufferedImage img = null;

			try {
				img = ImageIO.read(this.selectedFile);
				med.setImage(img);
				med.setImagePath(selectedFile.getAbsolutePath());
				med.setPerspectiveDimension( 0, new Dimension(img.getWidth(), img.getHeight()));
				med.setPerspectivePosition(0, new Point());
			} catch (IOException e1) {
			}

		
	}

}
