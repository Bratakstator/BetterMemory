package no.bettermemory.models.storageHandlers.databaseExtraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.models.time.Day;
import no.bettermemory.tools.DatabaseConnections;

@ExtendWith(MockitoExtension.class)
public class GetDayFromMongoDBTest {
    @Mock
    private MongoClient mockClient;
    @Mock
    private MongoDatabase mockDatabase;
    @Mock
    private MongoCollection<Document> mockCollection;
    @Mock
    private FindIterable<Document> mockFindIterable;
    @Mock
    private Document mockDocument;
    @Mock
    private Day mockDay;

    private GetDayFromMongoDB getDayFromMongoDB;

    @BeforeEach
    void setUp() {
        when(DatabaseConnections.getUsersDatabase(mockClient)).thenReturn(mockDatabase);
        //when(DatabaseConnections.getActivitiesCollection(mockDatabase)).thenReturn(mockCollection);
        getDayFromMongoDB = new GetDayFromMongoDB(mockClient);
    }

    @Test 
    public void testGetSpecific() throws Exception{
        //Arrange
        String patientId = "0003333";
        int year = 2024;
        int weekNumber = 50;
        String dayName = "Monday";


        try(MockedStatic<DatabaseConnections> mockedDatabaseConnection = mockStatic(DatabaseConnections.class)) {
            mockedDatabaseConnection.when(() -> DatabaseConnections.getUsersDatabase(mockClient))
            .thenReturn(mockDatabase);
            mockedDatabaseConnection.when(() -> DatabaseConnections.getWeeksCollection(mockDatabase))
            .thenReturn(mockCollection);
            mockedDatabaseConnection.when(() -> DatabaseConnections.getDaysCollection(mockDatabase))
            .thenReturn(mockCollection);

            
            doReturn(true).when(mockDocument).containsKey("days");
            doReturn(new ArrayList<>(List.of(new ObjectId()))).when(mockDocument).get("days");
            when(mockFindIterable.first()).thenReturn(mockDocument);
            when(mockCollection.find(ArgumentMatchers.any(Document.class))).thenReturn(mockFindIterable);
            when(mockDocument.getString("day")).thenReturn(dayName);
            

            Day day = getDayFromMongoDB.getSpecific(patientId, year, weekNumber, dayName);

            assertNotNull(day);
            assertEquals(dayName, day.getDayName());

        }
    
    }

    
}




