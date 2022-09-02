package agents;

import java.util.HashMap;

import agentmanager.AID;

public interface CachedAgentsRemote {

	public HashMap<AID, Agent> getRunningAgents();
	public void addRunningAgent(Agent agent, AID key);
	public void removeRunningAgent(AID key);
}
