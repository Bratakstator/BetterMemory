package no.bettermemory.interfaces.storageHandlers.databaseInserters;

public interface InsertToDatabase<T> {
    /**
     *  This method should be used to save a HealthCarePersonnel, Patient or Week to MongoDB. 
     *  @param t -  The HealthCarePersonnel, Patient or Week you want to add to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertToDatabase.saveObject(t);}
     */
    void saveObject(T t) throws Exception;

    /**
     *  This method should be used to update an a HealthCarePersonnel, Patient or Week in a MongoDB. 
     *  @param t -  The HealthCarePersonnel, Patient or Week you want to update to the database
     *  @code 
     *  This is an example of how you can use this method:
     *  <pre>{@code  InsertToDatabase.updateObject(t);}
     */
    void updateObject(T t) throws Exception;
}
