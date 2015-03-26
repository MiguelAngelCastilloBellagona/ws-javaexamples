package es.udc.ws.movies.model;

@SuppressWarnings("serial")
public class MovieInformationFacadeException extends RuntimeException {
    
    public MovieInformationFacadeException() {
        super();
    }
    
    public MovieInformationFacadeException(String message) {
        super(message);
    }    
    
    public MovieInformationFacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieInformationFacadeException(Throwable cause) {
        super(cause);
    }
    
}
