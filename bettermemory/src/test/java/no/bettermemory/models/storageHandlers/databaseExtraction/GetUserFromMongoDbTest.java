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

import no.bettermemory.models.users.CloseRelative;
import no.bettermemory.models.users.HealthCarePersonnel;
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
    private String patientId = "129045";
    private String patientFirstName = "John";
    private String patientSurname = "Doe";
    // Relative
    private String relativeId = "12904568-1";
    private String relativeFirstName = "Jane";
    private String relativeSurname = "Doe";
    // Healthcare personnel
    private String employeeNumber = "135620";
    private String employeeFirstName = "Ola";
    private String employeeSurname = "Nordmann";

    // Mocks for method returns
    @Mock
    private FindIterable<Document> mockFindIterable;
    @Mock
    private Document mockResult;
    @Mock
    private List<Document> mockDocumentList;
    @Mock
    private Iterator<Document> mockDocumentIterator;
    @Mock
    private Document mockCloseRelativeDocument;
    @Mock
    private List<String> mockStringList;
    @Mock
    private Iterator<String> mockStringIterator;

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
        when(mockResult.getString("first_name")).thenReturn(patientFirstName);
        when(mockResult.getString("surname")).thenReturn(patientSurname);

        when(mockResult.containsKey("close_relatives")).thenReturn(false);

        // Act
        Patient patient = getUserFromMongoDb.getPatient(patientId);

        // Assert
        assertEquals(patientId, patient.getPatientId());
        assertEquals(patientFirstName, patient.getFirstName());
        assertEquals(patientSurname, patient.getSurname());
        assertEquals(0, patient.getCloseRelatives().size());
    }

    @Test
    public void getPatientExistInDatabaseWithRelatives() throws Exception {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.getString("_id")).thenReturn(patientId);
        when(mockResult.getString("first_name")).thenReturn(patientFirstName);
        when(mockResult.getString("surname")).thenReturn(patientSurname);

        when(mockResult.containsKey("close_relatives")).thenReturn(true);
        when(mockResult.get("close_relatives")).thenReturn(mockDocumentList);

        when(mockDocumentList.iterator()).thenReturn(mockDocumentIterator);
        when(mockDocumentIterator.hasNext()).thenReturn(true, false);
        when(mockDocumentIterator.next()).thenReturn(mockCloseRelativeDocument);

        when(mockCloseRelativeDocument.getString("relative_id")).thenReturn(relativeId);
        when(mockCloseRelativeDocument.getString("first_name")).thenReturn(relativeFirstName);
        when(mockCloseRelativeDocument.getString("surname")).thenReturn(relativeSurname);


        // Act
        Patient patient = getUserFromMongoDb.getPatient(patientId);

        // Assert
        assertEquals(patientId, patient.getPatientId());
        assertEquals(patientFirstName, patient.getFirstName());
        assertEquals(patientSurname, patient.getSurname());
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

    @Test
    public void getHealthCarePersonnelWithoutPatients() throws Exception {
        // Arrange
        when(DatabaseConnections.getHealthCarePersonnelCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.getString("_id")).thenReturn(employeeNumber);
        when(mockResult.getString("first_name")).thenReturn(employeeFirstName);
        when(mockResult.getString("surname")).thenReturn(employeeSurname);

        when(mockResult.containsKey("connected_patients")).thenReturn(false);

        // Act
        HealthCarePersonnel healthCarePersonnel = getUserFromMongoDb.getHealthCarePersonnel(employeeNumber);

        // Assert
        assertEquals(employeeNumber, healthCarePersonnel.getEmployeeNumber());
        assertEquals(employeeFirstName, healthCarePersonnel.getFirstName());
        assertEquals(employeeSurname, healthCarePersonnel.getSurname());
        assertEquals(0, healthCarePersonnel.getConnectedPatients().size());
    }

    @Test
    public void getHealthCarePersonnelWithPatients() throws Exception {
        // Arrange
        when(DatabaseConnections.getHealthCarePersonnelCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.getString("_id")).thenReturn(employeeNumber);
        when(mockResult.getString("first_name")).thenReturn(employeeFirstName);
        when(mockResult.getString("surname")).thenReturn(employeeSurname);

        when(mockResult.containsKey("connected_patients")).thenReturn(true);
        when(mockResult.get("connected_patients")).thenReturn(mockStringList);

        when(mockStringList.iterator()).thenReturn(mockStringIterator);
        when(mockStringIterator.hasNext()).thenReturn(true, false);
        when(mockStringIterator.next()).thenReturn(patientId);

        // Act
        HealthCarePersonnel healthCarePersonnel = getUserFromMongoDb.getHealthCarePersonnel(employeeNumber);

        // Assert
        assertEquals(employeeNumber, healthCarePersonnel.getEmployeeNumber());
        assertEquals(employeeFirstName, healthCarePersonnel.getFirstName());
        assertEquals(employeeSurname, healthCarePersonnel.getSurname());
        assertEquals(1, healthCarePersonnel.getConnectedPatients().size());
    }

    @Test
    public void getHealthCarePersonnelDoesNotExist() {
        // Arrange
        when(DatabaseConnections.getHealthCarePersonnelCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> getUserFromMongoDb.getHealthCarePersonnel(employeeNumber)
        );

        assertEquals("No health care personnel was found.", exception.getMessage());
    }

    @Test
    public void getCloseRelativeExists() throws Exception {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.containsKey("close_relatives")).thenReturn(true);
        when(mockResult.get("close_relatives")).thenReturn(mockDocumentList);

        when(mockDocumentList.iterator()).thenReturn(mockDocumentIterator);
        when(mockDocumentIterator.hasNext()).thenReturn(true, false);
        when(mockDocumentIterator.next()).thenReturn(mockCloseRelativeDocument);

        when(mockCloseRelativeDocument.containsValue(anyString())).thenReturn(true);

        when(mockCloseRelativeDocument.getString("relative_id")).thenReturn(relativeId);
        when(mockCloseRelativeDocument.getString("surname")).thenReturn(relativeSurname);

        // Act
        CloseRelative closeRelative = getUserFromMongoDb.getCloseRelative(patientId, relativeFirstName);

        // Assert
        assertEquals(relativeId, closeRelative.getId());
        assertEquals(relativeFirstName, closeRelative.getFirstName());
        assertEquals(relativeSurname, closeRelative.getSurname());
    }

    @Test
    public void getCloseRelativeWhenNoRelativeWithGivenName() {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.containsKey("close_relatives")).thenReturn(true);
        when(mockResult.get("close_relatives")).thenReturn(mockDocumentList);

        when(mockDocumentList.iterator()).thenReturn(mockDocumentIterator);
        when(mockDocumentIterator.hasNext()).thenReturn(true, false);
        when(mockDocumentIterator.next()).thenReturn(mockCloseRelativeDocument);

        when(mockCloseRelativeDocument.containsValue(anyString())).thenReturn(false);

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> getUserFromMongoDb.getCloseRelative(patientId, relativeFirstName)
        );

        // Assert
        assertEquals("The patient has no close relatives with the provided name.", exception.getMessage());
    }

    @Test
    public void getCloseRelativeCouldNotConvertToDocument() {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.containsKey("close_relatives")).thenReturn(true);
        when(mockResult.get("close_relatives")).thenReturn(mockStringList);

        when(mockStringList.iterator()).thenReturn(mockStringIterator);
        when(mockStringIterator.hasNext()).thenReturn(true, false);
        when(mockStringIterator.next()).thenReturn("Not a document");

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> getUserFromMongoDb.getCloseRelative(patientId, relativeFirstName)
        );

        // Assert
        assertEquals("Could not convert object to Document-type", exception.getMessage());
    }

    @Test
    public void getCloseRelativeCouldNotFindList() {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.containsKey("close_relatives")).thenReturn(true);
        when(mockResult.get("close_relatives")).thenReturn("Not a list");

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> getUserFromMongoDb.getCloseRelative(patientId, relativeFirstName)
        );

        // Assert
        assertEquals("No close relative list was found.", exception.getMessage());
    }

    @Test
    public void getCloseRelativePatientHasNoRelatives() {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockResult);

        when(mockResult.containsKey("close_relatives")).thenReturn(false);

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> getUserFromMongoDb.getCloseRelative(patientId, relativeFirstName)
        );

        // Assert
        assertEquals("The patient has no close relatives", exception.getMessage());
    }

    @Test
    public void getCloseRelativeNoPatientFound() {
        // Arrange
        when(DatabaseConnections.getPatientCollection(mockDatabase)).thenReturn(mockCollection);
        when(mockCollection.find(any(Document.class))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);

        // Act
        Exception exception = assertThrows(
            Exception.class,
            () -> getUserFromMongoDb.getCloseRelative(patientId, relativeFirstName)
        );

        // Assert
        assertEquals("No patient was found.", exception.getMessage());
    }
}
