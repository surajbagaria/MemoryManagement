
public class PageTableEntry {

	boolean used;
    int processID;
    int pageID;
    int frameID;
    int lastReferenceTime;
    int frequency;

    public PageTableEntry() {
        this.used = false;
        processID = -1;
        pageID = -1;
        lastReferenceTime = -1;
        frequency = 0;
    }


    public String toString() {
        return String.format(
                "{u: %b, pid: %d, pgid: %d, lrt: %d}", this.used, this.processID, this.pageID, this.lastReferenceTime
        );
}
	
}
