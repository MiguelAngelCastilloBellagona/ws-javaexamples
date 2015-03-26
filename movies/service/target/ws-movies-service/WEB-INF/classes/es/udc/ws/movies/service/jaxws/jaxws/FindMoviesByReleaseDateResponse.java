
package es.udc.ws.movies.service.jaxws.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.movies.service.jaxws.MovieInformationWTO;

@XmlRootElement(name = "findMoviesByReleaseDateResponse", namespace = "http://movies.ws.adoo.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findMoviesByReleaseDateResponse", namespace = "http://movies.ws.adoo.udc.es/")
public class FindMoviesByReleaseDateResponse {

    @XmlElement(name = "return", namespace = "")
    private List<MovieInformationWTO> _return;

    /**
     * 
     * @return
     *     returns List<MovieInformationWTO>
     */
    public List<MovieInformationWTO> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<MovieInformationWTO> _return) {
        this._return = _return;
    }

}
