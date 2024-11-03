package no.bettermemory.models.storageHandlers.databaseExtraction;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
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

    private GetDayFromMongoDB getDayFromMongoDB;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getDayFromMongoDB = new GetDayFromMongoDB(any(MongoClient.class));
    }

    
    @Test
    @DisplayName("Get specific day from database")
    public void testGetSpecificDay() {
        
    }


   





    
}
