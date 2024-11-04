package no.bettermemory.models.storageHandlers.databaseExtraction;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;

import no.bettermemory.tools.DatabaseConnections;
import no.bettermemory.models.time.Day;
import no.bettermemory.models.activity.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
public class GetDayFromMongoDBTest {

    @Mock
    private  MongoClient mockMongoClient;

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockCollection;



    private GetDayFromMongoDB getDayFromMongoDB;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        try(MockedStatic<DatabaseConnections> mockedStatic = mockStatic(DatabaseConnections.class)){
            mockedStatic.when(() -> DatabaseConnections.getUsersDatabase(mockMongoClient))
                                                       .thenReturn(mockDatabase);
            mockedStatic.when(() -> DatabaseConnections.getWeeksCollection(mockDatabase))
                                                       .thenReturn(mockCollection);
            mockedStatic.when(() -> DatabaseConnections.getDaysCollection(mockDatabase))
                                                       .thenReturn(mockCollection);
            getDayFromMongoDB = new GetDayFromMongoDB(mockMongoClient);
        }

    }

    
    @Test
    @DisplayName("Test constructor for GetDayFromMongoDB")
    public void testGetDayFromMongoDB() {
        assertNotNull(getDayFromMongoDB);
        assertEquals(mockMongoClient, getDayFromMongoDB.getClient());
        assertEquals(mockDatabase, getDayFromMongoDB.getMongoDatabase());
    }


    /* 
    @Test
    @DisplayName("Get specific day from database")
    public void testGetSpecific() throws Exception {

        when(mockCollection.find(WeekQuery)).thenReturn(null)





        

        
    }*/


   





    
}
