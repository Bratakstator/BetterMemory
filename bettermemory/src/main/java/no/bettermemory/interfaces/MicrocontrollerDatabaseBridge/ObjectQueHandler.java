package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

import java.util.Map;

public interface ObjectQueHandler<T> {
    void run();
    T[] containerElementToDTOConverter(Map<?, ?> objectMap);
    void checkNullsAndAddToList();
}
