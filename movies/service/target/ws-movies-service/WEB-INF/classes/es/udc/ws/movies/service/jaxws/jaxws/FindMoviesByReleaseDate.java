
package es.udc.ws.movies.service.jaxws.jaxws;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findMoviesByReleaseDate", namespace = "http://movies.ws.adoo.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findMoviesByReleaseDate", namespace = "http://movies.ws.adoo.udc.es/")
public class FindMoviesByReleaseDate {

    @XmlElement(name = "arg0", namespace = "")
    private Calendar arg0;

    /**
     * 
     * @return
     *     returns Calendar
     */
    public Calendar getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(Calendar arg0) {
        this.arg0 = arg0;
    }

}
