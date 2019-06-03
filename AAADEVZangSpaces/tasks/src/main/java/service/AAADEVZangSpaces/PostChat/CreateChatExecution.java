package service.AAADEVZangSpaces.PostChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class CreateChatExecution extends NodeInstance{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CreateChatExecution.class);
	
	public CreateChatExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}
	
	public Object execute() throws Exception{
		JSONObject json = new JSONObject();
		
		CreateChatModel createChatModel = (CreateChatModel)getNode();
		
		String spaceIdCreateMessage = (String)get("spaceIdCreateMessage");
		if(spaceIdCreateMessage == null || spaceIdCreateMessage.isEmpty()){
			spaceIdCreateMessage = createChatModel.getSpaceIdCreateMessage();
		}
		
		String authorizationCreateMessage = (String)get("authorizationCreateMessage");
		if(authorizationCreateMessage == null || authorizationCreateMessage.isEmpty()){
			authorizationCreateMessage = createChatModel.getAuthorizationCreateMessage();
		}
		String bodyTextCreateMessage = (String)get("bodyTextCreateMessage");
		if(bodyTextCreateMessage == null || bodyTextCreateMessage.isEmpty()){
			bodyTextCreateMessage = createChatModel.getBodyTextCreateMessage();
		}
		
		String messageTypeCreateMessage = createChatModel.getMessageTypeCreateMessage();
		if(messageTypeCreateMessage == null || messageTypeCreateMessage.isEmpty()){
			messageTypeCreateMessage = "";
		}
		
		json = postCreateChat(spaceIdCreateMessage, authorizationCreateMessage, bodyTextCreateMessage, messageTypeCreateMessage);
		
		return json;
	}
	
	public JSONObject postCreateChat(String spaceIdCreateMessage, String authorizationCreateMessage, String bodyTextCreateMessage, String messageTypeCreateMessage) throws SSLUtilityException, ClientProtocolException, IOException, JSONException{
		final SSLProtocolType protocolTypeAssistant = SSLProtocolType.TLSv1_2;
		final SSLContext sslContextAssistant = SSLUtilityFactory
				.createSSLContext(protocolTypeAssistant);
		final String URI = "https://spaces.zang.io/api/spaces/"+spaceIdCreateMessage+"/chats";
		final HttpClient client = HttpClients.custom()
				.setSslcontext(sslContextAssistant)
				.setHostnameVerifier(new AllowAllHostnameVerifier())
				.build();
		final HttpPost postMethod = new HttpPost(URI);
		postMethod.addHeader("Authorization", "jwt " + authorizationCreateMessage.trim());
		postMethod.addHeader("Content-Type", "application/json");
		String messageBody = null;
		if(messageTypeCreateMessage.equals("ElectroCardiogram")){
			messageBody = getMessageElectroCardioGram(bodyTextCreateMessage);
		}
		if(messageTypeCreateMessage.equals("Xrays")){
			messageBody = getMessageXray1(bodyTextCreateMessage);
		}
		if(messageTypeCreateMessage.equals("Electrodes")){
			messageBody = getMessageElectrodes(bodyTextCreateMessage);
		}
		if(messageTypeCreateMessage.equals("")){
			messageBody = getMessageOnlyMessage(bodyTextCreateMessage);
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
	
	public String getMessageElectroCardioGram(String bodyText){
		String electroCardioGram = "{\n"
                + "  \"content\": {\n"
                + "      \"bodyText\": \""+bodyText+"\",\n"
                + "      \"data\": [{\n"
                + "          \"fileId\": \"1c452e87-7299-41d2-b9eb-3b422922422c\",\n"
                + "          \"fileSize\": 709708,\n"
                + "          \"fileType\": \"document\",\n"
                + "          \"icon\": \"\",\n"
                + "          \"name\": \"ElectroCariogramInfo1.pdf\",\n"
                + "          \"provider\": \"native\",\n"
                + "          \"providerFileType\": \"application/pdf\"\n"
                + "      }]              \n"
                + "  }\n"
                + "}";
		
		return electroCardioGram;
	}
	
	public String getMessageXray1(String bodyText){
		String xray1 = "{\n"
                + "  \"content\": {\n"
                + "      \"bodyText\": \""+bodyText+"\",\n"
                + "      \"data\": [{\n"
                + "          \"fileId\": \"c1345b72-bc27-467a-baf6-02f780683d18\",\n"
                + "          \"fileSize\": 403078,\n"
                + "          \"fileType\": \"document\",\n"
                + "          \"icon\": \"\",\n"
                + "          \"name\": \"xray1.PNG\",\n"
                + "          \"provider\": \"native\",\n"
                + "          \"providerFileType\": \"image/png\"\n"
                + "      }]              \n"
                + "  }\n"
                + "}";
		return xray1;
	}
	
	public String getMessageElectrodes(String bodyText){
		String electrodes = "{\n"
                + "  \"content\": {\n"
                + "      \"bodyText\": \""+bodyText+"\",\n"
                + "      \"data\": [{\n"
                + "          \"fileId\": \"a3da6d1c-78af-40d5-9095-e7958a54f966\",\n"
                + "          \"fileSize\": 233181,\n"
                + "          \"fileType\": \"document\",\n"
                + "          \"icon\": \"\",\n"
                + "          \"name\": \"Electrodes.PNG\",\n"
                + "          \"provider\": \"native\",\n"
                + "          \"providerFileType\": \"image/png\"\n"
                + "      }]              \n"
                + "  }\n"
                + "}";
		return electrodes;
	}
	public String getMessageOnlyMessage(String bodyText){
		String message = "{\n"
                + "  \"content\": {\n"
                + "      \"bodyText\": \""+bodyText+"\"\n"
                + "  }\n"
                + "}"; 
		return message;
	}

}
