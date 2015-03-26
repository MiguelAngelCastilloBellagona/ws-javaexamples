package es.udc.ws.util.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfigurationParametersManager {
    
    private static final String CONFIGURATION_FILE =
        "ConfigurationParameters.properties";
    
    private static Map<String, String> parameters;
       
    private ConfigurationParametersManager() {}
    
    public static String getParameter(String name) 
        throws MissingConfigurationParameterException, 
               UnavailableConfigurationParametersException {
        
        String value;
        
        try {
            value = getParameters().get(name);
        } catch (IOException e) {
            throw new UnavailableConfigurationParametersException(e);
        }

        if (value == null) {            
            throw new MissingConfigurationParameterException(name);            
        } else {
            return value;
        }
     
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, String> getParameters() throws IOException {
        
        if (parameters == null) {
        
            /* Read property file.*/    
            Class configurationParametersManagerClass = 
                ConfigurationParametersManager.class;
            ClassLoader classLoader =
                configurationParametersManagerClass.getClassLoader();
            InputStream inputStream =
                classLoader.getResourceAsStream(CONFIGURATION_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            
            /* 
             * We use a "HashMap" instead of a "Properties" because HashMap's
             * methods are *not* synchronized (then, they are faster) and the 
             * parameters are read-only.
             */
            parameters = new HashMap(properties);
        
        }
        
        return parameters;        
        
    }
    
}
