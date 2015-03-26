package es.udc.ws.movies.service.rest.xml;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;

public class ServiceResponse {
    
    public enum ContentType {DATA, EXCEPTION};
    
    private ContentType contentType;
    private List<Element> dataElements;
    private ExceptionInServiceResponse exception;
    
    public ServiceResponse() {
        dataElements = new ArrayList<Element>();
        contentType = ContentType.DATA;
    }
    
    public ServiceResponse(Element dataElement) {
        dataElements = new ArrayList<Element>();
        dataElements.add(dataElement);
        contentType = ContentType.DATA;
    }

    public ServiceResponse(List<Element> dataElements) {
        this.dataElements = dataElements;
        contentType = ContentType.DATA;
    }
    
    public ServiceResponse(ExceptionInServiceResponse exception) {
        this.exception = exception;
        contentType = ContentType.EXCEPTION;
    }
    
    public ContentType getContentType() {
        return contentType;
    }
    
    public List<Element> getDataElements() {
        return dataElements;
    } 
    
    public ExceptionInServiceResponse getException() {
        return exception;
    }
       
}
