package no.bettermemory.interfaces.storageHandlers;

public interface ObjectToDatabase<T> {

    /*
     * This methode should check if a given object already exists in the database.
     */
    boolean alreadyExists(T object) throws IllegalArgumentException;

    /*
     * This methode should write a given object to a given database.
     */
    void save(T object);

}
