package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueInserter;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueStateChecker;

public class ActivityQueHandler implements Runnable {
    public ObjectQueStateChecker stateChecker;
    public ObjectQueInserter inserter;

    public ActivityQueHandler(ObjectQueStateChecker stateChecker, ObjectQueInserter inserter) {
        this.stateChecker = stateChecker;
        this.inserter = inserter;
    }

    public void run() {
        stateChecker.checkQueState();
        try {
            inserter.checkNullsAndAddToList();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
