package application.frames.utils;

public enum FrameType {
	
	TODO_FRAME("ToDo Liste"),
	CALENDAR_FRAME("ToDo Kalender");
	
	private String name;
	
	private FrameType(String n) {
		this.name = n;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
