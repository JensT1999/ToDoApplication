package application.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.utils.calendar.CalendarSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class Utils {
	
	private static ExecutorService exe = Executors.newCachedThreadPool();
	
	public static ObservableList<ToDos> sort(ObservableList<ToDos> list, SortType type) {
		if(list != null && type != null) {
			try {
				Future<ObservableList<ToDos>> future = exe.submit(new Callable<ObservableList<ToDos>>() {

					@Override
					public ObservableList<ToDos> call() throws Exception {
						ObservableList<ToDos> list1 = FXCollections.observableArrayList();
						SortedList<ToDos> sortedList = new SortedList<ToDos>(list);
						
						switch (type) {
							case SortType.BY_PRIO -> {
								sortedList.setComparator(new Comparator<ToDos>() {

									@Override
									public int compare(ToDos o1, ToDos o2) {
										Integer i1 = o1.getPrio().getPrioAsInt();
										Integer i2 = o2.getPrio().getPrioAsInt();
										return i2.compareTo(i1);
									}
								});
							}
							case SortType.BY_DEADLINE -> {
								sortedList.setComparator(new Comparator<ToDos>() {

									@Override
									public int compare(ToDos o1, ToDos o2) {
										Integer i1 = o1.getDeadline().getDistanceToDeadLine();
										Integer i2 = o2.getDeadline().getDistanceToDeadLine();
										return i1.compareTo(i2);
									}
								});
							}
						}
						
						if(sortedList.size() > 0) {
							Iterator<ToDos> it = sortedList.iterator();
							
							while(it.hasNext()) {
								if(it != null) {
									list1.add(it.next());
								}
							}
						}
						
						return list1;
					}
				});
				
				return future.get();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static CalendarSource getMostImportant(Object[] o) {
		if(o != null) {
			try {
				Future<CalendarSource> future = exe.submit(new Callable<CalendarSource>() {

					@Override
					public CalendarSource call() throws Exception {
						ArrayList<CalendarSource> list = new ArrayList<CalendarSource>();
						for(Object obj : o) {
							if(obj != null) {
								CalendarSource cs = (CalendarSource) obj;
								
								if(cs.getToDo() != null) {
									list.add(cs);
								}
							}
						}
						
						if(list.size() > 0) {
							Collections.sort(list, new Comparator<CalendarSource>() {

								@Override
								public int compare(CalendarSource o1, CalendarSource o2) {
									Integer i1 = o1.getToDo().getPrio().getPrioAsInt();
									Integer i2 = o2.getToDo().getPrio().getPrioAsInt();
									return i2.compareTo(i1);
								}
							});
							
							return list.get(0);	
						}		
						
						return null;
					}
				});
				
				return future.get();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
