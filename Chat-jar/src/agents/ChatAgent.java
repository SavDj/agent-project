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

import agentmanager.AID;
import chatmanager.ChatManagerRemote;
import messagemanager.ACLMessage;
import messagemanager.AgentMessage;
import messagemanager.MessageManagerRemote;
import messagemanager.Performative;
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
	private AID id;

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
	public void handleMessage(ACLMessage message) {
		Performative performative = message.getPerformative();
		switch (performative) {
			case REGISTER: {
				String username = message.getUserArgs().get("username").toString();
				String password = message.getUserArgs().get("password").toString();

				User newUser = new User();
				newUser.setPassword(password);
				newUser.setUsername(username);
				if (chatManager.register(newUser)) {
					ws.sendText(username, "REGISTER:SUCCESS" + username);
				} else {
					ws.sendText(username, "REGISTER:FAILURE" + username);
				}
				break;
			}
			case LOG_IN: {
				String username = message.getUserArgs().get("username").toString();
				String password = message.getUserArgs().get("password").toString();

				if (chatManager.login(username, password)) {
					ws.sendText(username, "LOG_IN:SUCCESS" + username);
				} else {
					ws.sendText(username, "LOG_IN:FAILURE" + username);
				}
				break;
			}
			case GET_LOGGED_IN: {
				String username = message.getUserArgs().get("username").toString();
				String password = message.getUserArgs().get("password").toString();

				if (chatManager.isUserLoggedIn(username)) {
					for (User user : chatManager.loggedInUsers()) {
						ws.sendText(username, "LUSER:" + user.getUsername());
					}
				}

				break;
			}
			case LOG_OUT: {
				String username = message.getUserArgs().get("username").toString();
				String password = message.getUserArgs().get("password").toString();

				chatManager.logout(username);
				ws.sendText(username, "LOG_OUT" + username);

				break;
			}
			case GET_REGISTERED: {
				String username = message.getUserArgs().get("username").toString();
				List<User> registeredUsers = chatManager.registeredUsers();

				if (chatManager.isUserLoggedIn(username)) {
					for (User user : registeredUsers) {
						ws.sendText(username, "RUSER:" + user.getUsername());
					}
				}

				break;
			}
			case SEND_MESSAGE_ONE: {
				String content = message.getUserArgs().get("content").toString();
				String sender = message.getUserArgs().get("sender").toString();
				String subject = message.getUserArgs().get("subject").toString();
				String receiver = message.getUserArgs().get("receiver").toString();

				User senderUser = chatManager.getRegisteredUser(sender);
				User receiverUser = chatManager.getRegisteredUser(receiver);

				AppMessage msg = new AppMessage(receiverUser, senderUser,
						LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), subject, content);

				ws.sendMessage(receiver, msg);

				break;
			}
			case SEND_MESSAGE_ALL: {
				AppMessage appMessage = new AppMessage();
				appMessage.setContent(message.getUserArgs().get("content").toString());
				appMessage.setSender(chatManager.getRegisteredUser(message.getUserArgs().get("sender").toString()));
				appMessage.setSubject(message.getUserArgs().get("subject").toString());
				appMessage.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
				
				String receiver = message.getUserArgs().get("receiver").toString();

				for (User user : chatManager.registeredUsers()) {
					appMessage.setReceiver(user);
					ws.sendMessage(user.getUsername(), appMessage);
				}
				
				break;
			}
			case GET_USER_MESSAGES: {
				String username = message.getUserArgs().get("username").toString();
				String receiver = message.getUserArgs().get("receiver").toString();
				List<AppMessage> userMessages = chatManager.getMessagesOfUser(username);
				for (AppMessage userMsg : userMessages) {
					ws.sendMessage(receiver, userMsg);
				}

				break;
			}
			default:
				return;
		}
	}

	@Override
	public AID init() {
		cachedAgents.addRunningAgent(this, id);
		return id;
	}

	@Override
	public AID getAgentId() {
		return id;
	}
}
