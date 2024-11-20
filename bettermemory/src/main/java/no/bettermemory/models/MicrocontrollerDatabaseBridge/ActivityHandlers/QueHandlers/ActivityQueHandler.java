package no.bettermemory.models.MicrocontrollerDatabaseBridge.ActivityHandlers.QueHandlers;

import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueInserter;
import no.bettermemory.interfaces.MicrocontrollerDatabaseBridge.QueHandlers.ObjectQueStateChecker;

/**
 * This class is the main runnable class that keeps the activity queue updated in real-time.
 * It continuously checks the state of the queue, processes completed or outdated activities, 
 * and retrieves new activities to fill available slots. The class ensures that the queue remains 
 * synchronized with external data sources.
 * 
 * @param stateChecker - Responsible for examining the current state of the queue, handling concluded 
 * or outdated activities, and removing them from the queue as necessary.
 * @param inserter - Responsible for adding new activities into available (null) slots in the queue.
 * @param running - A flag that ensures the thread runs continuously until it is explicitly stopped. 
 * Used to gracefully terminate the loop when needed.
 * 
 * @author Joakim Klemsdal Bøe
 *
 * @code
 * Example usage:
 * <pre>{@code
 * ObjectQueStateChecker stateChecker = new YourStateCheckerImplementation();
 * ObjectQueInserter inserter = new YourInserterImplementation();
 * ActivityQueHandler queHandler = new ActivityQueHandler(stateChecker, inserter);
 * Thread handlerThread = new Thread(queHandler);
 * handlerThread.start();
 * 
 * // Stop the handler after some time or based on a condition
 * queHandler.stop();
 * handlerThread.join();
 * }</pre>
 */
public class ActivityQueHandler implements Runnable {
    public ObjectQueStateChecker stateChecker;
    public ObjectQueInserter inserter;
    boolean running = true;

    public ActivityQueHandler(ObjectQueStateChecker stateChecker, ObjectQueInserter inserter) {
        this.stateChecker = stateChecker;
        this.inserter = inserter;
    }

    /**
     * The main execution loop of the queue handler. This loop:
     * 1. Checks the state of the activity queue using the state checker.
     * 2. Attempts to insert new activities into available slots using the inserter.
     * 3. Pauses briefly between iterations to avoid overloading the system.
     * 
     * If an exception occurs during the insertion process, it is logged to the error output.
     * The loop runs until the `running` flag is set to false or the thread is interrupted.
     */
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            stateChecker.checkQueState();// Check and process the state of the queue.
            try {
                inserter.checkNullsAndAddToList();// Insert new activities into the queue.
            } catch (Exception e) {
                System.err.println(e);// Log any errors during the insertion process.
            }
            try {
                Thread.sleep(500);// Sleep for 500ms to reduce system load.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Handle thread interruption gracefully.
                e.printStackTrace();
                break;
            }
        }
    }
     /**
     * Stops the execution of the queue handler by setting the `running` flag to false.
     * This allows the loop in the `run` method to terminate gracefully.
     */
    public void stop() {
        running = false;
    }
}
