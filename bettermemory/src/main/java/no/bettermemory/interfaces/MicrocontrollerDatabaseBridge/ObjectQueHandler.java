package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

import java.util.Map;

public interface ObjectQueHandler<T, U> {
    void run();
    T[] containerElementToDTOConverter(U u);
    void checkNullsAndAddToList() throws Exception;
}
