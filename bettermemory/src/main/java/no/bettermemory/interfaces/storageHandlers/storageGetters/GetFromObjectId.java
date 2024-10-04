package no.bettermemory.interfaces.storageHandlers.storageGetters;

import org.bson.types.ObjectId;
import java.util.List;

public interface GetFromObjectId<T> {
    /**
     * Returns object using its stored object id.
     * 
     * @param dayId
     * 
     * @return Day
     * 
     * @throws Exception
     * 
     */
    T getSpecificFromObjectId(ObjectId tId) throws Exception;

    /**
     * Returns a list of objects using object id.
     * 
     * @param dayIds
     * 
     * @return List
     * 
     * @throws Exception
     * 
     */
    List<T> getListFromObjectId(List<ObjectId> tIds) throws Exception;
}
