
public class MemorySimulation {
	public static void main(String[] args) {
        ProcessFactory workLoad = new ProcessFactory();
        workLoad.printQueue(workLoad.arrivalQueue);

        PageTable table = new PageTable();


        System.out.print("FIFO algorithm:\n");
        //runAlgorithm(new FIFO(), workLoad, table);
        System.out.print("LRU algorithm:\n");
        runAlgorithm(new LRU(), workLoad, table);
        System.out.print("LFU algorithm:\n");
        //runAlgorithm(new LFU(), workLoad, table);
        System.out.print("MFU algorithm:\n");
        //runAlgorithm(new MFU(), workLoad, table);
        System.out.print("Random Pick algorithm:\n");
        //runAlgorithm(new RandomPick(), workLoad, table);

    }

    static double runAlgorithm(Algorithms algorithm, ProcessFactory workLoad, PageTable pageTable) {
        double hitMissRatio = 0;
        pageTable.setAlgorithms(algorithm);
        System.out.print("-------------------------------------\n");
        for (int i = 0; i < 5; i++) {
        	workLoad.run(pageTable);
            hitMissRatio += (double)pageTable.hit / (double)pageTable.miss;
            pageTable.cleanPageTable();
            System.out.print("-------------------------------------\n");
        }

        System.out.print("average hit miss ratio is ");
        System.out.printf("%.4f\n", hitMissRatio / 5);
        System.out.print("***********************************************\n\n\n");
        return hitMissRatio / 5;
}
}
