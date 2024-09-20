package application.utils;

import java.util.Random;

import javafx.scene.control.CheckBox;

public class ToDos {
	
	private CheckBox cb;
	
	private int id;
	private int sel;
	private String information;
	private ToDoPrio prio;
	private DeadLine deadline;
	
	public ToDos(int id, int sel, String info, ToDoPrio prio, String deadLine) {
		this.cb = new CheckBox();
		this.id = id;
		this.sel = sel;
		switch (this.sel) {
			case 0 -> this.cb.setSelected(false);
			case 1 -> this.cb.setSelected(true);	
		}
		this.information = info;
		this.prio = prio;
		this.deadline = new DeadLine(deadLine);
	}
	
	public ToDos(int sel, String info, ToDoPrio prio, String deadLine) {
		this.cb = new CheckBox();
		this.id = new Random().nextInt(0, 5000);
		this.sel = sel;
		switch (this.sel) {
			case 0 -> this.cb.setSelected(false);
			case 1 -> this.cb.setSelected(true);
		}
		this.information = info;
		this.prio = prio;
		this.deadline = new DeadLine(deadLine);		
	}

	public CheckBox getCb() {
		return cb;
	}
	
	public int getId() {
		return id;
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
	
	public int getSelection() {
		return sel;
	}
	
	public void setInfo(String input) {
		this.information = input;
	}
	
	public void setPrio(ToDoPrio input) {
		this.prio = input;
	}
	
	public void setDealine(String deadline) {
		this.deadline = new DeadLine(deadline);
	}
}
