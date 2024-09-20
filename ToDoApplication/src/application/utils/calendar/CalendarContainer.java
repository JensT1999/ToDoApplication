package application.utils.calendar;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CalendarContainer extends HBox {
	
	private Label l;
	
	public CalendarContainer() {
		this.l = new Label();
		
		this.buildContainer();
	}
	
	private void buildContainer() {
		this.getChildren().add(l);
		
		HBox.setMargin(l, new Insets(8));
	}

	public void setText(String t) {
		this.l.setText(t);
	}
	
	public String getText() {
		return l.getText();
	}
	public Label getLabel() {
		return l;
	}

}
