package service.AAADEVZangSpaces.GetMessages;

import com.roobroo.bpm.model.BpmNode;

public class MessagesModel extends BpmNode{

	public MessagesModel(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	
	private String spaceIdMessages;
	private String authorization;
	private String searchstring;
	private String size;
	
	public String getSpaceIdMessages() {
		return spaceIdMessages;
	}
	public void setSpaceIdMessages(String spaceIdMessages) {
		this.spaceIdMessages = spaceIdMessages;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getSearchstring() {
		return searchstring;
	}
	public void setSearchstring(String searchstring) {
		this.searchstring = searchstring;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
