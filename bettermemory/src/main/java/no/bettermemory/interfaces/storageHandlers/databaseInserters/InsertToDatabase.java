package no.bettermemory.interfaces.storageHandlers.databaseInserters;

public interface InsertToDatabase<T> {
    void saveObject(T t);
    void updateObject(T t);
}
