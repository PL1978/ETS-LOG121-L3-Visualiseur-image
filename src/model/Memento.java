package model;

import java.io.*;

/**
 * Represents a snapshot of an object instance.
 * It holds a name for UI purposes.
 *
 * @author Quentin Rimoux
 * @version 1.0
 */
public abstract class Memento implements Serializable {

    /**
     * Name given to the memento. Displayed in the loading menu.
     */
    private String name;

    private static final long serialVersionUID = 13L;

    public Memento(String name) {
    	this.name = name;
    }

    /**
     * Getter for the name of the memento.
     *
     * @return The current name of the memento.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name attribute.
     *
     * @param name The new name of the memento.
     */
    public void setName(String name) {
        this.name = name;
    }
}
