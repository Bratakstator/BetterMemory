package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers;

import com.mongodb.Function;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.StaticContainerHandler;

public abstract class ArrayHandler<T> implements StaticContainerHandler<T> {
    T[] array;

    public ArrayHandler(T[] array) {
        this.array = array;
    }

    public int add(T t) throws Exception {
        try {
            int index = getFirstNullIndex();
            array[index] = t;
            return index;
        } catch (Exception e) {
            throw new Exception("No more space in array.");
        }
    }

    public int addAtIndex(int index, T t) throws IndexOutOfBoundsException {
        try {
            array[index] = t;
            return index;
        } catch (IndexOutOfBoundsException e) {
            int diff = index - array.length;
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    public T[] nullShiftRight() {
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
        return array;
    }

    public boolean hasNulls() {
        for (T t : array) if (t == null) return true;
        return false;
    }

    public int getFirstNullIndex() throws Exception {
        for (int index = 0; index < array.length; index++) if(array[index] == null) return index;
        throw new Exception("No nulls in array.");
    }

    public T get(int index) throws IndexOutOfBoundsException {
        try {
            return array[index];
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    public <R> R getAttributeOf(int index, Function<T, R> toReturn) throws IndexOutOfBoundsException {
        try {
            return toReturn.apply(array[index]);
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    public T[] getArray() {
        return array;
    }

    public void setArray(T[] array) {
        this.array = array;
    }
}
