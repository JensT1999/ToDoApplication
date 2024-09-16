package application.frames;

import java.time.LocalDate;

import application.utils.SortType;
import application.utils.ToDoPrio;
import application.utils.ToDos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class InteractionBox extends VBox {
	
	private ToDoFrame tdf;
	
	private TextField tf1;
	private MenuButton mB;
	private MenuItem mi1;
	private MenuItem mi2;
	private MenuItem mi3;
	private MenuItem mi4;
	private MenuItem mi5;
	private DatePicker dp;
	
	private Button b1;
	private Button b2;
	
	private MenuButton mB1;
	private MenuItem mi6;
	private MenuItem mi7;
	
	public InteractionBox(ToDoFrame tdf) {
		this.tdf = tdf;
		
		this.tf1 = new TextField();
		this.mB = new MenuButton("Priorität");
		this.mi1 = new MenuItem(ToDoPrio.HIGHEST.toString());
		this.mi2 = new MenuItem(ToDoPrio.HIGH.toString());
		this.mi3 = new MenuItem(ToDoPrio.MIDDLE.toString());
		this.mi4 = new MenuItem(ToDoPrio.LOW.toString());
		this.mi5 = new MenuItem(ToDoPrio.LOWEST.toString());
		this.dp = new DatePicker();
		
		this.b1 = new Button("Hinzufügen");
		this.b2 = new Button("Erledigte" + System.getProperty("line.separator") + "entfernen");
		
		this.mB1 = new MenuButton("Sortierung");
		this.mi6 = new MenuItem("Nach " + SortType.BY_PRIO.toString());
		this.mi7 = new MenuItem("Nach " + SortType.BY_DEADLINE.toString());
		
		this.buildBox();
	}

	private void buildBox() {
		this.tf1.setMaxWidth(150);
		VBox.setMargin(tf1, new Insets(10));
		
		this.tf1.setOnKeyPressed(e -> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				this.addToDo();
			}
		});
		
		this.mB.setMaxWidth(150);
		this.mi1.setOnAction(e -> {
			if(this.mB != null) {
				this.mB.setText(this.mi1.getText());
			}
		});
		
		this.mi2.setOnAction(e -> {
			if(this.mB != null) {
				this.mB.setText(this.mi2.getText());
			}
		});
		
		this.mi3.setOnAction(e -> {
			if(this.mB != null) {
				this.mB.setText(this.mi3.getText());
			}
		});
		
		this.mi4.setOnAction(e -> {
			if(this.mB != null) {
				this.mB.setText(this.mi4.getText());
			}
		});
		
		this.mi5.setOnAction(e -> {
			if(this.mB != null) {
				this.mB.setText(this.mi5.getText());
			}
		});
		this.mB.getItems().addAll(mi1, mi2, mi3, mi4, mi5);
		VBox.setMargin(mB, new Insets(10));
		
		this.dp.setMaxWidth(150);
		VBox.setMargin(dp, new Insets(10));
		
		this.b1.setMaxWidth(150);
		VBox.setMargin(b1, new Insets(10));
		
		this.b1.setOnMouseClicked(e -> this.addToDo());
		
		this.b2.setMaxWidth(150);
		VBox.setMargin(b2, new Insets(10));
		this.b2.setOnMouseClicked(e ->  {
			this.tdf.getToDoList().removeCheckedOnes();
			this.tdf.getTvb().updateComplete();
		});
		
		this.mB1.setMaxWidth(150);
		this.mi6.setOnAction(e -> {
			if(this.tdf.getToDoList() != null) {
				this.tdf.getToDoList().sort(SortType.BY_PRIO);
				this.mB1.setText(this.mi6.getText());
				
				this.tdf.getTvb().updateComplete();
			}
		});
		this.mi7.setOnAction(e -> {
			if(this.tdf.getToDoList() != null) {
				this.tdf.getToDoList().sort(SortType.BY_DEADLINE);
				this.mB1.setText(this.mi7.getText());
				
				this.tdf.getTvb().updateComplete();
			}
		});
		this.mB1.getItems().addAll(mi6, mi7);
		VBox.setMargin(mB1, new Insets(10));
		
		this.getChildren().addAll(tf1, mB, dp, b1, b2, mB1);
	}
	
	private void addToDo() {
		if(this.tf1.getText() != "" && this.mB.getText() != "Priorität" &&
				this.dp.getValue() != null) {
			String info = this.tf1.getText();
			
			ToDoPrio prio = switch (this.mB.getText().toLowerCase()) {
				case "höchste" -> ToDoPrio.HIGHEST;
				case "hoch" -> ToDoPrio.HIGH;
				case "mittel" -> ToDoPrio.MIDDLE;
				case "niedrig" -> ToDoPrio.LOW;
				case "niedrigste" -> ToDoPrio.LOWEST;
				default -> 
				throw new IllegalArgumentException("Unexpected value: " + this.mB.getText().toLowerCase());
			};
			
			LocalDate ld = this.dp.getValue();
			String deadline = "";
			
			if(ld != null) {
				if(ld.getMonthValue() < 10) {
					if(ld.getDayOfMonth() < 10) {
						deadline = "0" + ld.getDayOfMonth() + ".0" + 
								ld.getMonthValue() + "." + ld.getYear();
					} else {
						deadline = ld.getDayOfMonth() + ".0" + ld.getMonthValue() + "." + ld.getYear();
					}
				} else {
					if(ld.getDayOfMonth() < 10) {
						deadline = "0" + ld.getDayOfMonth() + "." + 
								ld.getMonthValue() + "." + ld.getYear();
					} else {
						deadline = ld.getDayOfMonth() + "." + ld.getMonthValue() + "." + ld.getYear();
					}
				}
			}
			
			ToDos td = new ToDos(info, prio, deadline);
			
			this.tdf.getToDoList().addToList(td);
			
			switch (this.mB1.getText().toLowerCase()) {
				case "nach priorität" -> this.tdf.getToDoList().sort(SortType.BY_PRIO);
				case "nach deadline" -> this.tdf.getToDoList().sort(SortType.BY_DEADLINE);
				case "sortierung" -> this.tdf.getToDoList().sort(SortType.BY_PRIO);
 			}
			
			this.tdf.getTvb().updateComplete();
			
			this.tf1.clear();
			this.mB.setText("Priorität");
			this.dp.setValue(null);
			this.tf1.requestFocus();
		}
	}
}
