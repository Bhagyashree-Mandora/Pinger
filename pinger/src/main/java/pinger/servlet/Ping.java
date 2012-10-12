package pinger.servlet;

public class Ping {
	private final String arrivedAt;
	private final long processingTime;
	
	public Ping(String arrivedAt, long processingTime){
		this.arrivedAt = arrivedAt;
		this.processingTime = processingTime;
	}

	public String getArrivedAt() {
		return arrivedAt;
	}

	public long getProcessingTime() {
		return processingTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((arrivedAt == null) ? 0 : arrivedAt.hashCode());
		result = prime * result
				+ (int) (processingTime ^ (processingTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ping other = (Ping) obj;
		if (arrivedAt == null) {
			if (other.arrivedAt != null)
				return false;
		} else if (!arrivedAt.equals(other.arrivedAt))
			return false;
		if (processingTime != other.processingTime)
			return false;
		return true;
	}
	
	
}
