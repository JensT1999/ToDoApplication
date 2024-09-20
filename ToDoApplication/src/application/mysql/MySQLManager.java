package application.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.utils.SortType;
import application.utils.ToDoPrio;
import application.utils.ToDos;
import application.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQLManager {
	
	private MySQL db;
	
	public MySQLManager(MySQL db) {
		this.db = db;
	}
	
	public ObservableList<ToDos> readDBAsObservableList(){
		if(this.db.isConnected()) {
			ObservableList<ToDos> list = FXCollections.observableArrayList();
			PreparedStatement ps = this.db.prepareStatement("SELECT * FROM todos");
			ResultSet rs = this.db.executeQuery(ps);
			
			try {
				if(rs != null) {
					while(rs.next()) {
						int id = rs.getInt("id");
						int selected = rs.getInt("selected");
						String info = rs.getString("info");
						ToDoPrio prio = ToDoPrio.convertInt(rs.getInt("priority"));
						String deadline = rs.getString("deadline");
						
						ToDos td = new ToDos(id, selected, info, prio, deadline);
						
						list.add(td);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return Utils.sort(list, SortType.BY_PRIO);
		}
		return null;
	}
	
	public void addToDb(ToDos td) {
		try {
			if(td != null) {
				if(this.db.isConnected()) {
					PreparedStatement ps = this.db.prepareStatement("INSERT INTO todos VALUES (?, ?, ?, ?, ?)");
					ps.setInt(1, td.getId());
					ps.setInt(2, td.getSelection());
					ps.setString(3, td.getInformation());
					ps.setInt(4, td.getPrio().getPrioAsInt());
					ps.setString(5, td.getDeadlineAsString());
					
					this.db.executeUpdate(ps);
					ps.close();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromDb(ToDos td) {
		try {
			if(td != null) {
				if(this.db.isConnected()) {
					PreparedStatement ps = this.db.prepareStatement("DELETE FROM todos WHERE id=?");
					ps.setInt(1, td.getId());
					
					this.db.executeUpdate(ps);
					ps.close();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ToDos td, UpdateType type, Object newValue) {
		try {
			if(td != null && type != null && newValue != null) {
				if(this.db.isConnected()) {
					PreparedStatement ps = null;
					
					switch (type) {
						case UpdateType.SELECTED -> {
							ps = this.db.prepareStatement("UPDATE todos SET selected=? WHERE id=?");
							ps.setInt(1, (int) newValue);
							ps.setInt(2, td.getId());
						}
						case UpdateType.INFO -> {
							ps = this.db.prepareStatement("UPDATE todos SET info=? WHERE id=?");
							ps.setString(1, (String) newValue);
							ps.setInt(2, td.getId());
						}
						case UpdateType.PRIORITY -> {
							ps = this.db.prepareStatement("UPDATE todos SET priority=? WHERE id=?");
							ps.setInt(1, (int) newValue);
							ps.setInt(2, td.getId());
						}
						case UpdateType.DEADLINE -> {
							ps = this.db.prepareStatement("UPDATE todos SET deadline=? WHERE id=?");
							ps.setString(1, (String) newValue);
							ps.setInt(2, td.getId());
						}
					}
					
					if(ps != null) {
						this.db.executeUpdate(ps);
					}
					
					ps.close();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public MySQL getDb() {
		return db;
	}
}
