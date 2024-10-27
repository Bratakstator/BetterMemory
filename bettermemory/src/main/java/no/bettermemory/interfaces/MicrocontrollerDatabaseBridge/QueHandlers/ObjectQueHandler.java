package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers;

public interface ObjectQueHandler<T> {
    void run();
    void checkNullsAndAddToList() throws Exception;
}
