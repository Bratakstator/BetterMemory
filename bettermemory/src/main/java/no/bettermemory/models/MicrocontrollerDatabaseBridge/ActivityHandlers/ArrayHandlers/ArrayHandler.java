package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.ArrayHandlers;

import com.mongodb.Function;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;

public abstract class ArrayHandler<T> implements StaticContainerHandler<T> {
    T[] array;

    public ArrayHandler(T[] array) {
        this.array = array;
    }

    public synchronized int add(T t) throws Exception {
        try {
            int index = getFirstNullIndex();
            array[index] = t;
            return index;
        } catch (Exception e) {
            throw new Exception("No more space in array.");
        }
    }

    public synchronized int addAtIndex(int index, T t) throws IndexOutOfBoundsException {
        try {
            array[index] = t;
            return index;
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    public synchronized StaticContainerHandler<T> nullShiftRight() {
        for (int index = 0; index < array.length; index++) {
            if (array[index] == null) {
                for (int ahead = index + 1; ahead < array.length; ahead++) {
                    if (array[ahead] != null) {
                        array[index] = array[ahead];
                        array[ahead] = null;
                        break;
                    }
                }
            }
        }
        return this;
    }

    public synchronized boolean hasNulls() {
        for (T t : array) if (t == null) return true;
        return false;
    }

    public synchronized int getFirstNullIndex() throws Exception {
        for (int index = 0; index < array.length; index++) if(array[index] == null) return index;
        throw new Exception("No nulls in array.");
    }

    public synchronized T get(int index) throws IndexOutOfBoundsException {
        try {
            return array[index];
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    public synchronized <R> R getAttributeOf(int index, Function<T, R> toReturn) throws IndexOutOfBoundsException {
        try {
            return toReturn.apply(array[index]);
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    public synchronized int length() {
        return array.length;
    }

    public synchronized T[] getArray() {
        return array;
    }

    public synchronized void setArray(T[] array) {
        this.array = array;
    }
}
