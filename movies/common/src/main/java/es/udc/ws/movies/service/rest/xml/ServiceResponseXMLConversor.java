package es.udc.ws.movies.service.rest.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import es.udc.ws.movies.xml.MovieXMLConversor;
import es.udc.ws.util.exceptions.ParsingException;

public class ServiceResponseXMLConversor {
       
    private ServiceResponseXMLConversor() {}
    
    public static ServiceResponse toServiceResponse(InputStream in) 
        throws ParsingException {
        
        try {
            
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            Element responseElement = document.getRootElement();
            
            return toServiceResponse(responseElement);
        
        } catch (Exception e) {
            throw new ParsingException("Error deserializing instance of " +
                    ServiceResponse.class, e);
        }
        
    }
    
    public static void toXML(ServiceResponse response, 
                             OutputStream out,
                             boolean prettyFormat) throws ParsingException {
            
        try {
        
            Element responseElement = toXML(response);
            Document document = new Document(responseElement);
            XMLOutputter outputter;
            
            if (prettyFormat) {
                outputter = new XMLOutputter(Format.getPrettyFormat());                
            } else {
                outputter = new XMLOutputter();
            }
                        
            outputter.output(document, out);
        
        } catch (Exception e) {
            throw new ParsingException("Error serializing instance of " +
                    ServiceResponse.class, e);
        }
        
    }
    
    /* ---------- Helper methods for XML to Java conversion. ---------------- */
    
    private static ServiceResponse toServiceResponse(Element responseElement) 
        throws DataConversionException {
        
        String contentTypeString = responseElement
                .getAttributeValue("contentType");
        ServiceResponse.ContentType contentType = 
            ServiceResponse.ContentType.valueOf(contentTypeString);
        @SuppressWarnings("unchecked")
		List<Element> dataElements = responseElement.getChildren();
        
        if (contentType == ServiceResponse.ContentType.DATA) {
            
            if (dataElements.isEmpty()) {                
                return new ServiceResponse();                
            } else {                
                return new ServiceResponse(dataElements);
            }
            
        } else { // contentType == ServiceResponse.ContentType.EXCEPTION  
            return new ServiceResponse(
                    toResponseException(dataElements.get(0)));  
        }

    }
    
    private static ExceptionInServiceResponse toResponseException(
            Element exceptionElement) throws DataConversionException {
        
        int code = exceptionElement.getAttribute("code").getIntValue();
        String message = exceptionElement.getAttributeValue("message");
        
        return new ExceptionInServiceResponse(code, message);
        
    }
    
    /* ----------- Helper methods for Java to XML conversion. --------------- */
    
    private static Element toXML(ServiceResponse response) {
        
        Element responseElement = new Element("response", 
                MovieXMLConversor.XML_NS);
        ServiceResponse.ContentType contentType = response.getContentType();
        
        responseElement.setAttribute("contentType", 
                response.getContentType().toString());
        
        if (contentType == ServiceResponse.ContentType.DATA) {             
            responseElement.addContent(response.getDataElements());            
        } else { // contentType == ServiceResponse.ContentType.EXCEPTION          
            responseElement.addContent(toXML(response.getException()));            
        }
        
        return responseElement;
        
    }
    
    private static Element toXML(ExceptionInServiceResponse exception) {
        
        Element exceptionElement = new Element("exception", 
                MovieXMLConversor.XML_NS);
        
        exceptionElement.setAttribute("code", 
                Integer.toString(exception.getCode()));
        exceptionElement.setAttribute("message", 
                exception.getMessage());
        
        return exceptionElement;
        
    }

}
