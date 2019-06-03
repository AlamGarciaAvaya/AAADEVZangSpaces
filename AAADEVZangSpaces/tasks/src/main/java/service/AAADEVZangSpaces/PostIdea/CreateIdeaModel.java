package service.AAADEVZangSpaces.PostIdea;

import com.roobroo.bpm.model.BpmNode;

public class CreateIdeaModel extends BpmNode{

	public CreateIdeaModel(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	
	private String spaceIdCreateIdea;
	private String autorization;
	private String bodyText;
	private String description;
	private String messageType;
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getSpaceIdCreateIdea() {
		return spaceIdCreateIdea;
	}
	public void setSpaceIdCreateIdea(String spaceIdCreateIdea) {
		this.spaceIdCreateIdea = spaceIdCreateIdea;
	}
	public String getAutorization() {
		return autorization;
	}
	public void setAutorization(String autorization) {
		this.autorization = autorization;
	}
	public String getBodyText() {
		return bodyText;
	}
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
