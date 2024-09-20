package application.frames.calendar;

import application.frames.utils.FrameManager;
import application.utils.calendar.Calendar;
import application.utils.calendar.YearCalendar;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class CalendarFrame extends BorderPane {
	
	private FrameManager fm;
	
	private Calendar calendar;
	
	private CalendarGrid cg;
	private CalendarBottom cb;
	
	public CalendarFrame(FrameManager fm) {
		this.fm = fm;
		
		this.calendar = new Calendar();
		this.calendar.generate();
		this.calendar.loadInputs(this.fm.getMM().readDBAsObservableList());
		
		this.cg = new CalendarGrid(this);
		this.cb = new CalendarBottom(this);
		
		this.buildFrame();
	}

	private void buildFrame() {
		this.setCenter(this.cg);
		BorderPane.setMargin(cg, new Insets(20, 65, 0, 65));
		this.setBottom(this.cb);
		BorderPane.setMargin(cb, new Insets(20, 150, 140, 150));
	}
	
	public FrameManager getFm() {
		return fm;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public CalendarGrid getCg() {
		return cg;
	}
	
	public CalendarUpdateBox getCub() {
		return cb.getCub();
	}
	
	public CalendarInteractionBox getCib() {
		return cb.getCib();
	}
}
