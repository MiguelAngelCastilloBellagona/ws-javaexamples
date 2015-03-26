
package es.udc.ws.movies.service.jaxws;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "MovieNotFoundException", targetNamespace = "http://movies.ws.adoo.udc.es/")
public class MovieNotFoundException extends Exception {

    private MovieNotFoundExceptionInfo faultInfo;

    public MovieNotFoundException(MovieNotFoundExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public MovieNotFoundExceptionInfo getFaultInfo() {
        return faultInfo;
    }

}
