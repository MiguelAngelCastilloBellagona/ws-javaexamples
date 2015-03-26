package es.udc.ws.util.configuration;

@SuppressWarnings("serial")
public class UnavailableConfigurationParametersException extends RuntimeException {
    
    public UnavailableConfigurationParametersException(Exception e) {
        super("Cannot access to configuation parameters", e);
    }

}
