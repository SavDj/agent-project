package agents;

import java.io.Serializable;

import javax.jms.Message;

import agentmanager.AID;
import messagemanager.ACLMessage;

public interface Agent extends Serializable {

	public AID init();
	public void handleMessage(ACLMessage message);
	public AID getAgentId();
}
