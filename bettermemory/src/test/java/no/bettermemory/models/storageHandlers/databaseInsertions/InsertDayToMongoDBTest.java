package no.bettermemory.models.storageHandlers.databaseInsertions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
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

import java.util.ArrayList;

import no.bettermemory.models.activity.Activity;
import no.bettermemory.models.time.Day;
import no.bettermemory.tools.DatabaseConnections;

@ExtendWith(MockitoExtension.class)
public class InsertDayToMongoDBTest {
    @Mock
    private MongoClient mockClient;
    @Mock
    private MongoDatabase mockDatabase;
    @Mock
    private MongoCollection<Document> mockCollection;
    
    private InsertDayToMongoDB insertDay;

    @Mock
    private InsertActivityToMongoDB mockInsertActivity;

    @Mock
    private Day mockDay;
    
    private ArrayList<Activity> mockActivities;

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
        when(DatabaseConnections.getDaysCollection(mockDatabase)).thenReturn(mockCollection);
        insertDay = new InsertDayToMongoDB(mockClient, mockInsertActivity);

        mockActivities = new ArrayList<>();
    }

    @Test
    public void testSuccessfullyInsertNewDay() throws Exception {
        // Arrange
        mockActivities.add(mockActivity);

        when(mockDay.toDocument()).thenReturn(mockDocument);

        when(mockDay.getActivities()).thenReturn(mockActivities);
        
        when(mockInsertActivity.saveObject(mockActivity)).thenReturn(mockObjectId);
        when(mockDocument.append(anyString(), anyList())).thenReturn(mockDocument);

        when(mockCollection.insertOne(mockDocument)).thenReturn(mockInsertOneResult);
        when(mockInsertOneResult.getInsertedId()).thenReturn(mockBsonValue);
        when(mockBsonValue.asObjectId()).thenReturn(mockBsonObjectId);
        when(mockBsonObjectId.getValue()).thenReturn(mockObjectId);

        // Act
        ObjectId objectId = insertDay.saveObject(mockDay);

        // Assert
        assertEquals(mockObjectId, objectId);
        verify(mockInsertActivity).saveObject(mockActivity);
    }

    @Test
    public void testInsertNewDayWithoutActivities() throws Exception {
        // Arrange
        when(mockDay.toDocument()).thenReturn(mockDocument);

        when(mockDay.getActivities()).thenReturn(mockActivities);

        when(mockCollection.insertOne(mockDocument)).thenReturn(mockInsertOneResult);
        when(mockInsertOneResult.getInsertedId()).thenReturn(mockBsonValue);
        when(mockBsonValue.asObjectId()).thenReturn(mockBsonObjectId);
        when(mockBsonObjectId.getValue()).thenReturn(mockObjectId);

        // Act
        ObjectId objectId = insertDay.saveObject(mockDay);

        // Assert
        assertEquals(mockObjectId, objectId);
        verify(mockInsertActivity, never()).saveObject(mockActivity);
    }

    @Test
    public void testInsertNewDayWhereDayIsNull() {
        // Arrange
        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> insertDay.saveObject(null)
        );

        // Assert
        assertEquals("Day object is null.", exception.getMessage());
    }
}
