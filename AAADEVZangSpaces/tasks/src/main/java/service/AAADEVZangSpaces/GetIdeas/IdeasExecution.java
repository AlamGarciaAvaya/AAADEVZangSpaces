package service.AAADEVZangSpaces.GetIdeas;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import service.AAADEVZangSpaces.GetAllTasks.TasksExecution;

import com.avaya.app.entity.Instance;
import com.avaya.app.entity.NodeInstance;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.avaya.workflow.logger.Logger;
import com.avaya.workflow.logger.LoggerFactory;
import com.roobroo.bpm.model.BpmNode;

public class IdeasExecution extends NodeInstance{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(IdeasExecution.class);
	public IdeasExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}
	
	public Object execute() throws Exception{
		JSONObject json = new JSONObject();
		IdeasModel ideasModel = (IdeasModel)getNode();
		
		String spaceIdIdeas = (String)get("spaceIdIdeas");
		if(spaceIdIdeas == null || spaceIdIdeas.isEmpty()){
			spaceIdIdeas = ideasModel.getSpaceIdIdeas();
		}
		
		String authorization = (String)get("authorization");
		if(authorization == null || authorization.isEmpty()){
			authorization = ideasModel.getAuthorization();
		}
		String size = (String)get("size");
		if(size == null || size.isEmpty()){
			size = ideasModel.getSize();
			if(size == null || size.isEmpty()){
				size = "20";
			}
		}
		
		String page = (String)get("page");
		if(page == null || page.isEmpty()){
			page = ideasModel.getPage();
			if(page == null || page.isEmpty()){
				page = "1";
			}
		}
		
		json = getAllIdeas(spaceIdIdeas, authorization, size, page);
		
		return json;
	}
	
	public JSONObject getAllIdeas(String spaceIdIdeas, String authorization, String size, String page) throws JSONException{
		JSONObject json = new JSONObject();
		try{
			final SSLProtocolType protocolType = SSLProtocolType.TLSv1_2;
			final SSLContext sslContext = SSLUtilityFactory.createSSLContext(protocolType);
			final String URI = "https://spacesapis.zang.io/api/spaces/"+spaceIdIdeas+"/ideas?size="+size+"&page="+page;
			
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
