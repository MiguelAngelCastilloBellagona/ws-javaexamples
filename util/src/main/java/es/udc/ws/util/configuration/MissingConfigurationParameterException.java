package es.udc.ws.util.configuration;

@SuppressWarnings("serial")
public class MissingConfigurationParameterException extends RuntimeException {

    private String parameterName;

    public MissingConfigurationParameterException(String parameterName) {
        super("Missing configuration parameter: '" + parameterName + "'");
        this.parameterName = parameterName;
    }
    
    public String getParameterName() {
        return parameterName;
    }
    
}
