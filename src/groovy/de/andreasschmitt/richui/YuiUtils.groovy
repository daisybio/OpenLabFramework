package de.andreasschmitt.richui


class YuiUtils {
	
	private static final String YUI_PATH = "http://yui.yahooapis.com"
	private static final String LATEST_VERSION = "2.8.0"
	
	public static String getResourcePath(String resourcePath, boolean remote){
		return getResourcePath(LATEST_VERSION, resourcePath, remote)
	}
	
	public static String getResourcePath(String version, String resourcePath, boolean remote){
		if(remote || grails.util.Holders.config?.richui?.serve?.resource?.files?.remote){
			return getYuiResourcePath(version)
		}
		
		return resourcePath + "/js/yui"
	}
	
	private static String getYuiResourcePath(){
		return getYuiResourcePath(LATEST_VERSION)
	}
	
	private static String getYuiResourcePath(String version){
		return "${YUI_PATH}/${version}/build"
	}
	
}