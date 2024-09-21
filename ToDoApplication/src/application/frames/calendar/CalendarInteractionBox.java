package application.frames.calendar;


import application.frames.utils.SwitchButton;
import application.utils.ToDoPrio;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class CalendarInteractionBox extends GridPane {
	
	private CalendarFrame cf;
	
	private ColumnConstraints cl1;
	private ColumnConstraints cl2;
	private ColumnConstraints cl3;
	private ColumnConstraints cl4;
	
	private MonthChangeButton mcb;
	
	private YearChangeButton ycb;
	
	private TextField tf1;
	private MenuButton mb;
	private MenuItem mi1;
	private MenuItem mi2;
	private MenuItem mi3;
	private MenuItem mi4;
	private MenuItem mi5;
	private DatePicker dp;
		
	public CalendarInteractionBox(CalendarFrame cf) {
		this.cf = cf;
		
		this.cl1 = new ColumnConstraints();
		this.cl2 = new ColumnConstraints();
		this.cl3 = new ColumnConstraints();
		this.cl4 = new ColumnConstraints();
		
		this.mcb = new MonthChangeButton(this.cf);
		
		this.ycb = new YearChangeButton(this.cf);
		
		this.tf1 = new TextField();
		this.mb = new MenuButton("Priorität");
		this.mi1 = new MenuItem(ToDoPrio.HIGHEST.toString());
		this.mi2 = new MenuItem(ToDoPrio.HIGH.toString());
		this.mi3 = new MenuItem(ToDoPrio.MIDDLE.toString());
		this.mi4 = new MenuItem(ToDoPrio.LOW.toString());
		this.mi5 = new MenuItem(ToDoPrio.LOWEST.toString());
		this.dp = new DatePicker();
				
		this.buildBox();
	}
	
	public TextField getTextfield() {
		return tf1;
	}
	
	public MenuButton getMB() {
		return mb;
	}
	
	public DatePicker getDatePicker() {
		return dp;
	}

	private void buildBox() {
		this.cl1.setPrefWidth(200);
		this.cl2.setPrefWidth(200);
		this.cl3.setPrefWidth(200);
		this.cl4.setPrefWidth(200);
						
		this.getColumnConstraints().addAll(cl1, cl2, cl3, cl4);
		
		GridPane.setMargin(mcb, new Insets(5));
		
		GridPane.setMargin(ycb, new Insets(5));
		
		this.tf1.setPrefWidth(150);
		GridPane.setMargin(tf1, new Insets(5));
				
		this.mb.setPrefWidth(150);
		this.mi1.setOnAction(e -> {
			if(this.mb != null) {
				this.mb.setText(this.mi1.getText());
			}
		});
		
		this.mi2.setOnAction(e -> {
			if(this.mb != null) {
				this.mb.setText(this.mi2.getText());
			}
		});
		
		this.mi3.setOnAction(e -> {
			if(this.mb != null) {
				this.mb.setText(this.mi3.getText());
			}
		});	
		
		this.mi4.setOnAction(e -> {
			if(this.mb != null) {
				this.mb.setText(this.mi4.getText());
			}
		});		
		
		this.mi5.setOnAction(e -> {
			if(this.mb != null) {
				this.mb.setText(this.mi5.getText());
			}
		});
		
		this.mb.getItems().addAll(mi1, mi2, mi3, mi4, mi5);
		
		GridPane.setMargin(mb, new Insets(5));
				
		this.dp.setMaxWidth(150);
		
		GridPane.setMargin(dp, new Insets(5));
				
		this.add(mcb, 1, 0);
		this.add(tf1, 0, 1);
		this.add(mb, 1, 1);
		this.add(dp, 2, 1);
		this.add(ycb, 3, 0);
	}
}
