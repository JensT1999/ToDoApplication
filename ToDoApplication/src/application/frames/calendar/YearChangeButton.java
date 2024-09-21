package application.frames.calendar;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class YearChangeButton extends MenuButton {
	
	private CalendarFrame cf;
	
	private ObservableList<MenuItem> items;
	
	public YearChangeButton(CalendarFrame cf) {
		this.cf = cf;
		
		this.items = this.generateYears();
		
		this.buildButton();
	}
	
	private void buildButton() {
		this.setPrefWidth(150);
		
		this.setText(String.valueOf(this.cf.getCg().getShownYear()));
		
		this.loadItemsIntoButton();
	}
	
	private void loadItemsIntoButton() {
		if(this.items != null && this.items.size() > 0) {
			for(int i = 0; i < this.items.size(); i++) {
				if(this.items.get(i) != null) {
					this.getItems().add(this.items.get(i));
				}
			}
		}
	}

	private ObservableList<MenuItem> generateYears(){
		if(this.cf != null && this.cf.getCalendar() != null && this.cf.getCalendar().getForYears() != 0) {
			ObservableList<MenuItem> list = FXCollections.observableArrayList();
			
			for(int i = 0; i < this.cf.getCalendar().getForYears(); i++) {
				MenuItem mi = new MenuItem();
				mi.setText(String.valueOf(LocalDate.now().getYear() + i));
				
				mi.setOnAction(e -> this.itemClicked(mi));
				list.add(mi);
			}
			
			return list;
		}
		return null;
	}

	private void itemClicked(MenuItem item) {
		if(this != null && item != null) {
			this.setText(item.getText());
			
			this.cf.getCg().setShownYear(Integer.valueOf(item.getText()));
		}
	}
}
