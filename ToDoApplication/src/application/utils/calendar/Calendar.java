package application.utils.calendar;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.utils.ToDos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Calendar {
	
	private int maxThreads;
	
	private LinkedHashMap<Integer, YearCalendar> calendars;
	
	private ExecutorService exe;
	
	private int currentYear;
	
	private int currentYearCounter;
	
	private int forYears;
	
	public Calendar() {
		this.maxThreads = 3;
		
		this.calendars = new LinkedHashMap<Integer, YearCalendar>();
		
		this.exe = Executors.newFixedThreadPool(this.maxThreads);
		
		this.currentYear = LocalDate.now().getYear();
		
		this.currentYearCounter = 0;
		
		this.forYears = 2999 - this.currentYear;
	}
	
	public void generate() {
		this.calendars = this.generateComplete();
		
		this.exe.shutdown();
	}
	
	public void loadInputs(ObservableList<ToDos> list) {
		this.loadInputsInto(list);
	}
	
	public YearCalendar getCalendarOfYear(int year) {
		if(this.calendars.containsKey(year)) {
			return this.calendars.get(year);
		}
		return null;
	}
	
	private void loadInputsInto(ObservableList<ToDos> list){
		if(list != null && list.size() > 0 && this.calendars != null && this.calendars.size() > 0) {
			try {
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						LinkedHashMap<Integer, ObservableList<ToDos>> existingYears = 
								new LinkedHashMap<Integer, ObservableList<ToDos>>();
						
						for(int i = 0; i < list.size(); i++) {
							if(list.get(i) != null) {
								ToDos td = list.get(i);
								int year = td.getDeadline().getLd().getYear();
								if(!existingYears.containsKey(year)) {
									ObservableList<ToDos> list1 = FXCollections.observableArrayList();
									list1.add(td);
									existingYears.put(year, list1);
								} else {
									if(existingYears.get(year) != null) {
										ObservableList<ToDos> list1 = existingYears.get(year);
										
										if(!list1.contains(td)) {
											list1.add(td);
										}
										
										existingYears.put(year, list1);
									}
								}
							}
						}
						
						for(Map.Entry<Integer, ObservableList<ToDos>> entry : existingYears.entrySet()) {
							if(entry.getKey() != 0 && entry.getValue() != null) {
								if(calendars.containsKey(entry.getKey())) {
									if(calendars.get(entry.getKey()) != null) {
										calendars.get(entry.getKey()).loadListIntoCalendar(entry.getValue());
									}
								}
							}
						}
					}
				});
				
				t.start();
				
				t.join();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private LinkedHashMap<Integer, YearCalendar> generateComplete() {
		if(this.calendars != null) {
			Object[] o = new Object[this.maxThreads];
			
			for(int i = 0; i < this.maxThreads; i++) {
				o[i] = (LinkedHashMap<Integer, YearCalendar>) this.generateFraction(this.maxThreads);
			}
			
			if(o != null) {
				
				try {
					Future<LinkedHashMap<Integer, YearCalendar>> future = this.exe.submit(new Callable<LinkedHashMap<Integer, YearCalendar>>() {

						@Override
						public LinkedHashMap<Integer, YearCalendar> call() throws Exception {
							LinkedHashMap<Integer, YearCalendar> tempMap = new LinkedHashMap<Integer, YearCalendar>();
							
							for(int i = 0; i < o.length; i++) {
								if(o[i] != null) {
									LinkedHashMap<Integer, YearCalendar> maps = (LinkedHashMap<Integer, YearCalendar>) o[i];
									for(Map.Entry<Integer, YearCalendar> entry : maps.entrySet()) {
										if(entry.getKey() != 0 && entry.getValue() != null) {
											if(entry.getValue().getCalendars().size() > 0) {
												tempMap.put(entry.getKey(), entry.getValue());
											}
										}
									}
								}
							}
							
							return tempMap;
						}
					});
					
					return future.get();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private LinkedHashMap<Integer, YearCalendar> generateFraction(int threads){
		try {
			Future<LinkedHashMap<Integer, YearCalendar>> future = this.exe.submit(new Callable<LinkedHashMap<Integer, YearCalendar>>() {

				@Override
				public LinkedHashMap<Integer, YearCalendar> call() throws Exception {
					LinkedHashMap<Integer, YearCalendar> tempMap = new LinkedHashMap<Integer, YearCalendar>();
					
					while((currentYearCounter / 3) < (forYears / 3)) {
						YearCalendar yc = new YearCalendar();
						tempMap.put((currentYear + currentYearCounter), yc);
						currentYearCounter++;
					}
					
					return tempMap;
				}
			});
			
			return future.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
