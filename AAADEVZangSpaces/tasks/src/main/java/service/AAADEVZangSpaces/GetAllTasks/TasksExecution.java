package service.AAADEVZangSpaces.GetAllTasks;

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

import com.avaya.app.entity.Instance;
import com.avaya.app.entity.NodeInstance;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.roobroo.bpm.model.BpmNode;
import com.avaya.workflow.logger.*;


public class TasksExecution extends NodeInstance {


	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(TasksExecution.class);

	public TasksExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}

	public Object execute() throws Exception {
		
		JSONObject json = new JSONObject();
		
		TasksModel tasksModel = (TasksModel)getNode();
		
		String spaceId = (String)get("spaceId");
		if(spaceId == null || spaceId.isEmpty()){
			spaceId = tasksModel.getSpaceId();
		}
		String authorization = (String)get("authorization");
		if(authorization == null || authorization.isEmpty()){
			authorization = tasksModel.getAuthorization();
		}
		
		json = getZangSpaceTasks(spaceId, authorization);
		
		
		return json;
    }
	
	public JSONObject getZangSpaceTasks(String spaceId, String authorization) throws JSONException{
		JSONObject json;
		try {
		      final SSLProtocolType protocolType = SSLProtocolType.TLSv1_2;
		      final SSLContext sslContext = SSLUtilityFactory.createSSLContext(protocolType);
		      
		      final String URI = "https://spacesapis.zang.io/api/spaces/"+spaceId+"/tasks";
		      
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
			json = new JSONObject();
			json.put("Error", e.toString());
			return json;
		}

	}
	
}
