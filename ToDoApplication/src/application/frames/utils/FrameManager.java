package application.frames.utils;

import application.frames.ToDoFrame;
import application.frames.calendar.CalendarFrame;
import application.mysql.MySQL;
import application.mysql.MySQLManager;
import javafx.scene.Scene;

public class FrameManager {
	
	private MySQLManager db;
	
	private FrameType currentFrame;
	
	private SwitchButton sb;
		
	private ToDoFrame tdf;
	private CalendarFrame cf;
	
	private Scene scene;
			
	public FrameManager(MySQLManager db, double w, double h) {
		this.db = db;
		
		this.currentFrame = FrameType.TODO_FRAME;
		
		this.sb = new SwitchButton(this);
				
		this.tdf = new ToDoFrame(this);
		this.cf = new CalendarFrame(this);
						
		this.scene = new Scene(this.tdf, w, h);
	}
	
	public void switchFrame(FrameType type) {
		if(type != null && this.tdf != null && this.cf != null) {
			switch (type) {
				case FrameType.TODO_FRAME -> {
					this.scene.setRoot(this.tdf);
				}
				case FrameType.CALENDAR_FRAME -> {
					this.scene.setRoot(this.cf);
				}
			}
			this.currentFrame = type;
		}
	}
	
	public SwitchButton getSb() {
		return sb;
	}
	
	public FrameType getCurrentFrame() {
		return currentFrame;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public MySQLManager getMM() {
		return db;
	}
	
	public MySQL getDb() {
		return db.getDb();
	}
	
	public CalendarFrame getCf() {
		return cf;
	}
	
	public ToDoFrame getTdf() {
		return tdf;
	}
}
