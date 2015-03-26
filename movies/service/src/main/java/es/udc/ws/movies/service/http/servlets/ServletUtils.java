package es.udc.ws.movies.service.http.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.jdom.Element;

import es.udc.ws.movies.service.rest.xml.ServiceResponse;
import es.udc.ws.movies.service.rest.xml.ExceptionInServiceResponse;
import es.udc.ws.movies.service.rest.xml.ServiceResponseXMLConversor;

public class ServletUtils {
    
    private ServletUtils() {}
    
    public static void writeServiceResponse(HttpServletResponse response) 
        throws IOException {      
        
        writeServiceResponse(new ServiceResponse(), response);
        
    }
    
    public static void writeServiceResponse(Element dataElement,
                                            HttpServletResponse response) 
            throws IOException {   
        
        writeServiceResponse(new ServiceResponse(dataElement), response);
        
    }
    
    public static void writeServiceResponse(List<Element> dataElements,
                                            HttpServletResponse response) 
            throws IOException {   
            
        writeServiceResponse(new ServiceResponse(dataElements), response);
            
    }
    
    public static void writeServiceResponse(int exceptionCode,
                                            String exceptionMessage, 
                                            HttpServletResponse response) 
            throws IOException { 
        
        ServiceResponse serviceResponse = new ServiceResponse(
            new ExceptionInServiceResponse(exceptionCode, exceptionMessage));
            
        writeServiceResponse(serviceResponse, response);
                
    }
    
    private static void writeServiceResponse(ServiceResponse serviceResponse,
                                             HttpServletResponse response) 
            throws IOException {
        
        OutputStream out = response.getOutputStream();
        
        response.setContentType("text/xml; charset=UTF-8");
        ServiceResponseXMLConversor.toXML(serviceResponse, out, true);            
        out.close();
        
    }
    
    public static void writeServiceResponse(ServiceResponse serviceResponse,
                                           OutputStream out) 
            throws IOException {
        
        ServiceResponseXMLConversor.toXML(serviceResponse, out, true);            
        out.close();
        
    }    

}
