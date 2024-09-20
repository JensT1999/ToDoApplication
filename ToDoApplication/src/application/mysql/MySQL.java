package application.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.frames.ToDoFrame;

public class MySQL {
	
	private String url;
	private String username;
	private String password;
		
	private Connection con;
	
	private ExecutorService exe;
	
	public MySQL(String url, String username, String pw) {
		this.url = url;
		this.username = username;
		this.password = pw;
		
		this.exe = Executors.newCachedThreadPool();
	}
	
	public void connect() {
		try {
			this.con = DriverManager.getConnection(this.url, this.username, this.password);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		if(this.con != null) {
			return true;
		}
		
		return false;
	}
	
	public PreparedStatement prepareStatement(String execution) {
		try {
			if(this.isConnected()) {
				PreparedStatement ps = this.con.prepareStatement(execution);
				return ps;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean executeUpdate(PreparedStatement ps) {
		try {
			if(this.isConnected()) {
				Future<Boolean> future = this.exe.submit(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return ps.execute();
					}
				});
				
				return future.get();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ResultSet executeQuery(String execution) {
		try {
			Future<ResultSet> future = this.exe.submit(new Callable<ResultSet>() {

				@Override
				public ResultSet call() throws Exception {
					return con.prepareStatement(execution).executeQuery();
				}
			});
			
			return future.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet executeQuery(PreparedStatement ps) {
		try {
			Future<ResultSet> future = this.exe.submit(new Callable<ResultSet>() {

				@Override
				public ResultSet call() throws Exception {
					return ps.executeQuery();
				}
			});
			
			return future.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Connection getCon() {
		return con;
	}
	
}
