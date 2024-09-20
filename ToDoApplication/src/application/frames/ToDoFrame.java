package application.frames;

import java.sql.ResultSet;

import application.frames.calendar.CalendarFrame;
import application.frames.calendar.CalendarGrid;
import application.frames.utils.FrameManager;
import application.mysql.MySQL;
import application.mysql.MySQLManager;
import application.utils.SortType;
import application.utils.ToDoList;
import application.utils.ToDoPrio;
import application.utils.ToDos;
import application.utils.Utils;
import application.utils.calendar.MonthCalendar;
import application.utils.calendar.YearCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class ToDoFrame extends BorderPane {
	
	private FrameManager fm;
	
	private ToDoList list;
	private MySQLManager db;
	
	private TableViewBox tvb;
	private InteractionBox ib;
		
	public ToDoFrame(FrameManager fm) {
		this.fm = fm;
		
		this.db = this.fm.getMM();

		this.list = new ToDoList(this.fm.getMM().readDBAsObservableList());
				
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
	
	public InteractionBox getIb() {
		return ib;
	}
	
	public FrameManager getFm() {
		return fm;
	}
	
	private void buildFrame() {
		this.setCenter(this.tvb);
		this.setRight(this.ib);
	}
	
	public MySQLManager getDb() {
		return db;
	}
}
