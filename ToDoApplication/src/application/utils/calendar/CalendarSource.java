package application.utils.calendar;

import java.util.Random;

import application.utils.ToDos;

public class CalendarSource {
	
	private int id;
	private ToDos td;
	
	public CalendarSource(ToDos td) {
		this.id = new Random().nextInt(1, 5000);
		this.td = td;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDateAsString() {
		return td.getDeadlineAsString();
	}
	
	public ToDos getToDo() {
		return td;
	}
	
	public String getInfo() {
		return td.getInformation();
	}
}
