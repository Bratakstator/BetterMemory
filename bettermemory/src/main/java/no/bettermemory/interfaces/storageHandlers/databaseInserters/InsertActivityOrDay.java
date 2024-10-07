package no.bettermemory.interfaces.storageHandlers.databaseInserters;

import org.bson.types.ObjectId;

public interface InsertActivityOrDay<T> {
    ObjectId saveObject(T t) throws Exception;
    void updateObject(ObjectId tId, T t) throws Exception;
}
