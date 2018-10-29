
public class FIFO implements Algorithms  {
	
	 public int getNewFrame(PageTableEntry[] pageTableEntriesArray, int processID, int pageID, boolean isNewProcess){
	        int freePageCount = 0;
	        int firstFreePageIndex = -1;

	        int replacementIndex = -1;

	        for (int i = pageTableEntriesArray.length - 1; i >= 0; i--) {
	            if (pageTableEntriesArray[i].used) {
	                if (pageTableEntriesArray[i].processID == processID) {
	                    if (pageTableEntriesArray[i].pageID == pageID) {
	                        return i;
	                    }
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
	                if(replacementIndex == pageTableEntriesArray.length - 1){
	                    replacementIndex = -1;
	                }
	                replacementIndex+=1;
	                return replacementIndex;
	            }
	        }
	}
}
