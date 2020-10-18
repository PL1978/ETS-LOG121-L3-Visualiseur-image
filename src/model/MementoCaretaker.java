package model;

import java.io.*;
import java.util.*;

/**
 * Stores all MementoModelMediator instances (as Memento instance though) in the same order as they are created.
 *
 * @author Quentin Rimoux
 * @version 1.0
 */
public class MementoCaretaker {

    /**
     * Store all saved Memento.
     */
    private List<Memento> saveList;

    private static final long serialVersionUID = 15L;

    /**
     * Constructor that only initializes the inner List.
     */
    public MementoCaretaker() {
        saveList = new LinkedList<>();
    }

    /**
     * Returns the Memento instance corresponding to the given id (which is its position in the inner list).
     *
     * @param id The Memento's position in the list to retrieve.
     * @return The corresponding Memento.
     * @throws IllegalArgumentException If id<0 or id>=saveList.size().
     */
    public Memento getSave(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Parameter id should be greater or equal than 0.");
        } else if (id >= saveList.size()) {
            throw new IllegalArgumentException("Parameter id should be less than the list's size.");
        }

        return saveList.get(id);
    }

    /**
     * Add the given Memento to the inner List.
     *
     * @param save The Memento to store.
     * @throws IllegalArgumentException If parameter save is null.
     */
    public void addSave(Memento save) {
        if (save == null) {
            throw new IllegalArgumentException("Parameter save cannot be null.");
        }
        saveList.add(save);
    }
    
    public void removeSave(Memento save) {
        if (save == null) {
            throw new IllegalArgumentException("Parameter save cannot be null.");
        }
        saveList.remove(save);
    }
}
