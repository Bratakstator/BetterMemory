package no.bettermemory.models.storageHandlers.databaseExtraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        getDayFromMongoDB = new GetDayFromMongoDB(mockClient);
    }

    @Test 
    @DisplayName("Test of extraction of Day object from mongoDB where days exists")
    public void testGetSpecificWithDays() throws Exception{
        //Arrange
        String patientId = "0003333";
        int year = 2024;
        int weekNumber = 50;
        String dayName = "Monday";


        try(MockedStatic<DatabaseConnections> mockedDatabaseConnection = mockStatic(DatabaseConnections.class)) {
            mockedDatabaseConnection.when(() -> DatabaseConnections.getWeeksCollection(mockDatabase))
            .thenReturn(mockCollection);
            mockedDatabaseConnection.when(() -> DatabaseConnections.getDaysCollection(mockDatabase))
            .thenReturn(mockCollection);


            doReturn(true).when(mockDocument).containsKey("days");
            doReturn(new ArrayList<>(List.of(new ObjectId()))).when(mockDocument).get("days");
            when(mockFindIterable.first()).thenReturn(mockDocument);
            when(mockCollection.find(ArgumentMatchers.any(Document.class))).thenReturn(mockFindIterable);
            when(mockDocument.getString("day")).thenReturn(dayName);
            

            //Act
            Day day = getDayFromMongoDB.getSpecific(patientId, year, weekNumber, dayName);

            //Assert
            assertNotNull(day);
            assertEquals(dayName, day.getDayName());

        }
    
    }

    @Test
    @DisplayName("Test of extraction of Day object from mongoDB where days do not exists")
    public void testGetSpecificWithoutDays() throws Exception{
        //Arrange
        String patientId = "0003333";
        int year = 2024;
        int weekNumber = 50;
        String dayName = "Monday";


        try(MockedStatic<DatabaseConnections> mockedDatabaseConnection = mockStatic(DatabaseConnections.class)) {
            mockedDatabaseConnection.when(() -> DatabaseConnections.getWeeksCollection(mockDatabase))
            .thenReturn(mockCollection);
            mockedDatabaseConnection.when(() -> DatabaseConnections.getDaysCollection(mockDatabase))
            .thenReturn(mockCollection);


            doReturn(false).when(mockDocument).containsKey("days");
            when(mockFindIterable.first()).thenReturn(mockDocument);
            when(mockCollection.find(ArgumentMatchers.any(Document.class))).thenReturn(mockFindIterable);
            
        
            //Act
            Exception exception = assertThrows(Exception.class, () -> {
                getDayFromMongoDB.getSpecific(patientId, year, weekNumber, dayName);
            });

            //Assert
            assertEquals("No days are registered for week 50.", exception.getMessage());

        }
    
    }


}




