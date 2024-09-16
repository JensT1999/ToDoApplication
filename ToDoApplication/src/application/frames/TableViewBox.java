package application.frames;

import application.utils.ToDos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewBox extends TableView<ToDos> {
	
	private ToDoFrame tdf;
	
	private TableColumn<ToDos, CheckBox> tc1;
	private TableColumn<ToDos, String> tc2;
	private TableColumn<ToDos, String> tc3;
	private TableColumn<ToDos, String> tc4;
	
	public TableViewBox(ToDoFrame tdf) {
		this.tdf = tdf;
		
		this.tc1 = new TableColumn<ToDos, CheckBox>("Erledigt");
		this.tc2 = new TableColumn<ToDos, String>("ToDO");
		this.tc3 = new TableColumn<ToDos, String>("Priorität");
		this.tc4 = new TableColumn<ToDos, String>("Deadline");
		
		this.setItems(this.tdf.getToDoList().getList());
		
		this.buildBox();
	}
	
	public void updateComplete() {
		if(this.getItems() != null) {
			this.setItems(this.tdf.getToDoList().getList());
		}
	}

	private void buildBox() {
		this.setEditable(false);
		
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		this.tc1.setCellValueFactory(new PropertyValueFactory<ToDos, CheckBox>("cb"));
		this.tc2.setCellValueFactory(new PropertyValueFactory<ToDos, String>("information"));
		this.tc3.setCellValueFactory(new PropertyValueFactory<ToDos, String>("prioAsString"));
		this.tc4.setCellValueFactory(new PropertyValueFactory<ToDos, String>("deadlineAsString"));
		
		this.getColumns().addAll(tc1, tc2, tc3, tc4);
	}
}
