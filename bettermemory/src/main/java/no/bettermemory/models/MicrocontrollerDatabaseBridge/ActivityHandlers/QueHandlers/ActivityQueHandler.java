package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueInserter;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueStateChecker;

public class ActivityQueHandler implements Runnable {
    public ObjectQueStateChecker stateChecker;
    public ObjectQueInserter inserter;
    boolean running = true;

    public ActivityQueHandler(ObjectQueStateChecker stateChecker, ObjectQueInserter inserter) {
        this.stateChecker = stateChecker;
        this.inserter = inserter;
    }

    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            stateChecker.checkQueState();
            try {
                inserter.checkNullsAndAddToList();
            } catch (Exception e) {
                System.err.println(e);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
