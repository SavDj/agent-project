package chatmanager;

import java.util.List;

import javax.ejb.Remote;

import models.AppMessage;
import models.User;

@Remote
public interface ChatManagerRemote {

	public boolean login(String username, String password);
	
	public boolean logout(String username);

	public boolean register(User user);

	public List<User> loggedInUsers();
	
	public List<User> registeredUsers();
	
	public User getRegisteredUser(String username);
	
	public List<AppMessage> getMessages();
	
	public List<AppMessage> getMessagesOfUser(String username);
	
	public void addMessage(AppMessage message);

	boolean isUserLoggedIn(String username);
}
