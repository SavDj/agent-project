package agentmanager;

import java.io.Serializable;

import agents.AgentType;
import models.AgentCenter;

public class AID implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private AgentCenter host;
	private AgentType agentType;
	
	public AID() {}
	
	public AID(String name, AgentCenter host, AgentType agentType) {
		super();
		this.name = name;
		this.host = host;
		this.agentType = agentType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AgentCenter getHost() {
		return host;
	}
	public void setHost(AgentCenter host) {
		this.host = host;
	}
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}
}
