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
	
	public static ToDoPrio convertInt(int i) {
		ToDoPrio prio = switch (i) {
			case 1 -> ToDoPrio.LOWEST;
			case 2 -> ToDoPrio.LOW;
			case 3 -> ToDoPrio.MIDDLE;
			case 4 -> ToDoPrio.HIGH;
			case 5 -> ToDoPrio.HIGHEST;
			default -> null;
		};
		return prio;
	}
	
	public static ToDoPrio convertString(String input) {
		ToDoPrio prio = switch (input.toLowerCase()) {
			case "höchste" -> ToDoPrio.HIGHEST;
			case "hoch" -> ToDoPrio.HIGH;
			case "mittel" -> ToDoPrio.MIDDLE;
			case "niedrig" -> ToDoPrio.LOW;
			case "niedrigste" -> ToDoPrio.LOWEST;
			default -> null;
		};
		return prio;
	}
}
