package service.AAADEVZangSpaces.PostTask;

import com.roobroo.bpm.model.BpmNode;

public class CreateTaskModel extends BpmNode{

	public CreateTaskModel(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	private String spaceIdeCreateTask;
	private String authorizationCreateTask;
	private String bodyTextCreateTask;
	private String descriptionCreateTask;
	private String assignessCreateTask;
	private String dueDateCreateTask;
	private String messageTypeCreateTask;
	
	public String getSpaceIdeCreateTask() {
		return spaceIdeCreateTask;
	}
	public void setSpaceIdeCreateTask(String spaceIdeCreateTask) {
		this.spaceIdeCreateTask = spaceIdeCreateTask;
	}
	public String getAuthorizationCreateTask() {
		return authorizationCreateTask;
	}
	public void setAuthorizationCreateTask(String authorizationCreateTask) {
		this.authorizationCreateTask = authorizationCreateTask;
	}
	public String getBodyTextCreateTask() {
		return bodyTextCreateTask;
	}
	public void setBodyTextCreateTask(String bodyTextCreateTask) {
		this.bodyTextCreateTask = bodyTextCreateTask;
	}
	public String getDescriptionCreateTask() {
		return descriptionCreateTask;
	}
	public void setDescriptionCreateTask(String descriptionCreateTask) {
		this.descriptionCreateTask = descriptionCreateTask;
	}
	public String getAssignessCreateTask() {
		return assignessCreateTask;
	}
	public void setAssignessCreateTask(String assignessCreateTask) {
		this.assignessCreateTask = assignessCreateTask;
	}
	public String getDueDateCreateTask() {
		return dueDateCreateTask;
	}
	public void setDueDateCreateTask(String dueDateCreateTask) {
		this.dueDateCreateTask = dueDateCreateTask;
	}
	public String getMessageTypeCreateTask() {
		return messageTypeCreateTask;
	}
	public void setMessageTypeCreateTask(String messageTypeCreateTask) {
		this.messageTypeCreateTask = messageTypeCreateTask;
	}

}
