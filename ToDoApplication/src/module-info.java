module ToDoApplication {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens application.frames to javafx.graphics, javafx.fxml, javafx.base;
	opens application.frames.calendar to javafx.graphics, javafx.fxml, javafx.base;
	opens application.utils to javafx.graphics, javafx.fxml, javafx.base;
	opens application.utils.calendar to javafx.graphics, javafx.fxml, javafx.base;
	opens application.utils.calendar.test to javafx.graphics, javafx.fxml, javafx.base;
}
