package application.utils.calendar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class MonthCalendar {
	
	private LinkedHashMap<Integer, Object[]> sources;
	
	public MonthCalendar() {
		this.sources = new LinkedHashMap<Integer, Object[]>();
	}
	
	public void addTo(int day, CalendarSource source) {
		if(day != 0 && source != null && this.sources != null) {
			if(!this.sources.containsKey(day)) {
				Object[] o = new Object[20];
				o[0] = source;
				this.sources.put(day, o);
			} else {
				Object[] o = this.sources.get(day);
				for(int i = 0; i < o.length; i++) {
					if(o[i] == null) {
						o[i] = source;
						break;
					}
				}
				this.sources.put(day, o);
			}
		}
	}
	
	public void removeFrom(int day, CalendarSource source) {
		if(day != 0 && source != null && this.sources != null) {
			if(this.sources.containsKey(day)) {
				if(this.sources.get(day) != null) {
					if(this.sources.get(day).length > 1) {
						Object[] o = this.sources.get(day);
						for(int i = 0; i < o.length; i++) {
							if(o[i] != null && o[i].equals(source)) {
								o[i] = null;
								break;
							}
						}
						this.sources.put(day, o);
					} else if(this.sources.get(day).length == 1) {
						Object[] o = new Object[20];
						this.sources.put(day, o);
					}
				}
			}
		}
	}
	
	public void createCalendar(int maxDays) {
		if(maxDays > 0 && this.sources != null) {
			for(int i = 1; i < maxDays + 1; i++) {
				if(!this.sources.containsKey(i)) {
					Object[] o = new Object[20];
					this.sources.put(i, o);
				}
			}
		}
	}
	
	public void print() {
		if(this.sources != null && this.sources.size() > 0) {
			for(Entry<Integer, Object[]> entry : this.sources.entrySet()) {
				if(entry.getKey() != 0 && entry.getValue() != null) {
					Object[] o = entry.getValue();
					for(int i = 0; i < o.length; i++) {
						if(o[i] != null) {
							System.out.println(o[i]);
							CalendarSource s = (CalendarSource) o[i];
							System.out.println(s.getInfo() + " " + s.getToDo().getDeadline().getLd().getDayOfMonth());
						} else {
							break;
						}
					}
				}
			}
		}
	}
	
	public LinkedHashMap<Integer, Object[]> getSources() {
		return sources;
	}
}
