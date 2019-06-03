package service.AAADEVZangSpaces.GetMembers;

import com.roobroo.bpm.model.BpmNode;

public class MembersModel extends BpmNode{

	public MembersModel(String name, String id) {
		super(name, id);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	private String spaceIdMembers;
	private String authorization;
	private String size;
	private String page;
	private String search;
	public String getSpaceIdMembers() {
		return spaceIdMembers;
	}
	public void setSpaceIdMembers(String spaceIdMembers) {
		this.spaceIdMembers = spaceIdMembers;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
}
