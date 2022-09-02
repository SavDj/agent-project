package agentmanager;

import javax.ejb.Remote;

import agents.Agent;

@Remote
public interface AgentManagerRemote {
	public AID startAgent(String name);
	public void stopAgent(AID agentId);
	public Agent getAgentById(AID agentId);
}
