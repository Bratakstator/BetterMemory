package no.bettermemory.interfaces.storageHandlers.databaseInserters;

public interface InsertToDatabase<T> {
    void saveObject(T t) throws Exception;
    void updateObject(T t) throws Exception;
}
