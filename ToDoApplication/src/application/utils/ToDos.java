package application.utils;

import javafx.scene.control.CheckBox;

public class ToDos {
	
	private CheckBox cb;
	
	private String information;
	private ToDoPrio prio;
	private DeadLine deadline;
	
	public ToDos(String info, ToDoPrio prio, String deadLine) {
		this.cb = new CheckBox();
		this.information = info;
		this.prio = prio;
		this.deadline = new DeadLine(deadLine);		
	}

	public CheckBox getCb() {
		return cb;
	}
	
	public String getInformation() {
		return information;
	}
	
	public ToDoPrio getPrio() {
		return prio;
	}
	
	public String getPrioAsString() {
		return prio.toString();
	}
	
	public DeadLine getDeadline() {
		return deadline;
	}
	
	public String getDeadlineAsString() {
		return deadline.getDeadLine();
	}
}
