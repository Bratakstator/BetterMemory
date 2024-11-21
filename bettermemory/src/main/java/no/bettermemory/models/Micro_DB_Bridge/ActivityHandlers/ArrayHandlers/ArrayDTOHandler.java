package no.bettermemory.models.Micro_DB_Bridge.ActivityHandlers.ArrayHandlers;

/**
 * An implementation of the abstract class ArrayHandler.
 * Used to handle DTOs.
 */
public class ArrayDTOHandler<T> extends ArrayHandler<T> {
    public ArrayDTOHandler(T[] array) {
        super(array);
    }
}
