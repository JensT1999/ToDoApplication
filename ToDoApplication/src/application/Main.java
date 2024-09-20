package application;
	
import application.frames.utils.FrameManager;
import application.mysql.MySQL;
import application.mysql.MySQLManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private MySQL db;
	private MySQLManager mm;
	private FrameManager fm;
	
	@Override
	public void init() throws Exception {
		this.db = new MySQL("jdbc:mysql://localhost/todolist", "root", "");
		this.db.connect();
		
		this.mm = new MySQLManager(this.db);
		
		this.fm = new FrameManager(this.mm, 700, 500);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setScene(this.fm.getScene());
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
