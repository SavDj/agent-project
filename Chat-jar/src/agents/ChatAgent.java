package agents;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import chatmanager.ChatManagerRemote;
import messagemanager.AgentMessage;
import messagemanager.MessageManagerRemote;
import models.AppMessage;
import models.User;
import util.JNDILookup;
import ws.WSChat;

@Stateful
@Remote(Agent.class)
public class ChatAgent implements Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agentId;

	@EJB
	private ChatManagerRemote chatManager;
	@EJB
	private CachedAgentsRemote cachedAgents;
	@EJB
	private WSChat ws;

	@PostConstruct
	public void postConstruct() {
		System.out.println("Created Chat Agent!");
	}

	//private List<String> chatClients = new ArrayList<String>();

	protected MessageManagerRemote msm() {
		return (MessageManagerRemote) JNDILookup.lookUp(JNDILookup.MessageManagerLookup, MessageManagerRemote.class);
	}

	@Override
	public void handleMessage(Message message) {
		TextMessage tmsg = (TextMessage) message;

		String receiver;
		try {
			receiver = (String) tmsg.getObjectProperty("receiver");
			if (agentId.equals(receiver)) {
				String option = "";
				String response = "";
				try {
					option = (String) tmsg.getObjectProperty("command");
					switch (option) {
					case "REGISTER":
						String username = (String) tmsg.getObjectProperty("username");
						String password = (String) tmsg.getObjectProperty("password");
						
						User newUser = new User();
						newUser.setPassword(password);
						newUser.setUsername(username);
						if(chatManager.register(newUser)) {
							ws.sendText(agentId, "REGISTER:SUCCESS" + username);
						} else {
							ws.sendText(agentId, "REGISTER:FAILURE" + username);
						}
						break;
					case "LOG_IN":
						username = (String) tmsg.getObjectProperty("username");
						password = (String) tmsg.getObjectProperty("password");
						
						if(chatManager.login(username, password)) {
							ws.sendText(receiver, "LOG_IN:SUCCESS" + username);
						} else {
							ws.sendText(receiver, "LOG_IN:FAILURE" + username);
						}
						break;
					case "GET_LOGGED_IN":
						username = (String) tmsg.getObjectProperty("username");
						List<User> loggedInUsers = chatManager.loggedInUsers();
						
						if(ws.getLoggedInUsername(agentId) != null) {
							for(User user : loggedInUsers) {
								ws.sendText(receiver, "LUSER:" + user.getUsername());
							}
						}

						break;
					case "LOG_OUT":
						username = (String) tmsg.getObjectProperty("username");
						password = (String) tmsg.getObjectProperty("password");
						
						chatManager.logout(username);
						ws.sendText(receiver, "LOG_OUT" + username);

						break;
					case "GET_REGISTERED":
						username = (String) tmsg.getObjectProperty("username");
						List<User> registeredUsers = chatManager.registeredUsers();
						
						if(ws.getLoggedInUsername(agentId) != null) {
							for(User user : registeredUsers) {
								ws.sendText(receiver, "RUSER:" + user.getUsername());
							}
						}

						break;
					case "SEND_MESSAGE_ONE":
						String content = (String) tmsg.getObjectProperty("content");
						String sender = (String) tmsg.getObjectProperty("sender");
						String subject = (String) tmsg.getObjectProperty("subject");
						
						User senderUser = chatManager.getRegisteredUser(sender);
						User receiverUser = chatManager.getRegisteredUser(receiver);
						
						AppMessage msg = new AppMessage(receiverUser, senderUser, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), subject, content);
						
						ws.sendMessage(receiver, msg);

						break;
					case "SEND_MESSAGE_ALL":
						AppMessage msgAll = new AppMessage();
						msgAll.setContent((String) tmsg.getObjectProperty("content"));
						msgAll.setSender(chatManager.getRegisteredUser((String) tmsg.getObjectProperty("sender")));
						msgAll.setSubject((String) tmsg.getObjectProperty("subject"));
						msgAll.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
						
						for(User loggedInUser : chatManager.loggedInUsers()) {
							msgAll.setReceiver(loggedInUser);
							ws.sendMessage(receiver, msgAll);
						}

						break;
					case "GET_USER_MESSAGES":
						username = (String) tmsg.getObjectProperty("username");
						List<AppMessage> userMessages = new ArrayList<AppMessage>();
						for(AppMessage userMsg : userMessages) {
							ws.sendMessage(receiver, userMsg);
						}

						break;
					case "x":
						break;
					default:
						response = "ERROR!Option: " + option + " does not exist.";
						break;
					}
					System.out.println(response);
					//ws.onMessage("chat", response);
					
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String init() {
		agentId = "chat";
		cachedAgents.addRunningAgent(this, agentId);
		return agentId;
	}

	@Override
	public String getAgentId() {
		return agentId;
	}
}
