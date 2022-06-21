package rest;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import models.AppMessage;
import models.User;

@Remote
public interface ChatRest {
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public void register(User user);
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public void login(User user);
	
	@GET
	@Path("/loggedIn")
	public void getloggedInUsers(String username);
	
	@GET
	@Path("/registered")
	public void getregisteredUsers(String username);
	
	@POST
	@Path("/messages/all")
	public void sendAllMessage(AppMessage appMessage);
	
	@POST
	@Path("/messages/user")
	public void sendUserMessage(AppMessage appMessage);
	
	@GET
	@Path("/messages/{userId}")
	public void getUserMessages();
	
	@DELETE
	@Path("/users/loggedIn/{userId}")
	public void logOut(User user);
}
