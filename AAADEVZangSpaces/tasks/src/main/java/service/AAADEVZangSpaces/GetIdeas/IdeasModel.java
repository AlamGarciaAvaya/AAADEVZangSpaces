package service.AAADEVZangSpaces.GetIdeas;

import com.roobroo.bpm.model.BpmNode;

public class IdeasModel extends BpmNode{

	public IdeasModel(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	private String spaceIdIdeas;
	private String authorization;
	private String size;
	private String page;
	public String getSpaceIdIdeas() {
		return spaceIdIdeas;
	}
	public void setSpaceIdIdeas(String spaceIdIdeas) {
		this.spaceIdIdeas = spaceIdIdeas;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}

}
