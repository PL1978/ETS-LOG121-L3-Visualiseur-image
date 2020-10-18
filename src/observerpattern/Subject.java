package observerpattern;

/**
 * Defines the behavior of Subject objects which are meant to be observed by Observer objects.
 * This interface provides methods to add and remove observers, and also notify all the currently registered Observer
 * instances.
 *
 * @author Quentin Rimoux
 * @version 1.0
 * @see Observer
 */
public interface Subject {

    /**
     * Register the observer so it is notified when a change happens.
     * Maps the observer to a perspective using the key.
     *
     * @param o The Observer instance to register.
     * @param key key of the associated perspective
     */
    void attachObserver(int key, Observer o);

    /**
     * Remove the observer from the registered observers.
     *
     * @param key The index of the observer to delete.
     */
    void deleteObserver(int key);

    /**
     * Notify the observer associated to the key
     */
    void notifyObserver(int key);

    /**
     * Notify all the currently registered Observer instances by calling their update() method.
     */
    void notifyAllObserver();
}
