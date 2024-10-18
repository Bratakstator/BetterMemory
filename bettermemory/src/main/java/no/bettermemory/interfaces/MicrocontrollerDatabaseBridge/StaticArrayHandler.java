package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

public interface StaticArrayHandler<T, U> {
    void arrayShift();
    void addToArray(T t) throws Exception;
    U getValueFromIndex(int index) throws IndexOutOfBoundsException;
    boolean hasNull();
    int getFirstNull() throws Exception;
}
