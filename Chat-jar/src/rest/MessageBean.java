package rest;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import messagemanager.ACLMessage;
import models.AppMessage;
import models.User;

@Remote
public interface MessageBean {
	@POST
	@Path("/message")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMessage(ACLMessage message);

}
