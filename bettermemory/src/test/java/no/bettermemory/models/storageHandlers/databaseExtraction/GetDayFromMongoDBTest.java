package no.bettermemory.models.storageHandlers.databaseExtraction;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.tools.DatabaseConnections;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetDayFromMongoDBTest {

    @Mock
    private  MongoClient mockMongoClient;

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockCollection;

    @Mock
    private DatabaseConnections mockDatabaseConnections;

    private GetDayFromMongoDB getDayFromMongoDB;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        PowerMockito.mockStatic(DatabaseConnections.class);
        when(DatabaseConnections.getUsersDatabase(mockMongoClient)).thenReturn(mockDatabase);
        getDayFromMongoDB = new GetDayFromMongoDB(any(MongoClient.class));
    }

    
    @Test
    @DisplayName("Test constructor for GetDayFromMongoDB")
    public void testGetDayFromMongoDB() {
        assertNotNull(getDayFromMongoDB);
        assertEquals(mockMongoClient, getDayFromMongoDB.getClient());


    }

    @Test
    @DisplayName("Get specific day from database")
    public void testGetSpecific() {
        
    }


   





    
}
