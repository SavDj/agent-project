package ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import agentmanager.AID;
import agentmanager.AgentManagerBean;
import agentmanager.AgentManagerRemote;
import agents.AgentType;
import chatmanager.ChatManagerBean;
import chatmanager.ChatManagerRemote;
import models.AgentCenter;
import models.AppMessage;
import util.JNDILookup;

@Singleton
@ServerEndpoint("/ws/{username}")
public class WSChat {
	private Map<String, Session> sessions = new HashMap<String, Session>();
	private AgentManagerRemote agentManager = JNDILookup.lookUp(JNDILookup.AgentManagerLookup, AgentManagerBean.class);
	private ChatManagerRemote chatManager = JNDILookup.lookUp(JNDILookup.ChatManagerLookup, ChatManagerBean.class);
	
	@OnOpen
	public void onOpen(@PathParam("username") String username, Session session) {
		sessions.put(username, session);
		agentManager.startAgent(JNDILookup.ChatAgentLookup);
	}
	
	@OnClose
	public void onClose(@PathParam("username") String username, Session session) {
		sessions.remove(username);
		chatManager.logout(username);
		AID agentId = new AID(username, new AgentCenter(), new AgentType("CHAT_AGENT"));
		agentManager.stopAgent(agentId);
	}
	
	@OnError
	public void onError(@PathParam("username") String username, Session session, Throwable t) {
		sessions.remove(username);
		AID agentId = new AID(username, new AgentCenter(), new AgentType("CHAT_AGENT"));
		agentManager.stopAgent(agentId);
		chatManager.logout(username);
		t.printStackTrace();
	}
	
	public void sendText(String receiver, String message) {
		Session session = sessions.get(receiver);
		if(session != null && session.isOpen()) {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String receiver, AppMessage message) {
		Session session = sessions.get(receiver);
		if(session != null && session.isOpen()) {
			try {
				session.getBasicRemote().sendText(message.toJson());
				chatManager.addMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
