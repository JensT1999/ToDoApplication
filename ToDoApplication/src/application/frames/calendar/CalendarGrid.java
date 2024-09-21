package application.frames.calendar;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import application.utils.Utils;
import application.utils.calendar.Calendar;
import application.utils.calendar.CalendarContainer;
import application.utils.calendar.CalendarSource;
import application.utils.calendar.MonthCalendar;
import application.utils.calendar.YearCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class CalendarGrid extends GridPane {
	
	private CalendarFrame cf;
	
	private Calendar cal;
	
	private ColumnConstraints cl1;
	private ColumnConstraints cl2;
	private ColumnConstraints cl3;
	private ColumnConstraints cl4;
	private ColumnConstraints cl5;
	private ColumnConstraints cl6;
	private ColumnConstraints cl7;
	
	private RowConstraints rc1;
	private RowConstraints rc2;
	private RowConstraints rc3;
	private RowConstraints rc4;
	private RowConstraints rc5;
	
	private LinkedHashMap<Integer, CalendarContainer> container;
	
	private int shownYear;
	private Month showedMonth;
	
	private YearCalendar shownCalendar;

	public CalendarGrid(CalendarFrame cf) {
		this.cf = cf;
		this.cal = this.cf.getCalendar();
		
		this.cl1 = new ColumnConstraints();
		this.cl2 = new ColumnConstraints();
		this.cl3 = new ColumnConstraints();
		this.cl4 = new ColumnConstraints();
		this.cl5 = new ColumnConstraints();
		this.cl6 = new ColumnConstraints();
		this.cl7 = new ColumnConstraints();
		
		this.rc1 = new RowConstraints();
		this.rc2 = new RowConstraints();
		this.rc3 = new RowConstraints();
		this.rc4 = new RowConstraints();
		this.rc5 = new RowConstraints();
				
		this.container = new LinkedHashMap<Integer, CalendarContainer>();
		
		this.shownYear = LocalDate.now().getYear();
		this.showedMonth = LocalDate.now().getMonth();
		
		this.shownCalendar = this.cal.getCalendarOfYear(this.shownYear);
		
		this.buildCalendar();
	}
	
	public void setShowedMonth(Month m) {
		if(m != null && m != showedMonth && this.shownCalendar != null) {
			this.showedMonth = m;
			this.updateCalendar();
		}
	}
	
	public void setShownYear(int year) {
		if(year != 0 && year >= LocalDate.now().getYear()) {
			System.out.println("test");
			this.shownYear = year;
			this.shownCalendar = this.cal.getCalendarOfYear(this.shownYear);
			this.updateCalendar();
			this.cf.getCub().setShownYear(this.shownYear);
		}
	}
	
	public void update() {
		if(this.shownCalendar != null) {
			this.updateCalendar();
		}
	}
	
	public Month getShowedMonth() {
		return showedMonth;
	}
	
	public int getShownYear() {
		return shownYear;
	}

	private void buildCalendar() {
		this.setGridLinesVisible(true);
		
		this.cl1.setPrefWidth(80);
		this.cl2.setPrefWidth(80);
		this.cl3.setPrefWidth(80);
		this.cl4.setPrefWidth(80);
		this.cl5.setPrefWidth(80);
		this.cl6.setPrefWidth(80);
		this.cl7.setPrefWidth(80);
		
		this.rc1.setPrefHeight(50);
		this.rc2.setPrefHeight(50);
		this.rc3.setPrefHeight(50);
		this.rc4.setPrefHeight(50);
		this.rc5.setPrefHeight(50);
		
		this.getColumnConstraints().addAll(cl1, cl2, cl3, cl4, cl5, cl6, cl7);
		this.getRowConstraints().addAll(rc1, rc2, rc3, rc4, rc5);
		
		this.loadCalendar();
	}
	
	private void updateCalendar() {
		if(this.shownCalendar != null && this.shownCalendar.getCalendars() != null &&
				this.shownCalendar.getCalendars().size() > 0 && this.container != null &&
				this.container.size() > 0) {
			if(this.shownCalendar.getCalendars().containsKey(this.showedMonth)) {
				if(this.shownCalendar.getCalendarOfMonth(this.showedMonth) != null) {
					this.clearCalendar();
					MonthCalendar mc = this.shownCalendar.getCalendarOfMonth(showedMonth);
					
					for(Entry<Integer, Object[]> entry : mc.getSources().entrySet()) {
						if(entry.getKey() != 0 && entry.getValue() != null) {
							if(this.container.containsKey(entry.getKey())) {
								String info = entry.getKey() + " ";
								CalendarContainer ct = this.container.get(entry.getKey());
								
								if(Utils.getMostImportant(entry.getValue()) != null) {
									info += Utils.getMostImportant(entry.getValue()).getInfo();
								} 
								
								ct.setText(info);
							} else {
								if(this.container.size() < mc.getSources().size()) {
									String info = entry.getKey() + " ";
									CalendarContainer ct = new CalendarContainer();
									
									if(Utils.getMostImportant(entry.getValue()) != null) {
										info += Utils.getMostImportant(entry.getValue()).getInfo();
									}
									
									ct.setOnMouseClicked(e -> {
										if(e.getClickCount() >= 2) {
											this.containerClicked(ct);
										}
									});
									
									ct.setText(info);
									
									GridPane.setMargin(ct, new Insets(5));
									
									this.container.put(entry.getKey(), ct);
									
									this.setInSport(entry.getKey(), ct);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void clearCalendar() {
		if(this.container != null && this.container.size() > 0) {
			for(Map.Entry<Integer, CalendarContainer> entry : this.container.entrySet()) {
				if(entry.getKey() != 0 && entry.getValue() != null) {
					entry.getValue().setText("");
				}
			}
		}
	}
	
	private void loadCalendar() {
		if(this.shownCalendar != null && this.shownCalendar.getCalendars() != null && 
				this.shownCalendar.getCalendars().size() > 0) {
			if(this.shownCalendar.getCalendars().containsKey(this.showedMonth)) {
				if(this.shownCalendar.getCalendarOfMonth(this.showedMonth) != null) {
					MonthCalendar mc = this.shownCalendar.getCalendarOfMonth(this.showedMonth);
					
					int cc = 0;
					int cr = 0;
					
					for(Map.Entry<Integer, Object[]> entry : mc.getSources().entrySet()) {
						if(entry.getKey() != 0 && entry.getValue() != null) {
							Object[] o = entry.getValue();
							String info = entry.getKey() + " ";
							CalendarContainer ct;
							CalendarSource cs;
							
							if(Utils.getMostImportant(o) != null) {
								cs = Utils.getMostImportant(o);
								info += cs.getInfo();
							}
							
							if(!this.container.containsKey(entry.getKey())) {
								ct = new CalendarContainer();
								this.container.put(entry.getKey(), ct);
							} else {
								ct = this.container.get(entry.getKey());
							}
							
							ct.setOnMouseClicked(e -> {
								if(e.getClickCount() >= 2) {
									this.containerClicked(ct);
								}
							});
							
							ct.setText(info);
							
							GridPane.setMargin(ct, new Insets(5));
							
							if(cc <= 6) {
								this.add(ct, cc, cr);
								cc++;
							} else {
								cc = 0;
								cr++;
								this.add(ct, cc, cr);
								cc++;
							}
						}
					}
				}
			}
		}
	}
	
	private void setInSport(int spot, CalendarContainer ct) {
		if(spot != 0 && ct != null) {
			int currentspot = 1;
			int cc = 0;
			int cr = 0;
			
			while(currentspot < spot) {
				if(cc <= 6) {
					cc++;
				} else {
					cc = 0;
					cr++;
					cc++;
				}
				currentspot++;
			}
						
			this.add(ct, cc, cr);
		}
	}
	
	private void containerClicked(CalendarContainer cc) {
		if(cc != null && cc.getText() != "") {
			for(Map.Entry<Integer, CalendarContainer> entry : this.container.entrySet()) {
				if(entry.getValue().equals(cc)) {
					MonthCalendar mc = this.shownCalendar.getCalendarOfMonth(this.showedMonth);
					
					if(mc.getSources().containsKey(entry.getKey())) {
						Object[] o = mc.getSources().get(entry.getKey());
						ObservableList<CalendarSource> list = FXCollections.observableArrayList();
						
						for(Object obj : o) {
							if(obj != null) {
								CalendarSource cs = (CalendarSource) obj;
								list.add(cs);
							}
						}
						
						this.cf.getCub().showFor(list);
						this.cf.getCub().setVisible(true);
					}
				}
			}
		}
	}
}
