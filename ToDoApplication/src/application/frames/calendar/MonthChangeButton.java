package application.frames.calendar;

import java.time.Month;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class MonthChangeButton extends MenuButton {
	
	private CalendarFrame cf;
	
	private Month selMonth;
	
	private MenuItem mi1;
	private MenuItem mi2;
	private MenuItem mi3;
	private MenuItem mi4;
	private MenuItem mi5;
	private MenuItem mi6;
	private MenuItem mi7;
	private MenuItem mi8;
	private MenuItem mi9;
	private MenuItem mi10;
	private MenuItem mi11;
	private MenuItem mi12;

	public MonthChangeButton(CalendarFrame cf) {
		this.cf = cf;
		
		this.selMonth = this.cf.getCg().getShowedMonth();
		
		this.setText(this.convertMonthToString(this.selMonth));
		
		this.mi1 = new MenuItem(this.convertMonthToString(Month.JANUARY));
		this.mi2 = new MenuItem(this.convertMonthToString(Month.FEBRUARY));
		this.mi3 = new MenuItem(this.convertMonthToString(Month.MARCH));
		this.mi4 = new MenuItem(this.convertMonthToString(Month.APRIL));
		this.mi5 = new MenuItem(this.convertMonthToString(Month.MAY));
		this.mi6 = new MenuItem(this.convertMonthToString(Month.JUNE));
		this.mi7 = new MenuItem(this.convertMonthToString(Month.JULY));
		this.mi8 = new MenuItem(this.convertMonthToString(Month.AUGUST));
		this.mi9 = new MenuItem(this.convertMonthToString(Month.SEPTEMBER));
		this.mi10 = new MenuItem(this.convertMonthToString(Month.OCTOBER));
		this.mi11 = new MenuItem(this.convertMonthToString(Month.NOVEMBER));
		this.mi12 = new MenuItem(this.convertMonthToString(Month.DECEMBER));
		
		this.buildButton();
	}

	private void buildButton() {
		this.setPrefWidth(150);
		
		this.mi1.setOnAction(e -> this.changeSelectedMonth(this.mi1));
		this.mi2.setOnAction(e -> this.changeSelectedMonth(this.mi2));
		this.mi3.setOnAction(e -> this.changeSelectedMonth(this.mi3));
		this.mi4.setOnAction(e -> this.changeSelectedMonth(this.mi4));
		this.mi5.setOnAction(e -> this.changeSelectedMonth(this.mi5));
		this.mi6.setOnAction(e -> this.changeSelectedMonth(this.mi6));
		this.mi7.setOnAction(e -> this.changeSelectedMonth(this.mi7));
		this.mi8.setOnAction(e -> this.changeSelectedMonth(this.mi8));
		this.mi9.setOnAction(e -> this.changeSelectedMonth(this.mi9));
		this.mi10.setOnAction(e -> this.changeSelectedMonth(this.mi10));
		this.mi11.setOnAction(e -> this.changeSelectedMonth(this.mi11));
		this.mi12.setOnAction(e -> this.changeSelectedMonth(this.mi12));

		this.getItems().addAll(mi1, mi2, mi3, mi4, mi5, mi6, mi7, mi8, mi9, mi10, mi11, mi12);
	}
	
	private void changeSelectedMonth(MenuItem mi) {
		if(mi != null && mi.getText() != "" && this.cf != null && this.cf.getCalendar() != null 
				&& this.cf.getCg() != null) {
			
			this.setText(mi.getText());
			Month changed = this.convertStringIntoMonth(mi.getText());
			this.selMonth = changed;
			this.cf.getCg().setShowedMonth(changed);
		}
	}
	
	private Month convertStringIntoMonth(String input) {
		Month m = switch (input.toLowerCase()) {
			case "januar" -> Month.JANUARY;
			case "februar" -> Month.FEBRUARY;
			case "märz" -> Month.MARCH;
			case "april" -> Month.APRIL;
			case "mai" -> Month.MAY;
			case "juni" -> Month.JUNE;
			case "juli" -> Month.JULY;
			case "august" -> Month.AUGUST;
			case "september" -> Month.SEPTEMBER;
			case "oktober" -> Month.OCTOBER;
			case "november" -> Month.NOVEMBER;
			case "dezember" -> Month.DECEMBER;
			default -> null;
		};
		return m;
	}
	
	private String convertMonthToString(Month m) {
		String s = switch (m) {
			case Month.JANUARY -> "Januar";
			case Month.FEBRUARY -> "Februar";
			case Month.MARCH -> "März";
			case Month.APRIL -> "April";
			case Month.MAY -> "Mai";
			case Month.JUNE -> "Juni";
			case Month.JULY -> "Juli";
			case Month.AUGUST -> "August";
			case Month.SEPTEMBER -> "September";
			case Month.OCTOBER -> "Oktober";
			case Month.NOVEMBER -> "November";
			case Month.DECEMBER -> "Dezember";
			default -> "";
		};
		
		return s;
	}
}
