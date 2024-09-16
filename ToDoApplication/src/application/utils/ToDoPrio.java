package application.utils;

public enum ToDoPrio {
	
	HIGHEST(5, "Höchste"),
	HIGH(4, "Hoch"),
	MIDDLE(3, "Mittel"),
	LOW(2, "Niedrig"),
	LOWEST(1, "Niedrigste");
	
	private int prio;
	private String name;
	
	private ToDoPrio(int prio, String n) {
		this.prio = prio;
		this.name = n;
	}
	
	public int getPrioAsInt() {
		return prio;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
