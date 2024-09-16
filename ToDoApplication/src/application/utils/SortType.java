package application.utils;

public enum SortType {
	
	BY_PRIO("Priorität"),
	BY_DEADLINE("Deadline");
	
	private String name;
	
	private SortType(String n) {
		this.name = n;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
