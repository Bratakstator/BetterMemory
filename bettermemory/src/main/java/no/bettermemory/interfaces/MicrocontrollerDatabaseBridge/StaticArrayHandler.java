package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

public interface StaticArrayHandler<T> {
    void arrayShift();
    void addToArray(T t) throws Exception;
    boolean hasNull();
    int getFirstNull() throws Exception;
}
