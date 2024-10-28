package no.bettermemory.models.storageHandlers.databaseInsertions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.bson.BsonObjectId;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import no.bettermemory.models.activity.Activity;
import no.bettermemory.tools.DatabaseConnections;

@ExtendWith(MockitoExtension.class)
public class InsertActivityToMongoDBTest {
    @Mock
    private MongoClient mockClient;
    @Mock
    private MongoDatabase mockDatabase;
    @Mock
    private MongoCollection<Document> mockCollection;
    
    private InsertActivityToMongoDB insertActivity;

    @Mock
    private Activity mockActivity;
    @Mock
    private Document mockDocument;
    @Mock
    private InsertOneResult mockInsertOneResult;
    @Mock
    private BsonValue mockBsonValue;
    @Mock
    private BsonObjectId mockBsonObjectId;
    @Mock
    private ObjectId mockObjectId;

    @BeforeEach
    void setup() {
        when(DatabaseConnections.getUsersDatabase(mockClient)).thenReturn(mockDatabase);
        when(DatabaseConnections.getActivitiesCollection(mockDatabase)).thenReturn(mockCollection);
        insertActivity = new InsertActivityToMongoDB(mockClient);
    }

    @Test
    public void testInsertNewActivity() throws Exception {
        // Arrange
        when(mockActivity.toDocument()).thenReturn(mockDocument);
        when(mockCollection.insertOne(mockDocument)).thenReturn(mockInsertOneResult);
        when(mockInsertOneResult.getInsertedId()).thenReturn(mockBsonValue);
        when(mockBsonValue.asObjectId()).thenReturn(mockBsonObjectId);
        when(mockBsonObjectId.getValue()).thenReturn(mockObjectId);

        // Act
        ObjectId objectId = insertActivity.saveObject(mockActivity);

        // Assert
        assertEquals(mockObjectId, objectId);
    }

    @Test
    public void testInsertNewActivityWhenActivityIsNull() {
        // Arrange
        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> insertActivity.saveObject(null)
        );

        // Assert
        assertEquals("Activity is null.", exception.getMessage());
    }

    @Test
    public void testUpdatingActivity() throws Exception {
        // Arrange
        when(mockActivity.toDocument()).thenReturn(mockDocument);

        // Act
        insertActivity.updateObject(mockObjectId, mockActivity);

        // Assert
        verify(mockCollection).replaceOne(any(Document.class), any(Document.class));
    }

    @Test
    public void testUpdateNewActivityWhenActivityIsNull() {
        // Arrange
        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> insertActivity.updateObject(mockObjectId, null)
        );

        // Assert
        assertEquals("Activity is null.", exception.getMessage());
    }

    @Test
    public void testUpdateNewActivityWhenActivityIdIsNull() {
        // Arrange
        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> insertActivity.updateObject(null, mockActivity)
        );

        // Assert
        assertEquals("ActivityId is null.", exception.getMessage());
    }
}
