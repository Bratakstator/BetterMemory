package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

public interface ObjectQue<T, U> {
    int addToQue(T listOrMapObject);
    U getFirstFromQueAsDTO();
    boolean containsElements();
}
