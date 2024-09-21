package application.utils.calendar;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import application.utils.ToDos;
import javafx.collections.ObservableList;

public class YearCalendar {
	
	private LinkedHashMap<Month, MonthCalendar> calendar;
	
	public YearCalendar() {
		this.calendar = new LinkedHashMap<Month, MonthCalendar>();
		this.generateCalendar();
	}
	
	public MonthCalendar getCalendarOfMonth(Month m) {
		if(this.calendar != null && this.calendar.size() > 0 && m != null) {
			if(this.calendar.containsKey(m)) {
				if(this.calendar.get(m) != null) {
					return this.calendar.get(m);
				}
			}
		}
		return null;
	}
	
	public void loadListIntoCalendar(ObservableList<ToDos> list) {
		if(this.calendar != null && list != null && list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i) != null) {
					ToDos td = list.get(i);
					Month m = td.getDeadline().getLd().getMonth();
					if(this.calendar.containsKey(m)) {
						MonthCalendar mc = this.calendar.get(m);
						
						if(mc.getSources().containsKey(td.getDeadline().getLd().getDayOfMonth())) {
							CalendarSource cs = new CalendarSource(td);
							
							mc.addTo(td.getDeadline().getLd().getDayOfMonth(), cs);
						}
					}
				}
			}
		}
	}
	
	public LinkedHashMap<Month, MonthCalendar> getCalendars() { 
		return calendar;
	}

	private void generateCalendar() {
		for(int i = 1; i <= 12; i++) {
			MonthCalendar cal = new MonthCalendar();
			YearMonth ym = YearMonth.of(LocalDate.now().getYear(), i);
			cal.createCalendar(ym.atEndOfMonth().getDayOfMonth());
			this.calendar.put(this.convertIntInMonth(i), cal);
		}
	}
	
	private Month convertIntInMonth(int i) {
		Month m = switch (i) {
			case 1 -> Month.JANUARY;
			case 2 -> Month.FEBRUARY;
			case 3 -> Month.MARCH;
			case 4 -> Month.APRIL;
			case 5 -> Month.MAY;
			case 6 -> Month.JUNE;
			case 7 -> Month.JULY;
			case 8 -> Month.AUGUST;
			case 9 -> Month.SEPTEMBER;
			case 10 -> Month.OCTOBER;
			case 11 -> Month.NOVEMBER;
			case 12 -> Month.DECEMBER;
			default -> throw new IllegalArgumentException("Unexpected value: " + i);
		};
		
		return m;
	}
}
