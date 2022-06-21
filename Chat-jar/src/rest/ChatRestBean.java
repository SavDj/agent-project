package rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import messagemanager.AgentMessage;
import messagemanager.MessageManagerRemote;
import models.AppMessage;
import models.User;

@Stateless
@Path("/chat")
public class ChatRestBean implements ChatRest {

	@EJB
	private MessageManagerRemote messageManager;
	
	@Override
	public void register(User user) {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("receiver", "chat");
		message.userArgs.put("command", "REGISTER");
		message.userArgs.put("username", user.getUsername());
		message.userArgs.put("password", user.getPassword());
		
		messageManager.post(message);
	}

	@Override
	public void login(User user) {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("receiver", "chat");
		message.userArgs.put("command", "LOG_IN");
		message.userArgs.put("username", user.getUsername());
		message.userArgs.put("password", user.getPassword());
		
		messageManager.post(message);
	}

	@Override
	public void getloggedInUsers(String username) {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("receiver", username);
		message.userArgs.put("command", "GET_LOGGEDIN");
		
		messageManager.post(message);
	}
	
	@Override
	public void getregisteredUsers(String username) {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("receiver", username);
		message.userArgs.put("command", "GET_REGISTERED");
		
		messageManager.post(message);
	}
	
	@Override
	public void sendAllMessage(AppMessage appMessage) {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("receiver", appMessage.getReceiver());
		message.userArgs.put("sender", appMessage.getSender());
		message.userArgs.put("content", appMessage.getContent());
		message.userArgs.put("subject", appMessage.getSubject());
		message.userArgs.put("command", "SEND_MESSAGE_ALL");
		
		messageManager.post(message);
	}
	
	@Override
	public void sendUserMessage(AppMessage appMessage) {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("receiver", appMessage.getReceiver());
		message.userArgs.put("sender", appMessage.getSender());
		message.userArgs.put("content", appMessage.getContent());
		message.userArgs.put("subject", appMessage.getSubject());
		message.userArgs.put("command", "SEND_MESSAGE_ONE");
		
		messageManager.post(message);
	}
	
	@Override
	public void getUserMessages() {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("receiver", "chat");
		message.userArgs.put("command", "GET_USER_MESSAGES");
		
		messageManager.post(message);
	}
	
	@Override
	public void logOut(User user) {
		AgentMessage message = new AgentMessage();
		message.userArgs.put("command", "LOG_OUT");
		message.userArgs.put("username", user.getUsername());
		
		messageManager.post(message);
	}

}
