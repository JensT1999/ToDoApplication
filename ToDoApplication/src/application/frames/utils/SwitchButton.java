package application.frames.utils;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SwitchButton extends Button {
	
	private FrameManager fm;
	
	public SwitchButton(FrameManager fm) {
		this.fm = fm;
		
		this.buildButton();
	}

	private void buildButton() {
		this.setPrefWidth(150);
		this.setNameForCurrentFrame();
		this.setOnMouseClicked(e -> this.buttonClicked());
	}

	private void buttonClicked() {
		switch (this.fm.getCurrentFrame()) {
			case FrameType.TODO_FRAME -> {
				if(this.fm.getTdf().getIb().getChildren().contains(this)) {
					this.fm.getTdf().getIb().getChildren().remove(this);
					this.fm.switchFrame(FrameType.CALENDAR_FRAME);
					this.fm.getCf().getCib().add(this, 2, 0);
					
					GridPane.setMargin(this, new Insets(5));
				}
			}
			case FrameType.CALENDAR_FRAME -> {
				if(this.fm.getCf().getCib().getChildren().contains(this)) {
					this.fm.getCf().getCib().getChildren().remove(this);
					this.fm.switchFrame(FrameType.TODO_FRAME);
					this.fm.getTdf().getIb().getChildren().add(this);
					
					VBox.setMargin(this, new Insets(10));
				}
			}
		}
		this.setNameForCurrentFrame();
	}
	
	private void setNameForCurrentFrame() {
		switch (this.fm.getCurrentFrame()) {
			case FrameType.TODO_FRAME -> this.setText(FrameType.CALENDAR_FRAME.toString());
			case FrameType.CALENDAR_FRAME -> this.setText(FrameType.TODO_FRAME.toString());
		}
	}
}
