/* *****************************************************
Cours:  LOG121
Session: H2020
Groupe:  02
Projet: Laboratoire #3
Étudiant(e)s: Philippe Lepage

Professeur : Vincent Lacasse
Nom du fichier: ModelMediator.java
Date crée: 2020-03-29
Date dern. modif. 2020-03-29
*******************************************************
Historique des modifications
*******************************************************
2020-03-29 Version initiale (et1)
*******************************************************/
package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import controler.*;
import observerpattern.*;
import observerpattern.Observer;

import javax.imageio.*;

/**
 * Mediator for the model package. Is observed by classes in the view package.
 * Possesses the perspective of each view and the image shared by all.
 * <p>
 * Only methods from this class should be called by classes outside the package.
 *
 * @author Philippe Lepage
 * @see Perspective
 * @see Subject
 * @see Observer
 */
public class ModelMediator implements Subject, Serializable {

    private HashMap<Integer, Perspective> perspectiveMap;

    transient private BufferedImage image;

    private String imagePath;

    transient private MementoCaretaker caretaker;

    private HashMap<Integer, Observer> observerMap;

    private static final long serialVersionUID = 12L;


    public ModelMediator() {
        observerMap = new HashMap<Integer, Observer>();
        perspectiveMap = new HashMap<Integer, Perspective>();
        caretaker = new MementoCaretaker();

    }

    public void addPerspective(int key, Perspective value) {
        this.perspectiveMap.put(key, value);
    }

    public void removePerspective(int key) {
        this.perspectiveMap.remove(key);
    }

    public void setPerspectivePosition(int key, Point position) {
        this.perspectiveMap.get(key).setDisplayPosition(position);
        notifyObserver(key);
    }

    public Point getPerspectivePosition(int i) {
        return this.perspectiveMap.get(i).getDisplayPosition();
    }

    public Dimension getPerspectiveDimension(int i) {
    	return this.perspectiveMap.get(i).getDimension();
    }

    public void setPerspectiveDimension(int key, Dimension dimension) {
        this.perspectiveMap.get(key).setDimension(dimension);
        notifyObserver(key);
    }


    public HashMap<Integer, Perspective> getPerspectiveMap() {
        return perspectiveMap;
    }

    public void setPerspective(HashMap<Integer, Perspective> p) {
        this.perspectiveMap = p;
    }
    // END

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        notifyAllObserver();
    }

    public MementoCaretaker getCaretaker() {
        return caretaker;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public void attachObserver(int key, Observer o) {
        this.observerMap.put(key, o);
    }

    @Override
    public void deleteObserver(int key) {
        this.observerMap.remove(key);
    }

    @Override
    public void notifyAllObserver() {
        for (Integer integer : this.observerMap.keySet()) {
            this.observerMap.get(integer).update();
        }
    }

    @Override
    public void notifyObserver(int key) {
        this.observerMap.get(key).update();
    }

    /**
     * Create a Memento of the perspective,
     *
     * @param history Command history.
     * @param undone  Undone Command history.
     * @return A Memento instance of the current perspective's state.
     */
    public Memento createMemento(String name) {
       return new MementoModelMediator(name, perspectiveMap, imagePath);
//        return new MementoModelMediator(name, perspectiveMap, imagePath);
    }

    /**
     * Load the given Memento's state into the current ModelMediator instance.
     *
     * @param m The Memento to load.
     */
    public void setMemento(Memento m) throws IOException, IllegalArgumentException {
        if (!(m instanceof MementoModelMediator)) {
            throw new IllegalArgumentException("m should be an instance of MementoModelMediator.");
        }
        MementoModelMediator mmm = (MementoModelMediator) m;
        this.perspectiveMap = clonePerspectiveMap(mmm.getPerspectiveHashMap());
        this.imagePath = mmm.getImagePath();
        this.image = ImageIO.read(new File(imagePath));
        
        CommandExecuter.getInstance().setCommandHistory(mmm.getHistory());
        CommandExecuter.getInstance().setCommandHistory(mmm.getUndone());
        
        notifyAllObserver();
    }

    private HashMap<Integer,Perspective> clonePerspectiveMap(HashMap<Integer, Perspective> toClone) {
    	HashMap<Integer,Perspective> clone = new HashMap<Integer, Perspective>();
    	for (int clef: toClone.keySet()) {
    		clone.put(clef, toClone.get(clef).clonePerspective());
        }
    return clone;
    }
    
    /**
     * Implementation of Memento for the ModelMediator class.
     * It holds the perspective's state, the image and the command stack.
     * <p>
     *
     * @author Quentin Rimoux
     * @version 1.0
     */
    class MementoModelMediator extends Memento implements Serializable {

        /**
         * The saved perspective.
         */
        private HashMap<Integer, Perspective> perspectiveHashMap;

        /**
         * The image path used when the Memento was created.
         */
        private String image;

        /**
         * The list of all commands input by the user.
         */
        private LinkedList<Command> history;

        /**
         * The history of all commands undone by the user.
         */
        private LinkedList<Command> undone;

        /**
         * Only constructor that takes in parameter the current perspective Map.
         *
         * @param p       The Perspective instance of the ModelMediator.
         * @param i       The BufferedImage instance.
         * @param history The Command history.
         * @param undone  The undone Command history.
         */
        private MementoModelMediator(String name, HashMap<Integer, Perspective> p, String i) {
            super(name);
            perspectiveHashMap = clonePerspectiveMap(p);
            image = i;
        }

        
        
        /**
         * Private getter for the perspective attribute.
         *
         * @return The saved Perspective.
         */
        private HashMap<Integer, Perspective> getPerspectiveHashMap() {
            return perspectiveHashMap;
        }

        /**
         * Private getter for the image attribute.
         *
         * @return The saved image.
         */
        private String getImagePath() {
            return image;
        }

        /**
         * Private getter for the history attribute.
         *
         * @return The list of Command saved.
         */
        private LinkedList<Command> getHistory() {
            return history;
        }

        /**
         * Private getter for the undone attribute.
         *
         * @return The list of Command undone saved.
         */
        private LinkedList<Command> getUndone() {
            return undone;
        }

    }
}
