package service.AAADEVZangSpaces.GetAllTasks;

import com.roobroo.bpm.model.BpmNode;

public class TasksModel extends BpmNode {

	private static final long serialVersionUID = 1L;

	public TasksModel(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}
	
	private String spaceId;
	private String authorization;

	public String getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	
}
