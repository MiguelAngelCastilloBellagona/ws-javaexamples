
package es.udc.ws.movies.service.jaxws;

import javax.xml.ws.WebFault;


@SuppressWarnings("serial")
@WebFault(name = "MovieException", targetNamespace = "http://movies.ws.adoo.udc.es/")
public class MovieException extends Exception {

    private MovieExceptionInfo faultInfo;

    public MovieException(MovieExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public MovieExceptionInfo getFaultInfo() {
        return faultInfo;
    }

}
