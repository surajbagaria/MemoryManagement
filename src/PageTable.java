
public class PageTable {

	private PageTableEntry[] entries;
    private Algorithms algorithm;
    private int referenceCount = 0;

    public int hit = 0;
    public int miss = 0;

    public void setAlgorithms(Algorithms algorithm) {
        this.algorithm = algorithm;
    }
    public void cleanPageTable() {
        for (PageTableEntry page: entries) {
            page.used = false;
        }
        hit = 0;
        miss = 0;
        referenceCount = 0;
    }

    public PageTable() {
        this.entries = new PageTableEntry[100];
        for (int i = 0; i < 100; i++) {
            this.entries[i] = new PageTableEntry();
        }
    }

    public int numFreeFrames() {
        int numFree = 0;

        for (PageTableEntry entry : entries) {
           if (!entry.used) numFree++;
        }

        return numFree;
    }

    public void freeFramesForProcess(Process process) {
        for (PageTableEntry entry : entries) {
            if (entry.processID == process.id) entry.used = false;
        }
    }

    public boolean pageExistsInTable(int processID, int pageID) {
        for (PageTableEntry entry : entries) {
            if (entry.processID == processID && entry.pageID == pageID) return true;
        }
        return false;
    }


    public int getFrameFor(int processID, int pageID, int time, boolean isNewProcess) {
        int newFrameNumber = Algorithms.getNewFrame(entries, processID, pageID, isNewProcess);
        referenceCount += 1;
        double currentTime = (double)time / 10.0;
        if (referenceCount>=200 && referenceCount < 300) {
            System.out.printf("At time: %.1f, process %s, page %d gets referenced, ", currentTime, Process.intToStringID(processID), pageID);
        }
        if (newFrameNumber == -1) {
            if (referenceCount>=200 && referenceCount < 300) {
                System.out.printf("not enough free frames, page request rejected.\n");
            }
            return -1;
        }
        if (entries[newFrameNumber].processID == processID && entries[newFrameNumber].pageID == pageID) {
            if (referenceCount>=200 && referenceCount < 300) {
                System.out.printf("page already in memory, ");
            }
            hit += 1;
            entries[newFrameNumber].frequency += 1;
        }else if (!entries[newFrameNumber].used) {
            if (referenceCount>=200 && referenceCount < 300) {
                System.out.printf("assigned a free memory frame, ");
            }
            entries[newFrameNumber].frequency = 1;
//            miss += 1;
        }else {
            if (referenceCount>=200 && referenceCount < 300) {
                System.out.printf("process %s, page %d gets swapped out, ", Process.intToStringID(entries[newFrameNumber].processID), entries[newFrameNumber].pageID);
            }
            entries[newFrameNumber].frequency = 1;
            miss += 1;
        }
        if (referenceCount>=200 && referenceCount < 300) {
            System.out.printf("memory frame number is %d.\n", newFrameNumber);
        }
        entries[newFrameNumber].used = true;
        entries[newFrameNumber].pageID = pageID;
        entries[newFrameNumber].processID = processID;
        entries[newFrameNumber].lastReferenceTime = time;
        return newFrameNumber;
    }

    public void print() {
        System.out.print("[ ");
        for (PageTableEntry entry : this.entries) {
            if (entry == null || entry.used == false) {
                System.out.print("-- ");
            } else {
                System.out.print(Process.intToStringID(entry.processID) + " ");
            }
        }
        System.out.println("]");
}
	
	
}
