package service.AAADEVZangSpaces.GetMessages;

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

public class MessagesExecution extends NodeInstance{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(MessagesExecution.class);
	
	public MessagesExecution(Instance instance, BpmNode node) {
		super(instance, node);
	}
	
	public Object execute() throws Exception{
		JSONObject json = new JSONObject();
		MessagesModel messagesModel = (MessagesModel)getNode();
		
		String spaceIdMessages = (String)get("spaceIdMessages");
		if(spaceIdMessages == null || spaceIdMessages.isEmpty()){
			spaceIdMessages = messagesModel.getSpaceIdMessages();
		}
		String authorization = (String)get("authorization");
		if(authorization == null || authorization.isEmpty()){
			authorization = messagesModel.getAuthorization();
		}
		String searchstring = (String)get("searchstring");
		if(searchstring == null || searchstring.isEmpty()){
			searchstring = messagesModel.getSearchstring();
			if(searchstring == null || searchstring.isEmpty()){
				searchstring = "";
			}
		}
		
		String size = (String)get("size");
		if(size == null || size.isEmpty()){
			size = messagesModel.getSize();
			if(size == null || size.isEmpty()){
				size = "30";
			}
		}
		
		json = getMessages(spaceIdMessages, authorization, searchstring, size);
		
		return json;
	}
	
	
	public JSONObject getMessages(String spaceIdMessages, String authorization, String searchstring, String size) throws JSONException{
		JSONObject json = new JSONObject();
		try{
			final SSLProtocolType protocolType = SSLProtocolType.TLSv1_2;
			final SSLContext sslContext = SSLUtilityFactory.createSSLContext(protocolType);
			
			String encodedMessage = URLEncoder.encode(searchstring, "UTF-8");
			final String URI = "https://spacesapis.zang.io/api/spaces/5ce823c445151631cc752094/messages/query?category=chat&size="
								+size+"&searchstring="+encodedMessage;
			
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
