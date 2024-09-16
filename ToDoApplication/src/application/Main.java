package application;
	
import application.frames.ToDoFrame;
import application.utils.ToDoPrio;
import application.utils.ToDos;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	
	private ToDoFrame tdf;
	
	@Override
	public void init() throws Exception {
		ToDos td = new ToDos("Müll", ToDoPrio.HIGHEST, "10.10.2024");
		this.tdf = new ToDoFrame();
		
		this.tdf.getToDoList().addToList(td);
		
		this.tdf.getTvb().updateComplete();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(this.tdf, 700, 500);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
