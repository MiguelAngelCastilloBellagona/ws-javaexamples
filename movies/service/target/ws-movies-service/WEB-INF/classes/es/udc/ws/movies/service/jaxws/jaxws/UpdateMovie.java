
package es.udc.ws.movies.service.jaxws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.movies.service.jaxws.MovieInformationWTO;

@XmlRootElement(name = "updateMovie", namespace = "http://movies.ws.adoo.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateMovie", namespace = "http://movies.ws.adoo.udc.es/")
public class UpdateMovie {

    @XmlElement(name = "arg0", namespace = "")
    private MovieInformationWTO arg0;

    /**
     * 
     * @return
     *     returns MovieInformationWTO
     */
    public MovieInformationWTO getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(MovieInformationWTO arg0) {
        this.arg0 = arg0;
    }

}
