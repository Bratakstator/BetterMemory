package no.bettermemory.interfaces.storageHandlers.databaseInserters;

import com.mongodb.client.MongoClient;

public interface InsertSpecificObject<T> {
    void saveObject(T t, MongoClient client);
    //void updateObject(T t, MongoClient client);
}
