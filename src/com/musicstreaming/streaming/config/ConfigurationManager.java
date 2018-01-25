package com.musicstreaming.streaming.config;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public final class ConfigurationManager {

    private static final String SERVICE_CONFIGURATION_FILE =
        "ServiceConfiguration.properties";
    
    private static Map parameters;

    static {
        try {
            Class configurationParametersManagerClass = ConfigurationManager.class;
            ClassLoader classLoader = configurationParametersManagerClass.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(SERVICE_CONFIGURATION_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            
            parameters = Collections.synchronizedMap(properties);
            
        } catch (Throwable t) {
        	// TODO: Logger 
            t.printStackTrace();            
        }

    }

    private static ConfigurationManager instance = null;
    
    /**
     * Singleton Thread-Safe.
     */
    public static ConfigurationManager getInstance() {
    	if (instance == null) {
    		synchronized(ConfigurationManager.class) {
    			if (instance == null) { // Necesario para proteger una segunda instanciaci�n
    				instance = new ConfigurationManager();
    			}
    		}
    	}
    	return instance;    	
    }
    
    private ConfigurationManager() {    	
    };

    public String getParameter(String name) {
        String value = (String) parameters.get(name);       
        return value;
    }
}
