package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

public interface ObjectQueHandler<T, U> {
    void run();
    T[] containerElementToDTOConverter(U u);
    void checkNullsAndAddToList() throws Exception;
}
