package application.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class ToDoList {

	private ObservableList<ToDos> list;
	
	public ToDoList() {
		this.list = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
	}
	
	public void addToList(ToDos td) {
		if(!this.list.contains(td)) {
			this.list.add(td);
		}
	}
	
	public void removeFromList(ToDos td) {
		if(this.list.contains(td)) {
			for(int i = 0; i < this.list.size(); i++) {
				if(this.list.get(i).equals(td)) {
					this.list.remove(i);
				}
			}
		}
	}
	
	public void removeCheckedOnes() {
		if(this.list != null && this.list.size() > 0) {
			ArrayList<ToDos> removeOnes = new ArrayList<ToDos>();
			
			for(int i = 0; i < this.list.size(); i++) {
				if(this.list.get(i) != null) {
					ToDos td = this.list.get(i);
					
					if(td.getCb().isSelected()) {
						removeOnes.add(td);
					}
				}
			}
			
			for(int i = 0; i < removeOnes.size(); i++) {
				if(removeOnes.get(i) != null) {
					ToDos td = removeOnes.get(i);
					if(this.list.contains(td)) {
						this.removeFromList(td);
					}
				}
			}
		}
	}
	
	public void sort(SortType type) {
		if(this.list != null && this.list.size() > 0) {
			this.list = Utils.sort(this.list, type);
		}
	}
	
	public ObservableList<ToDos> getList() {
		return list;
	}
}
