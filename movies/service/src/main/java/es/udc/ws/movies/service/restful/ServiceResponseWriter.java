package es.udc.ws.movies.service.restful;

import es.udc.ws.movies.service.rest.xml.ServiceResponse;
import es.udc.ws.movies.service.rest.xml.ServiceResponseXMLConversor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Produces("application/xml")
@Provider
public class ServiceResponseWriter implements MessageBodyWriter<ServiceResponse>{

    @Override
    public boolean isWriteable(Class<?> type, 
                               Type type1, 
                               Annotation[] antns, 
                               MediaType mt) {
        return type == ServiceResponse.class;
    }

    @Override
    public long getSize(ServiceResponse t, 
                        Class<?> type, 
                        Type type1, 
                        Annotation[] antns, 
                        MediaType mt) {
        return -1;
    }

    @Override
    public void writeTo(ServiceResponse t, 
                        Class<?> type, 
                        Type type1, 
                        Annotation[] antns, 
                        MediaType mt, 
                        MultivaluedMap<String, Object> mm, 
                        OutputStream out) 
            throws IOException, WebApplicationException {
        ServiceResponseXMLConversor.toXML(t, out, true);
    }

}
