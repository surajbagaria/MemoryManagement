import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class ProcessFactory {
	public static final int NUM_PROCS = 150;

    // contains processes that have not arrived yet
    public LinkedList<Process> arrivalQueue;

    // contains processes that have arrived but are waiting to run
    public LinkedList<Process> waitingQueue;

    // contains processes that are running
    public LinkedList<Process> runningQueue;

    // contains processes that have run and are completed
    public LinkedList<Process> completedQueue;

    public static LinkedList<Process> createRandomArrivalQueue() {
        LinkedList<Process> arrivalQueue = new LinkedList<Process>();
        for (int i = 0; i < NUM_PROCS; i++) {
            arrivalQueue.add(Process.createRandomProcess());
        }
        Collections.sort(arrivalQueue, new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return p1.arrivalTime - p2.arrivalTime;
            }
        });
        return arrivalQueue;
    }

    public static void printQueue(LinkedList<Process> ll) {
        System.out.println("[");
        for (Process process : ll) {
            System.out.println("\t" + process.toString());
        }
        System.out.println("[");
    }

    public ProcessFactory() {
        arrivalQueue = createRandomArrivalQueue();
        waitingQueue = new LinkedList<Process>();
        runningQueue = new LinkedList<Process>();
        completedQueue = new LinkedList<Process>();
    }

    public ProcessFactory(LinkedList<Process> arrivalQueue) {
        this.arrivalQueue = arrivalQueue;
        waitingQueue = new LinkedList<Process>();
        runningQueue = new LinkedList<Process>();
        completedQueue = new LinkedList<Process>();
    }

    public void run(PageTable pageTable) {
        for (int quantum = 0; quantum < 1000; quantum++) {
            // get arriving processes, put them in waiting queue
            for (Process process : arrivalQueue) {
                // process arrival time is 0-100, but quantum is in 100ms chunks not seconds, so convert to 100ms
                if (process.arrivalTime == quantum) {
                    waitingQueue.add(process);
                }
            }

            // check to see if we can schedule a new process (check to see if there are 4 pages free)
            // if we can schedule a new process, then add it to the running queue
            while (waitingQueue.size() > 0 && pageTable.numFreeFrames() >= 4) {
                // allocate 4 frames for the next process, add it to the running queue
                Process newProcess = waitingQueue.removeFirst();
                System.out.printf("At time: %.1f, process %s arrives\n", (double) quantum / 10.0, newProcess.sid);
                newProcess.lastUsedPage = -1;
                int frameNum = pageTable.getFrameFor(newProcess.id, 0, quantum, true);
                runningQueue.addLast(newProcess);
            }

            // run the next process in the running queue
            if (runningQueue.size() == 0) continue; // nothing to do, so lets go to the next quantum

            Process runningProcess = runningQueue.removeFirst();

            int pageID = runningProcess.nextPageID();
            pageTable.getFrameFor(runningProcess.id, pageID, quantum, false);

            runningProcess.remainingRuntime -= 1;

            if (runningProcess.remainingRuntime <= 0) {
                pageTable.freeFramesForProcess(runningProcess);
                System.out.printf("At time: %.1f, process %s finished\n", (double)(quantum + 1) / 10.0, runningProcess.sid);
                completedQueue.add(runningProcess);
            } else {
                runningQueue.addLast(runningProcess);
            }

            //pageTable.print();
        }
        double hitMissRatio = (double)pageTable.hit / (double)pageTable.miss;
        for (Process process: arrivalQueue) {
            process.remainingRuntime = process.serviceDuration;
            process.lastUsedPage = -1;
        }
        waitingQueue = new LinkedList<Process>();
        runningQueue = new LinkedList<Process>();
        completedQueue = new LinkedList<Process>();
}
}
