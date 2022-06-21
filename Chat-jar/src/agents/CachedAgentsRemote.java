package agents;

import java.util.HashMap;

public interface CachedAgentsRemote {

	public HashMap<String, Agent> getRunningAgents();
	public void addRunningAgent(Agent agent, String key);
	public void removeRunningAgent(String key);
}
