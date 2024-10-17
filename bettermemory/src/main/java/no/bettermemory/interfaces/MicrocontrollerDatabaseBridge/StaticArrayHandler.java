package no.bettermemory.interfaces.MicrocontrollerDatabaseBridge;

public interface StaticArrayHandler {
    void arrayShift();
    void addToArray();
    boolean hasNull();
    int getFirstNull() throws Exception;
}
