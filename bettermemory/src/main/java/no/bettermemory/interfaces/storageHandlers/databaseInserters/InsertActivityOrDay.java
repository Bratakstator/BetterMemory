package no.bettermemory.interfaces.storageHandlers.databaseInserters;

import org.bson.types.ObjectId;

public interface InsertActivityOrDay<T> {

    /**
     *  This method should be used to save an activity or a day to MongoDB. 
     *  @param t -  The activity or day you want to add to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertActivityToMongoDB.saveObject(t);}
     */
    ObjectId saveObject(T t) throws Exception;

    /**
     *  This method should be used to update an activity or a day in a MongoDB. 
     *  @param tId -  The activityID or dayIDyou want to update to the database
     *  @param t -  The activity or day you want to update to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertActivityToMongoDB.updateObject(tId, t);}
     */
    void updateObject(ObjectId tId, T t) throws Exception;
}
