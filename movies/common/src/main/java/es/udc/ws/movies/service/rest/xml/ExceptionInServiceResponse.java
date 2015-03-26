package es.udc.ws.movies.service.rest.xml;

public class ExceptionInServiceResponse {
    
    private int code;
    private String message;
    
    public ExceptionInServiceResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
}
