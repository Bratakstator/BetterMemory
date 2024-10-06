package no.bettermemory.models.storageHandlers.databaseInsertions;

import com.mongodb.client.MongoClient;

import no.bettermemory.interfaces.storageHandlers.databaseInserters.InsertToDatabase;




/**
 * This class is used to connect and write to a MongoDB database.
 * The ToMongoDB class takes the provided information and establishes a connection to the database if possible.
 * 
 * @param connectionString - This string should contain the information used to connect to a client.
 * 
 * @author Joakim Klemsdal Bøe
 * 
 * @code
 * To create an object of this class:
 * <pre>{@code ToMongoDB toMongoDB = new ToMongoDB("mongodb://localhost:27017")}
 */
public class ToMongoDB {
    private MongoClient client;

    public ToMongoDB(MongoClient client) {
        this.client = client;
    }

    
    /*public void saveObject(Object object) {
        MongoDatabase database = DatabaseConnections.getUsersDatabase(client);
        if (object instanceof Patient) {
            MongoCollection<Document> collection = DatabaseConnections.getPatientCollection(database);
            if (DatabaseDataHandler.checkIfExists(collection, object) == null) {
                Document document = ((Patient) object).toDocument();
                collection.insertOne(document);
            }
        }
        else if (object instanceof HealthCarePersonnel) {
            MongoCollection<Document> collection = DatabaseConnections.getHealthCarePersonnelCollection(database);
            if (DatabaseDataHandler.checkIfExists(collection, object) == null) {
                Document document = ((HealthCarePersonnel) object).toDocument();
                collection.insertOne(document);
            }
        }
        else if (object instanceof Week) {
            MongoCollection<Document> activityCollection = DatabaseConnections.getActivitiesCollection(database);
            MongoCollection<Document> dayCollection = DatabaseConnections.getDaysCollection(database);
            MongoCollection<Document> weekCollection = DatabaseConnections.getWeeksCollection(database);

            Week week = (Week) object;
            ArrayList<Day> days = week.getDays();
            List<ObjectId> dayResultValues = new ArrayList<>();
            for (Day day : days) {
                List<Document> activities = day.getActivities().stream().filter(
                    activity -> DatabaseDataHandler.checkIfExists(activityCollection, activity) == null
                ).map(Activity::toDocument).collect(Collectors.toList());

                List<Activity> existingActivities = day.getActivities().stream().filter(
                    activity -> DatabaseDataHandler.checkIfExists(activityCollection, activity) != null
                ).collect(Collectors.toList());

                List<ObjectId> insertResultValues;

                if (activities.size() != 0) {
                    try {
                        InsertManyResult insertResult = activityCollection.insertMany(activities);
                        insertResultValues = insertResult.getInsertedIds().values()
                        .stream().map(document -> document.asObjectId().getValue()).collect(Collectors.toList());
                    } catch (MongoBulkWriteException e) {
                        insertResultValues = e.getWriteResult().getInserts().stream()
                        .map(document -> document.getId().asObjectId().getValue()).collect(Collectors.toList());
                    }
                } else insertResultValues = new ArrayList<>();

                List<ObjectId> existingIds = existingActivities.stream().map(
                    activity -> DatabaseDataHandler.checkIfExists(activityCollection, activity)
                ).collect(Collectors.toList());

                List<ObjectId> allIds = Stream.concat(
                    insertResultValues.stream(),
                    existingIds.stream()
                ).distinct().toList();

                ObjectId dayExists = DatabaseDataHandler.checkIfExists(dayCollection, day);

                if (dayExists == null) {
                    Document dayDocument = day.toDocument();
                    dayDocument.append("activities", allIds);
                    InsertOneResult insertOneResult = dayCollection.insertOne(dayDocument);
                    dayResultValues.add(insertOneResult.getInsertedId().asObjectId().getValue());
                }
                else {
                    dayResultValues.add(dayExists);
                }
            }
            if (DatabaseDataHandler.checkIfExists(weekCollection, week) == null) {
                Document weekDocument = week.toDocument();
                weekDocument.append("days", dayResultValues);
                weekCollection.insertOne(weekDocument);
            }
        }
    }

    
    public void updateObject(Object object) {}

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public MongoClient getClient() {
        return client;
    }*/
}
