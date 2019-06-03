package service.AAADEVZangSpaces.PostChat;

import com.roobroo.bpm.model.BpmNode;

public class CreateChatModel extends BpmNode{

	public CreateChatModel(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	
	private String spaceIdCreateMessage;
	private String authorizationCreateMessage;
	private String bodyTextCreateMessage;
	private String messageTypeCreateMessage;
	
	public String getSpaceIdCreateMessage() {
		return spaceIdCreateMessage;
	}
	public void setSpaceIdCreateMessage(String spaceIdCreateMessage) {
		this.spaceIdCreateMessage = spaceIdCreateMessage;
	}
	public String getAuthorizationCreateMessage() {
		return authorizationCreateMessage;
	}
	public void setAuthorizationCreateMessage(String authorizationCreateMessage) {
		this.authorizationCreateMessage = authorizationCreateMessage;
	}
	public String getBodyTextCreateMessage() {
		return bodyTextCreateMessage;
	}
	public void setBodyTextCreateMessage(String bodyTextCreateMessage) {
		this.bodyTextCreateMessage = bodyTextCreateMessage;
	}
	public String getMessageTypeCreateMessage() {
		return messageTypeCreateMessage;
	}
	public void setMessageTypeCreateMessage(String messageTypeCreateMessage) {
		this.messageTypeCreateMessage = messageTypeCreateMessage;
	}
}
