package messagemanager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import agentmanager.AID;

public class ACLMessage implements Serializable{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private Performative performative;
	private AID sender;
	private List<AID> receivers;
	private AID replyTo;
	private String content;
	private Serializable contentObj;
	private Map<String, Serializable> userArgs;
	private String language;
	private String ontology;
	private String encoding;
	private String protocol;
	private long replyBy;
	private String inReplyTo;
	private String replyWith;
	
	public ACLMessage() {}
	
	public ACLMessage(Performative performative, AID sender, List<AID> receivers, AID replyTo, String content,
			Serializable contentObj, Map<String, Serializable> userArgs, String language, String ontology,
			String encoding, String protocol, long replyBy, String inReplyTo, String replyWith) {
		super();
		this.performative = performative;
		this.sender = sender;
		this.receivers = receivers;
		this.replyTo = replyTo;
		this.content = content;
		this.contentObj = contentObj;
		this.userArgs = userArgs;
		this.language = language;
		this.ontology = ontology;
		this.encoding = encoding;
		this.protocol = protocol;
		this.replyBy = replyBy;
		this.inReplyTo = inReplyTo;
		this.replyWith = replyWith;
	}
	
	public Performative getPerformative() {
		return performative;
	}
	public void setPerformative(Performative performative) {
		this.performative = performative;
	}
	public AID getSender() {
		return sender;
	}
	public void setSender(AID sender) {
		this.sender = sender;
	}
	public List<AID> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<AID> receivers) {
		this.receivers = receivers;
	}
	public AID getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(AID replyTo) {
		this.replyTo = replyTo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Serializable getContentObj() {
		return contentObj;
	}
	public void setContentObj(Serializable contentObj) {
		this.contentObj = contentObj;
	}
	public Map<String, Serializable> getUserArgs() {
		return userArgs;
	}
	public void setUserArgs(Map<String, Serializable> userArgs) {
		this.userArgs = userArgs;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getOntology() {
		return ontology;
	}
	public void setOntology(String ontology) {
		this.ontology = ontology;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public long getReplyBy() {
		return replyBy;
	}
	public void setReplyBy(long replyBy) {
		this.replyBy = replyBy;
	}
	public String getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public String getReplyWith() {
		return replyWith;
	}
	public void setReplyWith(String replyWith) {
		this.replyWith = replyWith;
	}

}
