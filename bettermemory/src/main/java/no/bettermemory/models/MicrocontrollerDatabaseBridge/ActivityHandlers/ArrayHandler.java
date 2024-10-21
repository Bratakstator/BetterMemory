package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import com.mongodb.Function;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;

public abstract class ArrayHandler<T> implements StaticContainerHandler<T> {
    T[] array;

    public ArrayHandler(T[] array) {
        this.array = array;
    }

    public int add(T t) throws Exception {
        return 0;
    }

    public int addAtIndex(int index, T t) throws IndexOutOfBoundsException {
        return 0;
    }

    public T[] nullShiftRight() {
        for (int index = 0; index < array.length; index++) {
            if (array[index] == null) {
                for (int ahead = index + 1; ahead < array.length; ahead++) {
                    if (array[ahead] != null) {
                        array[index] = array[ahead];
                        array[ahead] = null;
                    }
                }
            }
        }
        return array;
    }

    public boolean hasNulls() {
        for (T t : array) if (t == null) return true;
        return false;
    }

    public int getFirstNullIndex() throws Exception {
        return 0;
    }

    public T get(int index) throws IndexOutOfBoundsException {
        return array[index];
    }

    public <R> R getAttributeOf(int index, Function<T, R> toReturn) throws IndexOutOfBoundsException {
        return null;
    }
}
