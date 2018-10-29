
public class LRU implements Algorithms{

	static int getNewFrame(PageTableEntry[] pageTableEntriesArray, int processID, int pageID, boolean isNewProcess) {
		int leastRecentReferenceTime = Integer.MAX_VALUE;
        int leastRecentReferenceIndex = -1;
        int freePageCount = 0;
        int firstFreePageIndex = -1;

        for (int i = pageTableEntriesArray.length - 1; i >= 0; i--) {
            if (pageTableEntriesArray[i].used) {
                if (pageTableEntriesArray[i].processID == processID) {
                    if (pageTableEntriesArray[i].pageID == pageID) {
                        return i;
                    }
                }
                if (leastRecentReferenceTime > pageTableEntriesArray[i].lastReferenceTime) {
                    leastRecentReferenceIndex = i;
                    leastRecentReferenceTime = pageTableEntriesArray[i].lastReferenceTime;
                }
            }else {
                freePageCount += 1;
                firstFreePageIndex = i;
            }
        }

        if (isNewProcess) {
            if (freePageCount >= 4) {
                return firstFreePageIndex;
            }else {
                return -1;
            }
        }else {
            if (freePageCount > 0) {
                return firstFreePageIndex;
            }else {
                return leastRecentReferenceIndex;
            }
        }
	}
}

