package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

public interface StaticContainerHandler<T> {
    int add(T t) throws Exception;
    int addAtIndex(int index, T t) throws IndexOutOfBoundsException;
    T[] nullShiftRight();
    boolean hasNulls();
    int getFirstNullIndex() throws Exception;
    T get(int index) throws IndexOutOfBoundsException;
}
