
package es.udc.ws.movies.service.jaxws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addMovieResponse", namespace = "http://movies.ws.adoo.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addMovieResponse", namespace = "http://movies.ws.adoo.udc.es/")
public class AddMovieResponse {

    @XmlElement(name = "return", namespace = "")
    private long _return;

    /**
     * 
     * @return
     *     returns long
     */
    public long getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(long _return) {
        this._return = _return;
    }

}
