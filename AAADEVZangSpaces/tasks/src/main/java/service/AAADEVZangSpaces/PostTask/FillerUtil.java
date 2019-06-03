package service.AAADEVZangSpaces.PostTask;

import java.util.ArrayList;
import java.util.List;

public class FillerUtil {
	
	private static FillerUtil instance = null;
	
	public static FillerUtil getInstance()
	  {
	    if (instance == null) {
	      synchronized (FillerUtil.class)
	      {
	        if (instance == null) {
	          instance = new FillerUtil();
	        }
	      }
	    }
	    return instance;
	  }
	
	public List<String> comboMessageType() {
		List<String> contentTypeList = new ArrayList();
		contentTypeList.add("ElectroCardiogram");
		contentTypeList.add("Xrays");
		contentTypeList.add("Electrodes");
		return contentTypeList;
	}


}
