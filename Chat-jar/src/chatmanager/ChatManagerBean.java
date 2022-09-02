package chatmanager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import models.AppMessage;
import models.User;

// TODO Implement the rest of Client-Server functionalities 
/**
 * Session Bean implementation class ChatBean
 */
@Stateful
@LocalBean
public class ChatManagerBean implements ChatManagerRemote, ChatManagerLocal {

	private List<User> registered = new ArrayList<User>();
	private List<User> loggedIn = new ArrayList<User>();
	private List<AppMessage> messages = new ArrayList<AppMessage>();
	
	/**
	 * Default constructor.
	 */
	public ChatManagerBean() {
	}

	@Override
	public boolean register(User user) {
		registered.add(user);
		return true;
	}

	@Override
	public boolean login(String username, String password) {
		boolean exists = registered.stream().anyMatch(u->u.getUsername().equals(username) && u.getPassword().equals(password));
		if(exists)
			loggedIn.add(new User(username, password));
		return exists;
	}
	
	@Override
	public boolean logout(String username) {
		boolean exists = loggedIn.stream().anyMatch(u->u.getUsername().equals(username));
		if(exists)
			loggedIn.removeIf(u->u.getUsername().equals(username));
		return exists;
	}

	@Override
	public List<User> loggedInUsers() {
		return loggedIn;
	}
	
	@Override
	public List<User> registeredUsers() {
		return registered;
	}
	
	@Override
	public boolean isUserLoggedIn(String username) {
		for (User user : loggedIn) {
			if(user.getUsername() == username) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public User getRegisteredUser(String username) {
		for(User user : registered) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	@Override
	public List<AppMessage> getMessages() {
		return messages;
	}
	
	@Override
	public List<AppMessage> getMessagesOfUser(String username) {
		List<AppMessage> userMessages = new ArrayList<AppMessage>();
		for(AppMessage message : messages) {
			if(message.getSender().getUsername().equals(username)) {
				userMessages.add(message);
			}
		}
		return userMessages;
	}
	
	@Override
	public void addMessage(AppMessage message) {
		messages.add(message);
	}
}
