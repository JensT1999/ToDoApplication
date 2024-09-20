package application.utils.calendar.test;

import application.frames.calendar.CalendarGrid;
import application.utils.ToDoPrio;
import application.utils.ToDos;
import application.utils.calendar.MonthCalendar;
import application.utils.calendar.YearCalendar;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarGridTest extends Application {
	
	private static ObservableList<ToDos> list;

	@Override
	public void start(Stage primaryStage) throws Exception {
		list = FXCollections.observableArrayList();
		YearCalendar yc = new YearCalendar();
		fillList();
		yc.loadListIntoCalendar(list);
//		CalendarGrid grid = new CalendarGrid(yc);
		
//		Scene scene = new Scene(grid);
		
//		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private static void fillList() {
		for(int i = 1; i < 10; i++) {
			ToDos td = new ToDos(0, "müll", ToDoPrio.HIGH, (10 + i) + ".04.2024");
			list.add(td);
		}
		
		ToDos td = new ToDos(0, "jens", ToDoPrio.HIGH, "11.04.2024");
		list.add(td);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
