package application.frames;

import application.utils.ToDoList;
import javafx.scene.layout.BorderPane;

public class ToDoFrame extends BorderPane {
	
	private ToDoList list;
	
	private TableViewBox tvb;
	private InteractionBox ib;
	
	public ToDoFrame() {
		this.list = new ToDoList();
		
		this.tvb = new TableViewBox(this);
		this.ib = new InteractionBox(this);
		
		this.buildFrame();
	}

	public ToDoList getToDoList() {
		return list;
	}
	
	public TableViewBox getTvb() {
		return tvb;
	}
	
	private void buildFrame() {
		this.setCenter(this.tvb);
		this.setRight(this.ib);
	}
}
