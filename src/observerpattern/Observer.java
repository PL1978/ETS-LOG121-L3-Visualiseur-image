package observerpattern;

/**
 * Defines the behavior of objects that can observe Subject objects.
 * The method update() is called by the Observables that registered the Observer whenever a meaningful inner change
 * happened.
 *
 * @see Subject
 * @author Quentin Rimoux
 * @version 1.0
 */
public interface Observer {

    /**
     * Tells the Observer that a meaningful changed happened and that he must update to reflect the change.
     */
    void update();
}
