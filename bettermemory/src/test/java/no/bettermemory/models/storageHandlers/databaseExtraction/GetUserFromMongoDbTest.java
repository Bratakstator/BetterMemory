package no.bettermemory.models.storageHandlers.databaseExtraction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import no.bettermemory.models.users.Patient;
import no.bettermemory.tools.DatabaseConnections;

@ExtendWith(MockitoExtension.class)
public class GetUserFromMongoDbTest {
    // Database mocks
    @Mock
    private MongoClient mockClient;
    @Mock
    private MongoDatabase mockDatabase;
    @Mock
    private MongoCollection<Document> mockCollection;

    // Non-mocks
    private GetUserFromMongoDb getUserFromMongoDb;
    // Patient
    private String patientId = "12904568";
    private String firstName = "John";
    private String surname = "Doe";
    // Relative
    private String relativeId = "12904568-1";
    private String relativeFirstName = "Jane";
    private String relativeSurname = "Doe";

    // Mocks for method returns
    @Mock
    private FindIterable<Document> mockFindIterable;
    @Mock
    private Document mockResult;
    @Mock
    private List<Document> mockList;
    @Mock
    private Iterator<Document> mockIterator;
    @Mock
    private Document mockCloseRelativeDocument;

    @BeforeEach
    void setup() {
        when(DatabaseConnections.getUsersDatabase(mockClient)).thenReturn(mockDatabase);
        getUserFromMongoDb = new GetUserFromMongoDb(mockClient);
    }
    
    @Test
    public void getPatientExistInDatabaseWithoutRelatives() throws Exception {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.getString("_id")).thenReturn(patientId);
        when(mockResult.getString("first_name")).thenReturn(firstName);
        when(mockResult.getString("surname")).thenReturn(surname);

        when(mockResult.containsKey("close_relatives")).thenReturn(false);

        // Act
        Patient patient = getUserFromMongoDb.getPatient(patientId);

        // Assert
        assertEquals(patientId, patient.getPatientId());
        assertEquals(firstName, patient.getFirstName());
        assertEquals(surname, patient.getSurname());
    }

    @Test
    public void getPatientExistInDatabaseWithRelatives() throws Exception {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.getString("_id")).thenReturn(patientId);
        when(mockResult.getString("first_name")).thenReturn(firstName);
        when(mockResult.getString("surname")).thenReturn(surname);

        when(mockResult.containsKey("close_relatives")).thenReturn(true);
        when(mockResult.get("close_relatives")).thenReturn(mockList);

        when(mockList.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(mockCloseRelativeDocument);

        when(mockCloseRelativeDocument.getString("relative_id")).thenReturn(relativeId);
        when(mockCloseRelativeDocument.getString("first_name")).thenReturn(relativeFirstName);
        when(mockCloseRelativeDocument.getString("surname")).thenReturn(relativeSurname);


        // Act
        Patient patient = getUserFromMongoDb.getPatient(patientId);

        // Assert
        assertEquals(patientId, patient.getPatientId());
        assertEquals(firstName, patient.getFirstName());
        assertEquals(surname, patient.getSurname());
        assertEquals(1, patient.getCloseRelatives().size());
    }

    @Test
    public void getPatientDoesNotExist() {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> getUserFromMongoDb.getPatient(patientId)
        );

        assertEquals("No patient was found.", exception.getMessage());
    }
}
