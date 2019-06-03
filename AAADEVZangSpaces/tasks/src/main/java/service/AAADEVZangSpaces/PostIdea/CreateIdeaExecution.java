package service.AAADEVZangSpaces.PostIdea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
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

public class CreateIdeaExecution extends NodeInstance{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CreateIdeaExecution.class);
	
	public CreateIdeaExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}
	
	public Object execute() throws Exception{
		JSONObject json = new JSONObject();
		
		CreateIdeaModel createIdeaModel = (CreateIdeaModel)getNode();
		
		String spaceIdCreateIdea = (String)get("spaceIdCreateIdea");
		if(spaceIdCreateIdea == null || spaceIdCreateIdea.isEmpty()){
			spaceIdCreateIdea = createIdeaModel.getSpaceIdCreateIdea();
		}
		String authorization = (String)get("authorization");
		if(authorization == null || authorization.isEmpty()){
			authorization = createIdeaModel.getAutorization();
		}
		String bodyText = (String)get("bodyText");
		if(bodyText == null || bodyText.isEmpty()){
			bodyText = createIdeaModel.getBodyText();
		}
		String description = (String)get("description");
		if(description == null || description.isEmpty()){
			description = createIdeaModel.getDescription();
		}
		
		String messageType = createIdeaModel.getMessageType();
		
		try{
		json = createIdea(spaceIdCreateIdea, authorization, bodyText, description, messageType);
		}catch(Exception e){
			json.put("error", e.toString());
		}
		return json;
	}
	
	public JSONObject createIdea(String spaceIdCreateIdea, String authorization, String bodyText, String description, String messageType) throws JSONException, IOException, SSLUtilityException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);
		final String URI = "https://spacesapis.zang.io/api/spaces/"+spaceIdCreateIdea+"/ideas";
		final HttpClient client = HttpClients.custom()
				.setSslcontext(sslContextAssistant)
				.setHostnameVerifier(new AllowAllHostnameVerifier())
				.build();
		
		final HttpPost postMethod = new HttpPost(URI);
		postMethod.addHeader("Authorization", "jwt " + authorization.trim());
		postMethod.addHeader("Content-Type", "application/json");
		String messageBody = null;
		
		if(messageType.equals("ElectroCardiogram")){
			messageBody = getMessageElectroCardioGram(bodyText, description);
		}
		if(messageType.equals("Xrays")){
			messageBody = getMessageXray1(bodyText, description);
		}
		if(messageType.equals("Electrodes")){
			messageBody = getMessageElectrodes(bodyText, description);
		}
		if(messageType.equals("")){
			messageBody = getMessageOnlyMessage(bodyText, description);
		}
		
		final StringEntity tagoEntity = new StringEntity(
				messageBody);
		postMethod.setEntity(tagoEntity);

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
	
	public String getMessageElectroCardioGram(String bodyText, String description){
		String electroCardioGram = "{\n"
                + "\"content\": {\n"
                + "    \"bodyText\": \""+bodyText+"\",\n"
                + "    \"description\": \""+description+"\",\n"
                + "    \"data\": [{\n"
                + "        \"fileId\": \"1c452e87-7299-41d2-b9eb-3b422922422c\",\n"
                + "        \"fileSize\": 709708,\n"
                + "        \"fileType\": \"document\",\n"
                + "        \"icon\": \"\",\n"
                + "        \"name\": \"ElectroCariogramInfo1.pdf\",\n"
                + "        \"provider\": \"native\",\n"
                + "        \"providerFileType\": \"application/pdf\"\n"
                + "    }]\n"
                + "  }\n"
                + "}";
		
		return electroCardioGram;
	}
	
	public String getMessageXray1(String bodyText, String description){
		String xray1 = "{\n"
                + "\"content\": {\n"
                + "    \"bodyText\": \""+bodyText+"\",\n"
                + "    \"description\": \""+description+"\",\n"
                + "    \"data\": [{\n"
                + "        \"fileId\": \"c1345b72-bc27-467a-baf6-02f780683d18\",\n"
                + "        \"fileSize\": 403078,\n"
                + "        \"fileType\": \"document\",\n"
                + "        \"icon\": \"\",\n"
                + "        \"name\": \"xray1.PNG\",\n"
                + "        \"provider\": \"native\",\n"
                + "        \"providerFileType\": \"image/png\"\n"
                + "    }]\n"
                + "  }\n"
                + "}";
		return xray1;
	}
	
	public String getMessageElectrodes(String bodyText, String description){
		String electrodes = "{\n"
                + "\"content\": {\n"
                + "    \"bodyText\": \""+bodyText+"\",\n"
                + "    \"description\": \""+description+"\",\n"
                + "    \"data\": [{\n"
                + "        \"fileId\": \"a3da6d1c-78af-40d5-9095-e7958a54f966\",\n"
                + "        \"fileSize\": 233181,\n"
                + "        \"fileType\": \"document\",\n"
                + "        \"icon\": \"\",\n"
                + "        \"name\": \"Electrodes.PNG\",\n"
                + "        \"provider\": \"native\",\n"
                + "        \"providerFileType\": \"image/png\"\n"
                + "    }]\n"
                + "  }\n"
                + "}";
		return electrodes;
	}
	public String getMessageOnlyMessage(String bodyText, String description){
		String message = "{\n"
                + "\"content\": {\n"
                + "    \"bodyText\": \""+bodyText+"\",\n"
                + "    \"description\": \""+description+"\",\n"
                + "    \"data\": []\n"
                + "  }\n"
                + "}";
		return message;
	}

}
