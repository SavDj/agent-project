package rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import messagemanager.ACLMessage;
import messagemanager.AgentMessage;
import messagemanager.MessageManagerRemote;
import models.AppMessage;
import models.User;

@Stateless
@Path("/chat")
public class MessageRestBean implements MessageBean {

	@EJB
	private MessageManagerRemote messageManager;
	
	public void sendMessage(ACLMessage message) {
		messageManager.post(message);
	}
}
