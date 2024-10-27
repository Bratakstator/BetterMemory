package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.ArrayHandlers;

import com.mongodb.Function;

public interface StaticContainerHandler<T> {
    int add(T t) throws Exception;
    int addAtIndex(int index, T t) throws IndexOutOfBoundsException;
    StaticContainerHandler<T> nullShiftRight();
    boolean hasNulls();
    int getFirstNullIndex() throws Exception;
    T get(int index) throws IndexOutOfBoundsException;
    <R> R getAttributeOf(int index, Function<T, R> toReturn) throws IndexOutOfBoundsException;
    int length();
}
