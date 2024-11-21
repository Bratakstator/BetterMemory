package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.ArrayHandlers;

import com.mongodb.Function;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers.StaticContainerHandler;

/**
 * This abstract class is used to add methods for handling arrays.
 * This abstract class contains methods to make it easier to use an array
 * as a ordered list.
 * 
 * @author Joakim Klemsdal Bøe
 */
public abstract class ArrayHandler<T> implements StaticContainerHandler<T> {
    T[] array;

    public ArrayHandler(T[] array) {
        this.array = array;
    }

    /**
     * This method adds the provided object to the first instance of null in the array.
     * If the array does not contain any null values, it will throw an exception.
     * Returns the index where the object was added.
     * 
     * @return int
     * @throws Exception
     */
    public synchronized int add(T t) throws Exception {
        try {
            int index = getFirstNullIndex();
            array[index] = t;
            return index;
        } catch (Exception e) {
            throw new Exception("No more space in array.");
        }
    }

    /**
     * Adds given object to specified index.
     * Returns the index where the object was added.
     * 
     * @return int
     * @throws IndexOutOfBoundsException
     */
    public synchronized int addAtIndex(int index, T t) throws IndexOutOfBoundsException {
        try {
            array[index] = t;
            return index;
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    /**
     * Goes through the array and moves all nulls to the back, used to maintain the order of the array.
     * Returns the updated instance of itself.
     * 
     * @return StaticContainerHandler<T>
     */
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

    /**
     * Checks if the array contains null values.
     * Returns true if it has nulls, false if it doesn't.
     * 
     * @return boolean
     */
    public synchronized boolean hasNulls() {
        for (T t : array) if (t == null) return true;
        return false;
    }

    /**
     * Returns the index of the first null instance in array.
     * If array does not have nulls, it will throw an exception.
     * 
     * @return int
     * @throws Exception
     */
    public synchronized int getFirstNullIndex() throws Exception {
        for (int index = 0; index < array.length; index++) if(array[index] == null) return index;
        throw new Exception("No nulls in array.");
    }

    /**
     * Returns the object at given index.
     * 
     * @return T
     * @throws IndexOutOfBoundsException
     */
    public synchronized T get(int index) throws IndexOutOfBoundsException {
        try {
            return array[index];
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    /**
     * Returns a given attribute of the object at given index.
     * 
     * @return the type of the given attribute.
     * @throws IndexOutOfBoundsException
     * 
     * @code
     * <pre>{@code arrayHandler.getAttributeOf(2, ExampleClass::getName);} </pre>
     */
    public synchronized <R> R getAttributeOf(int index, Function<T, R> toReturn) throws IndexOutOfBoundsException {
        try {
            return toReturn.apply(array[index]);
        } catch (IndexOutOfBoundsException e) {
            int diff = index - (array.length - 1);
            throw new IndexOutOfBoundsException("Index out of bounds by an amount: " + diff);
        }
    }

    /**
     * Returns the length of the array.
     * 
     * @return int
     */
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
