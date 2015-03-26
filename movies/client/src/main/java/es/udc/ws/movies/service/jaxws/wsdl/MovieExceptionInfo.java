
package es.udc.ws.movies.service.jaxws.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for movieExceptionInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="movieExceptionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="movie" type="{http://movies.ws.adoo.udc.es/}movieInformationWTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movieExceptionInfo", propOrder = {
    "movie"
})
public class MovieExceptionInfo {

    protected MovieInformationWTO movie;

    /**
     * Gets the value of the movie property.
     * 
     * @return
     *     possible object is
     *     {@link MovieInformationWTO }
     *     
     */
    public MovieInformationWTO getMovie() {
        return movie;
    }

    /**
     * Sets the value of the movie property.
     * 
     * @param value
     *     allowed object is
     *     {@link MovieInformationWTO }
     *     
     */
    public void setMovie(MovieInformationWTO value) {
        this.movie = value;
    }

}
