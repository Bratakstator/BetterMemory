package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import com.mongodb.Function;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;

public abstract class ArrayHandler<T> implements StaticContainerHandler<T> {
    T[] array;

    public ArrayHandler(T[] array) {
        this.array = array;
    }

    public int add(T t) {
        return 0;
    }

    public int addAtIndex(int index, T t) {
        return 0;
    }

    public T[] nullShiftRight() {
        return array;
    }

    public boolean hasNulls() {
        return false;
    }

    public int getFirstNullIndex() {
        return 0;
    }

    public T get(int index) {
        return array[index];
    }

    public <R> R getAttributeOf(int index, Function<T, R> toReturn) {
        return null;
    }
}
