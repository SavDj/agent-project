package models;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class AppMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User receiver;
	private User sender;
	private LocalDateTime dateTime;
	private String subject;
	private String content;
	
	public AppMessage() {}

	public AppMessage(User receiver, User sender, LocalDateTime date, String subject, String content) {
		super();
		this.receiver = receiver;
		this.sender = sender;
		this.dateTime = date;
		this.subject = subject;
		this.content = content;
	}

	public User getReceiver() {
		return receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	public User getSender() {
		return sender;
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public LocalDateTime getDate() {
		return dateTime;
	}
	
	public void setDate(LocalDateTime date) {
		this.dateTime = date;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toJson(){
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
}
