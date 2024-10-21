package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;

public abstract class ArrayHandler<T> implements StaticContainerHandler<T> {
    T[] array;

    public ArrayHandler(T[] array) {
        this.array = array;
    }
}
