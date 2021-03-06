package agents;

import java.util.HashMap;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class CachedAgents
 */
@Singleton
@LocalBean
@Remote(CachedAgentsRemote.class)
public class CachedAgents implements CachedAgentsRemote{

	HashMap<String, Agent> runningAgents;

	/**
	 * Default constructor.
	 */
	public CachedAgents() {

		runningAgents = new HashMap<>();
	}

	@Override
	public HashMap<String, Agent> getRunningAgents() {
		return runningAgents;
	}

	@Override
	public void addRunningAgent(Agent agent, String key) {
		runningAgents.put(key, agent);
	}

	@Override
	public void removeRunningAgent(String key) {
		runningAgents.remove(key);
		
	}
	
}
