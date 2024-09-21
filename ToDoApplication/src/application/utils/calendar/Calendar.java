package application.utils.calendar;

import java.time.LocalDate;
import java.time.Month;
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
	
	public int getForYears() {
		return forYears;
	}
	
	public void updateComplete() {
		this.update();
	}
	
	private void update() {
		if(this.calendars != null && this.calendars.size() > 0) {
			try {
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						LinkedHashMap<Integer, ArrayList<CalendarSource>> yearMoves = new LinkedHashMap<Integer, ArrayList<CalendarSource>>();
						
						for(Map.Entry<Integer, YearCalendar> entry : calendars.entrySet()) {
							if(entry.getKey() != 0 && entry.getValue() != null) {
								YearCalendar yc = entry.getValue();
								LinkedHashMap<Month, ArrayList<CalendarSource>> monthMoves = new LinkedHashMap<Month, ArrayList<CalendarSource>>();
								
								for(Map.Entry<Month, MonthCalendar> entry1 : yc.getCalendars().entrySet()) {
									if(entry1.getKey() != null && entry1.getValue() != null) {
										MonthCalendar mc = entry1.getValue();
										ArrayList<CalendarSource> inMonthMoves = new ArrayList<CalendarSource>();
										
										for(Map.Entry<Integer, Object[]> entry2 : mc.getSources().entrySet()) {
											if(entry2.getKey() != 0 && entry2.getValue() != null) {
												Object[] o = entry2.getValue();
												
												if(o != null) {
													for(int i = 0; i < o.length; i++) {
														if(o[i] != null) {
															CalendarSource cs = (CalendarSource) o[i];
															LocalDate ld = cs.getToDo().getDeadline().getLd();
															
															if(ld.getYear() == entry.getKey()) {
																if(ld.getMonth() == entry1.getKey()) {
																	if(ld.getDayOfMonth() != entry2.getKey()) {
																		inMonthMoves.add(cs);
																		o[i] = null;
																	}
																} else {
																	if(!monthMoves.containsKey(ld.getMonth())) {
																		ArrayList<CalendarSource> list = new ArrayList<CalendarSource>();
																		list.add(cs);
																		monthMoves.put(ld.getMonth(), list);
																	} else {
																		ArrayList<CalendarSource> list = monthMoves.get(ld.getMonth());
																		if(!list.contains(cs)) {
																			list.add(cs);
																		}
																		monthMoves.put(ld.getMonth(), list);
																	}
																	o[i] = null;
																}
															} else {
																if(!yearMoves.containsKey(ld.getYear())) {
																	ArrayList<CalendarSource> list = new ArrayList<CalendarSource>();
																	list.add(cs);
																	yearMoves.put(ld.getYear(), list);
																} else {
																	ArrayList<CalendarSource> list = yearMoves.get(ld.getYear());
																	if(!list.contains(cs)) {
																		list.add(cs);
																	}
																	yearMoves.put(ld.getYear(), list);
																}
																o[i] = null;
															}
														}
													}
												}
											}
										}
										
										if(inMonthMoves != null && inMonthMoves.size() > 0) {
											inMonthMoves.forEach(e -> {
												if(e != null) {
													if(mc.getSources().containsKey(e.getToDo().getDeadline().getLd().getDayOfMonth())) {
														Object[] o = mc.getSources().get(e.getToDo().getDeadline().getLd().getDayOfMonth());
														
														for(int i = 0; i < o.length; i++) {
															if(o[i] == null) {
																o[i] = e;
																break;
															}
														}
													}
												}
											});
										}
									}
								}
								
								for(Map.Entry<Month, ArrayList<CalendarSource>> entry1 : monthMoves.entrySet()) {
									if(entry1.getKey() != null && entry1.getValue() != null && entry1.getValue().size() > 0) {
										if(yc.getCalendarOfMonth(entry1.getKey()) != null) {
											MonthCalendar mc = yc.getCalendarOfMonth(entry1.getKey());
											
											entry1.getValue().forEach(e -> {
												if(e != null) {
													if(mc.getSources().containsKey(e.getToDo().getDeadline().getLd().getDayOfMonth())) {
														if(mc.getSources().get(e.getToDo().getDeadline().getLd().getDayOfMonth()) != null) {
															Object[] o = mc.getSources().get(e.getToDo().getDeadline().getLd().getDayOfMonth());
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
							}
						}
						
						for(Map.Entry<Integer, ArrayList<CalendarSource>> entry : yearMoves.entrySet()) {
							if(entry.getKey() != 0 && entry.getValue() != null && entry.getValue().size() > 0) {
								if(calendars.containsKey(entry.getKey()) && calendars.get(entry.getKey()) != null) {
									YearCalendar yc = calendars.get(entry.getKey());
									
									entry.getValue().forEach(e -> {
										if(e != null) {
											if(yc.getCalendars().containsKey(e.getToDo().getDeadline().getLd().getMonth())) {
												if(yc.getCalendarOfMonth(e.getToDo().getDeadline().getLd().getMonth()) != null) {
													MonthCalendar mc = yc.getCalendarOfMonth(e.getToDo().getDeadline().getLd().getMonth());
													
													if(mc.getSources().containsKey(e.getToDo().getDeadline().getLd().getDayOfMonth())) {
														if(mc.getSources().get(e.getToDo().getDeadline().getLd().getDayOfMonth()) != null) {
															Object[] o = mc.getSources().get(e.getToDo().getDeadline().getLd().getDayOfMonth());
															
															for(int i = 0; i < o.length; i++) {
																if(o[i] == null) {
																	o[i] = e;
																	break;
																}
															}
														}
													}
												}
											}
										}
									});
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
					
					while((currentYearCounter / threads) < (forYears / threads)) {
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
