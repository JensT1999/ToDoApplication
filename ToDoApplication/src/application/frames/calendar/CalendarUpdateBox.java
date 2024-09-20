package application.frames.calendar;

import java.time.LocalDate;
import java.util.Comparator;

import application.mysql.UpdateType;
import application.utils.ToDoPrio;
import application.utils.calendar.CalendarSource;
import application.utils.calendar.MonthCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CalendarUpdateBox extends HBox {
	
	private CalendarFrame cf;
	
	private TableColumn<CalendarSource, String> tc1;
	private TableColumn<CalendarSource, String> tc2;
	
	private TableView<CalendarSource> tv;
	
	private Button b1;
		
	public CalendarUpdateBox(CalendarFrame cf) {
		this.cf = cf;
		
		this.tc1 = new TableColumn<CalendarSource, String>("Info");
		this.tc2 = new TableColumn<CalendarSource, String>("Deadline");
		
		this.tv = new TableView<CalendarSource>();
		
		this.b1 = new Button("Speichern");
		
		this.buildBox();
	}

	public void showFor(ObservableList<CalendarSource> list) {
		SortedList<CalendarSource> sl = new SortedList<CalendarSource>(list);
		sl.setComparator(new Comparator<CalendarSource>() {

			@Override
			public int compare(CalendarSource o1, CalendarSource o2) {
				Integer i1 = o1.getToDo().getPrio().getPrioAsInt();
				Integer i2 = o2.getToDo().getPrio().getPrioAsInt();
				return i2.compareTo(i1);
			}
		});
		
		ObservableList<CalendarSource> tempList = FXCollections.observableArrayList();
		
		sl.forEach(e -> {
			if(e != null) {
				tempList.add(e);
			}
		});
		
		this.tv.setItems(tempList);
	}
	
	private void buildBox() {
		this.tv.setEditable(false);
		this.tv.setPrefSize(500, 100);
		this.tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.tc1.setCellValueFactory(new PropertyValueFactory<CalendarSource, String>("info"));
		this.tc2.setCellValueFactory(new PropertyValueFactory<CalendarSource, String>("dateAsString"));
		
		this.tv.getColumns().addAll(tc1, tc2);
		this.getChildren().addAll(tv);
		
		this.b1.setPrefWidth(150);
		GridPane.setMargin(b1, new Insets(5));
		this.b1.setVisible(false);
		
		this.tv.setOnMouseClicked(e -> {
			if(e.getClickCount() >= 2) {
				if(this.tv.getSelectionModel() != null && this.tv.getSelectionModel().getSelectedItem() != null) {
					CalendarSource cs = this.tv.getSelectionModel().getSelectedItem();
					
					if(this.cf.getCib().getTextfield() != null && this.cf.getCib().getMB() != null &&
							this.cf.getCib().getDatePicker() != null) {
						TextField tf = this.cf.getCib().getTextfield();
						MenuButton mb = this.cf.getCib().getMB();
						DatePicker dp = this.cf.getCib().getDatePicker();
						
						tf.setText(cs.getToDo().getInformation());
						mb.setText(cs.getToDo().getPrioAsString());
						dp.setValue(cs.getToDo().getDeadline().getLd());
						
						if(!this.b1.isVisible()) {
							this.cf.getCib().add(b1, 0, 0);
							this.b1.setVisible(true);
						}
					}
				}
			}
		});
		
		this.b1.setOnMouseClicked(e -> {
			if(this.cf.getCib() != null) {
				if(this.tv.getSelectionModel() != null && this.tv.getSelectionModel().getSelectedItem() != null) {
					if(this.cf.getCib().getTextfield().getText() != "" && this.cf.getCib().getMB() != null &&
							this.cf.getCib().getDatePicker() != null) {
						CalendarSource cs = this.tv.getSelectionModel().getSelectedItem();
						TextField tf = this.cf.getCib().getTextfield();
						MenuButton mb = this.cf.getCib().getMB();
						DatePicker dp = this.cf.getCib().getDatePicker();
						
						if(tf.getText() != cs.getToDo().getInformation()) {
							cs.getToDo().setInfo(tf.getText());
							this.cf.getFm().getMM().update(cs.getToDo(), UpdateType.INFO, tf.getText());
						}
						
						if(ToDoPrio.convertString(mb.getText()) != cs.getToDo().getPrio()) {
							cs.getToDo().setPrio(ToDoPrio.convertString(mb.getText()));
							this.cf.getFm().getMM().update(cs.getToDo(), UpdateType.PRIORITY, ToDoPrio.convertString(mb.getText()).getPrioAsInt());
						}
						
						if(dp.getValue() != cs.getToDo().getDeadline().getLd()) {
							LocalDate ld = dp.getValue();
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
							cs.getToDo().setDealine(deadline);
							this.cf.getFm().getMM().update(cs.getToDo(), UpdateType.DEADLINE, deadline);
							
							this.cf.getCalendar().updateComplete();
							
							this.updateListForDate(cs, cs.getToDo().getDeadline().getLd().getDayOfMonth());
						}
						
						this.cf.getCg().update();
						
						this.updateSourceInTv(cs);
						
						tf.clear();
						mb.setText("Priorität");
						dp.setValue(null);
					}
				}
			}
		});
	}
	
	private void updateSourceInTv(CalendarSource cs) {
		if(cs != null && this.tv != null && this.tv.getItems() != null && this.tv.getItems().size() > 0) {
			ObservableList<CalendarSource> list = this.tv.getItems();
			
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i) != null && list.get(i).equals(cs)) {
					if(list.get(i).getToDo().getDeadline().equals(cs.getToDo().getDeadline())) {
						list.set(i, cs);
					}
				}
			}
			
			this.tv.setItems(list);
		}
	}
	
	private void updateListForDate(CalendarSource cs, int newDate) {
		if(cs != null && newDate != 0 && this.tv != null && this.tv.getItems() != null) {
			ObservableList<CalendarSource> list = FXCollections.observableArrayList();
			
			MonthCalendar mc = this.cf.getCalendar().getCalendarOfMonth(this.cf.getCg().getShowedMonth());
			
			if(mc.getSources().containsKey(newDate)) {
				Object[] o = mc.getSources().get(newDate);
				
				if(o != null) {
					for(Object obj : o) {
						if(obj != null) {
							list.add((CalendarSource) obj);
						}
					}
				}
			}
			
			this.tv.setItems(list);
		}
	}
}
