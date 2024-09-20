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
	
	public void updateComplete() {
		if(this.calendar != null && this.calendar.size() > 0) {
			LinkedHashMap<CalendarSource, Month> moves = new LinkedHashMap<CalendarSource, Month>();
			
			for(Map.Entry<Month, MonthCalendar> entry : this.calendar.entrySet()) {
				if(entry.getKey() != null && entry.getValue() != null) {
					MonthCalendar mc = entry.getValue();
					ArrayList<CalendarSource> list = new ArrayList<CalendarSource>();
							
					for(Map.Entry<Integer, Object[]> entry1 : mc.getSources().entrySet()) {
						if(entry1.getKey() != 0 && entry1.getValue() != null) {
							Object[] o = entry1.getValue();
							
							if(o != null) {
								for(int i = 0; i < o.length; i++) {
									if(o[i] != null) {
										
										CalendarSource cs = (CalendarSource) o[i];
										
										if(cs.getToDo().getDeadline().getLd().getMonth().equals(entry.getKey())) {
											if(cs.getToDo().getDeadline().getLd().getDayOfMonth() != entry1.getKey()) {
												list.add(cs);
												o[i] = null;
											}
										} else {
											moves.put(cs, cs.getToDo().getDeadline().getLd().getMonth());
											o[i] = null;
										}
									}
								}
							}
						}
					}
					
					if(list != null && list.size() > 0) {
						list.forEach(e -> {
							if(e != null) {
								if(mc.getSources().containsKey(e.getToDo().getDeadline().getLd().getDayOfMonth())) {
									Object[] o = mc.getSources().get(e.getToDo().getDeadline().getLd().getDayOfMonth());
									
									if(o != null) {
										for(int i = 0; i < o.length; i++) {
											if(o[i] == null) {
												o[i] = e;
												break;
											}
										}
									}
								}
							}
						});
					}
				}
			}
			
			for(Map.Entry<CalendarSource, Month> entry : moves.entrySet()) {
				if(entry.getKey() != null && entry.getValue() != null) {
					MonthCalendar mc = this.getCalendarOfMonth(entry.getValue());
					
					if(mc.getSources().containsKey(entry.getKey().getToDo().getDeadline().getLd().getDayOfMonth())) {
						Object[] o = mc.getSources().get(entry.getKey().getToDo().getDeadline().getLd().getDayOfMonth());
						
						if(o != null) {
							for(int i = 0; i < o.length; i++) {
								if(o[i] == null) {
									o[i] = entry.getKey();
									break;
								}
							}
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
