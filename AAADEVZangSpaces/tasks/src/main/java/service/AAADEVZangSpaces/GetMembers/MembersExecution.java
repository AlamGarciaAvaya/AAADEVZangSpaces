package service.AAADEVZangSpaces.GetMembers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import com.avaya.app.entity.Instance;
import com.avaya.app.entity.NodeInstance;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.avaya.workflow.logger.Logger;
import com.avaya.workflow.logger.LoggerFactory;
import com.roobroo.bpm.model.BpmNode;

public class MembersExecution extends NodeInstance{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(MembersExecution.class);
	
	public MembersExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}
	
	public Object execute() throws Exception{
		JSONObject json = new JSONObject();
		MembersModel membersModel = (MembersModel)getNode();
		
		String spaceIdMembers = (String)get("spaceIdMembers");
		if(spaceIdMembers == null || spaceIdMembers.isEmpty()){
			spaceIdMembers = membersModel.getSpaceIdMembers();
		}
		String authorization = (String)get("authorization");
		if(authorization == null || authorization.isEmpty()){
			authorization = membersModel.getAuthorization();
		}
		String size = (String)get("size");
		if(size == null || size.isEmpty()){
			size = membersModel.getSize();
			if(size == null || size.isEmpty()){
				size = "30";
			}
		}
		String page = (String)get("page");
		if(page == null || page.isEmpty()){
			page = membersModel.getPage();
			if(page == null || page.isEmpty()){
				page = "0";
			}
		}
		
		String search = (String)get("searchMembers");
		if(search == null || search.isEmpty()){
			search = membersModel.getSearch();
			if(search == null || search.isEmpty()){
				search = "";
			}
		}
		
		json = getMembers(spaceIdMembers, authorization, size, page, search);
		
		return json;
	}
	
	public JSONObject getMembers(String spaceIdMembers, String authorization, String size, String page, String search) throws JSONException{
		JSONObject json = new JSONObject();
		
		try{
			final SSLProtocolType protocolType = SSLProtocolType.TLSv1_2;
			final SSLContext sslContext = SSLUtilityFactory.createSSLContext(protocolType);
			String encodedSearch = URLEncoder.encode(search, "UTF-8");
			final String URI = "https://spacesapis.zang.io/api/spaces/"+spaceIdMembers+"/members?size="+size+"&page="+page+"&search="+encodedSearch.trim();
		      HttpClient client = HttpClients.custom().setSslcontext(sslContext).setHostnameVerifier(new AllowAllHostnameVerifier()).build();
		      HttpGet getMethod = new HttpGet(URI);
		      
		      getMethod.addHeader("Authorization", "jwt " + authorization.trim());
		      final HttpResponse response = client.execute(getMethod);

				final BufferedReader inputStream = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));

				String line = "";
				final StringBuilder result = new StringBuilder();
				while ((line = inputStream.readLine()) != null) {
					result.append(line);
				}
				
				json = new JSONObject(result.toString());
		      
			return json;
		}catch(Exception e){
			json.put("Error", e.toString());
			return json;
		}
	}
}
