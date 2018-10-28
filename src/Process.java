
public class Process {
	String id;
	int size;
	int arrivalTime;
	int serviceDuration;


	public Process(String id) {
		this.id = id;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public void setServiceDuration(int serviceDuration) {
		this.serviceDuration = serviceDuration;
	}
	
	public String getId() {
		return this.id;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getserviceDuration() {
		return this.serviceDuration;
	}
	
	public String getProcessInfo() {
		return "Id: "+this.getId()+" Size: "+this.getSize()+" Arrival Time: "+this.getArrivalTime()+" Service Duration: "+this.getserviceDuration();
	}
}

