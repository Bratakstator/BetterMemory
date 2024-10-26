package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

public interface ObjectQueHandler<T> {
    void run();
    void checkNullsAndAddToList() throws Exception;
}
