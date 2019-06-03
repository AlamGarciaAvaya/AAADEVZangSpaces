package service.AAADEVZangSpaces.PostTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import com.avaya.app.entity.Instance;
import com.avaya.app.entity.NodeInstance;
import com.avaya.collaboration.ssl.util.SSLProtocolType;
import com.avaya.collaboration.ssl.util.SSLUtilityException;
import com.avaya.collaboration.ssl.util.SSLUtilityFactory;
import com.avaya.workflow.logger.Logger;
import com.avaya.workflow.logger.LoggerFactory;
import com.roobroo.bpm.model.BpmNode;

public class CreateTaskExecution extends NodeInstance{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CreateTaskExecution.class);
	
	public CreateTaskExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}
	
	public Object execute() throws Exception{
		JSONObject json = new JSONObject();
		CreateTaskModel createTaskModel = (CreateTaskModel)getNode();
		
		String spaceIdeCreateTask = (String)get("spaceIdeCreateTask");
		if(spaceIdeCreateTask == null || spaceIdeCreateTask.isEmpty()){
			spaceIdeCreateTask = createTaskModel.getSpaceIdeCreateTask();
		}
		String authorizationCreateTask = (String)get("authorizationCreateTask");
		if(authorizationCreateTask == null || authorizationCreateTask.isEmpty()){
			authorizationCreateTask = createTaskModel.getAuthorizationCreateTask();
		}
		String bodyTextCreateTask = (String)get("bodyTextCreateTask");
		if(bodyTextCreateTask == null || bodyTextCreateTask.isEmpty()){
			bodyTextCreateTask = createTaskModel.getBodyTextCreateTask();
		}
		String descriptionCreateTask = (String)get("descriptionCreateTask");
		if(descriptionCreateTask == null || descriptionCreateTask.isEmpty()){
			descriptionCreateTask = createTaskModel.getDescriptionCreateTask();
		}
		String assignessCreateTask = (String)get("assignessCreateTask");
		if(assignessCreateTask == null || assignessCreateTask.isEmpty()){
			assignessCreateTask = createTaskModel.getAssignessCreateTask();
			if(assignessCreateTask == null || assignessCreateTask.isEmpty()){
				assignessCreateTask =  "5ced39e0bcacd1e2f6533037";
			}
		}
		String dueDateCreateTask = (String)get("dueDateCreateTask");
		if(dueDateCreateTask == null || dueDateCreateTask.isEmpty()){
			dueDateCreateTask = createTaskModel.getDueDateCreateTask();
			if(dueDateCreateTask == null || dueDateCreateTask.isEmpty()){
				ZonedDateTime zonedDateTimeGenerate = ZonedDateTime.now();
				dueDateCreateTask = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SS").format(zonedDateTimeGenerate);
			}
		}
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= dueDateCreateTask.length(); i++) {
            if(i == 10){
                sb.append("T");
                continue;
            }
            if(i == dueDateCreateTask.length()){
                sb.append("Z");
                continue;
            }
            
            sb.append(dueDateCreateTask.charAt(i));
        }
        dueDateCreateTask = sb.toString();
		
		String messageTypeCreateTask = createTaskModel.getMessageTypeCreateTask();
		
		json = postCreateTask(spaceIdeCreateTask, authorizationCreateTask, bodyTextCreateTask, descriptionCreateTask, assignessCreateTask, dueDateCreateTask, messageTypeCreateTask);
		
		return json;
	}
	
	public JSONObject postCreateTask(String spaceIdeCreateTask, String authorizationCreateTask, String bodyTextCreateTask, String descriptionCreateTask, String assignessCreateTask, String dueDateCreateTask, String messageTypeCreateTask) throws SSLUtilityException, ClientProtocolException, IOException, JSONException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);
		final String URI = "https://spaces.zang.io/api/spaces/"+spaceIdeCreateTask+"/tasks";
		final HttpClient client = HttpClients.custom()
				.setSslcontext(sslContextAssistant)
				.setHostnameVerifier(new AllowAllHostnameVerifier())
				.build();
		final HttpPost postMethod = new HttpPost(URI);
		postMethod.addHeader("Authorization", "jwt " + authorizationCreateTask.trim());
		postMethod.addHeader("Content-Type", "application/json");
		String messageBody = null;
		if(messageTypeCreateTask.equals("ElectroCardiogram")){
			messageBody = getMessageElectroCardiogram(bodyTextCreateTask, descriptionCreateTask, assignessCreateTask, dueDateCreateTask);
		}
		if(messageTypeCreateTask.equals("Xrays")){
			messageBody = getMessageXray1(bodyTextCreateTask, descriptionCreateTask, assignessCreateTask, dueDateCreateTask);
		}
		if(messageTypeCreateTask.equals("Electrodes")){
			messageBody = getMessageElectrodes(bodyTextCreateTask, descriptionCreateTask, assignessCreateTask, dueDateCreateTask);
		}
		if(messageTypeCreateTask.equals("")){
			messageBody = getMessageOnlyMessage(bodyTextCreateTask, descriptionCreateTask, assignessCreateTask, dueDateCreateTask);
		}
		
		final StringEntity entity = new StringEntity(
				messageBody);
		postMethod.setEntity(entity);
		
		final HttpResponse response = client
				.execute(postMethod);

		final BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(response.getEntity()
						.getContent()));

		String line = "";
		final StringBuilder result = new StringBuilder();
		while ((line = inputStream.readLine()) != null) {
			result.append(line);
		}
		
		JSONObject json = new JSONObject(result.toString());
		
		return json;

	}
	
	public String getMessageElectroCardiogram(String bodyText, String description, String assigness, String dueDate){
		String electroCardioGram = "{  \n"
                + "   \"content\":{  \n"
                + "      \"bodyText\":\""+bodyText+"\",\n"
                + "      \"description\":\""+description+"\",\n"
                + "      \"data\":[  \n"
                + "         {  \n"
                + "            \"fileId\":\"1c452e87-7299-41d2-b9eb-3b422922422c\",\n"
                + "            \"fileSize\":709708,\n"
                + "            \"fileType\":\"document\",\n"
                + "            \"icon\":\"\",\n"
                + "            \"name\":\"ElectroCariogramInfo1.pdf\",\n"
                + "            \"provider\":\"native\",\n"
                + "            \"providerFileType\":\"application/pdf\"\n"
                + "         }\n"
                + "      ],\n"
                + "      \"assignees\":[  \n"
                + "         {  \n"
                + "            \"_id\":\""+assigness+"\"\n"
                + "         }\n"
                + "      ],\n"
                + "      \"dueDate\":\""+dueDate+"\",\n"
                + "      \"status\":\"pending\"\n"
                + "   }\n"
                + "}";
		return electroCardioGram;	
	}
	
	public String getMessageXray1(String bodyText, String description, String assigness, String dueDate){
		String xray1 = "{  \n"
                + "   \"content\":{  \n"
                + "      \"bodyText\":\""+bodyText+"\",\n"
                + "      \"description\":\""+description+"\",\n"
                + "      \"data\":[  \n"
                + "         {  \n"
                + "            \"fileId\":\"c1345b72-bc27-467a-baf6-02f780683d18\",\n"
                + "            \"fileSize\":403078,\n"
                + "            \"fileType\":\"document\",\n"
                + "            \"icon\":\"\",\n"
                + "            \"name\":\"xray1.PNG\",\n"
                + "            \"provider\":\"native\",\n"
                + "            \"providerFileType\":\"image/png\"\n"
                + "         }\n"
                + "      ],\n"
                + "      \"assignees\":[  \n"
                + "         {  \n"
                + "            \"_id\":\""+assigness+"\"\n"
                + "         }\n"
                + "      ],\n"
                + "      \"dueDate\":\""+dueDate+"\",\n"
                + "      \"status\":\"pending\"\n"
                + "   }\n"
                + "}";
		return xray1;
	}
	
	public String getMessageElectrodes(String bodyText, String description, String assigness, String dueDate){
		String electrodes = "{  \n"
                + "   \"content\":{  \n"
                + "      \"bodyText\":\""+bodyText+"\",\n"
                + "      \"description\":\""+description+"\",\n"
                + "      \"data\":[  \n"
                + "         {  \n"
                + "            \"fileId\":\"a3da6d1c-78af-40d5-9095-e7958a54f966\",\n"
                + "            \"fileSize\":233181,\n"
                + "            \"fileType\":\"document\",\n"
                + "            \"icon\":\"\",\n"
                + "            \"name\":\"Electrodes.PNG\",\n"
                + "            \"provider\":\"native\",\n"
                + "            \"providerFileType\":\"image/png\"\n"
                + "         }\n"
                + "      ],\n"
                + "      \"assignees\":[  \n"
                + "         {  \n"
                + "            \"_id\":\""+assigness+"\"\n"
                + "         }\n"
                + "      ],\n"
                + "      \"dueDate\":\""+dueDate+"\",\n"
                + "      \"status\":\"pending\"\n"
                + "   }\n"
                + "}";
				
		return electrodes;		
	}
	
	public String getMessageOnlyMessage(String bodyText, String description, String assigness, String dueDate){
		String message = "{  \n"
                + "   \"content\":{  \n"
                + "      \"bodyText\":\""+bodyText+"\",\n"
                + "      \"description\":\""+description+"\",\n"
                + "      \"data\":[],\n"
                + "      \"assignees\":[  \n"
                + "         {  \n"
                + "            \"_id\":\""+assigness+"\"\n"
                + "         }\n"
                + "      ],\n"
                + "      \"dueDate\":\""+dueDate+"\",\n"
                + "      \"status\":\"pending\"\n"
                + "   }\n"
                + "}";
		return message;
	}
}
