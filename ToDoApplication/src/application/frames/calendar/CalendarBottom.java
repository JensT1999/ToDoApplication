package application.frames.calendar;

import javafx.scene.layout.VBox;

public class CalendarBottom extends VBox {
	
	private CalendarFrame cf;
	
	private CalendarInteractionBox cib;
	private CalendarUpdateBox cub;
	
	public CalendarBottom(CalendarFrame cf) {
		this.cf = cf;
		
		this.cib = new CalendarInteractionBox(this.cf);
		this.cub = new CalendarUpdateBox(this.cf);
		
		this.buildBox();
	}

	private void buildBox() {
		this.getChildren().addAll(cib, cub);
		this.cub.setVisible(false);
	}
	
	public CalendarInteractionBox getCib() {
		return cib;
	}
	
	public CalendarUpdateBox getCub() {
		return cub;
	}

}
