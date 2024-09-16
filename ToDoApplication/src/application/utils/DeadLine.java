package application.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DeadLine {
	
	private String deadLine;
	private DateTimeFormatter formatter;
	private LocalDate ld;
	
	public DeadLine(String deadline) {
		this.deadLine = deadline;
		this.formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		this.ld = LocalDate.parse(this.deadLine, this.formatter);
	}
	
	public String getDeadLine() {
		return deadLine;
	}
	
	public LocalDate getLd() {
		return ld;
	}
	
	public int getDistanceToDeadLine() {
		Period period = Period.between(LocalDate.now(), this.ld);
		
		return period.getDays();
	}
}
