module ToDoApplication {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens application.frames to javafx.graphics, javafx.fxml, javafx.base;
	opens application.utils to javafx.graphics, javafx.fxml, javafx.base;
}
