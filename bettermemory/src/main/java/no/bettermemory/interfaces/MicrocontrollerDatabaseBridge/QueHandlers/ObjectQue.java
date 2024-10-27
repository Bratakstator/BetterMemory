package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers;

public interface ObjectQue<T, U> {
    int addToQue(T listOrMapObject);
    U getFirstFromQueAsDTO();
    boolean containsElements();
}
