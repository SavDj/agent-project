package agents;

import java.util.HashMap;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import agentmanager.AID;

/**
 * Session Bean implementation class CachedAgents
 */
@Singleton
@LocalBean
@Remote(CachedAgentsRemote.class)
public class CachedAgents implements CachedAgentsRemote{

	HashMap<AID, Agent> runningAgents;

	/**
	 * Default constructor.
	 */
	public CachedAgents() {
		runningAgents = new HashMap<>();
	}

	@Override
	public HashMap<AID, Agent> getRunningAgents() {
		return runningAgents;
	}

	@Override
	public void addRunningAgent(Agent agent, AID key) {
		runningAgents.put(key, agent);
	}

	@Override
	public void removeRunningAgent(AID key) {
		runningAgents.remove(key);
	}
	
}
