package no.bettermemory.interfaces.storageHandlers.databaseInserters;

import org.bson.types.ObjectId;

import no.bettermemory.models.activity.Activity;

public interface InsertActivity {
    void saveObject(Activity activity) throws Exception;
    void updateObject(ObjectId activityId, Activity activity) throws Exception;
}
