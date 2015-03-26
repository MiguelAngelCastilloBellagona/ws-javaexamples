
package es.udc.ws.movies.service.jaxws.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for movieInformationWTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="movieInformationWTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actorNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="directorNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="genres" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="identifier" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="runtime" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="synopsis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movieInformationWTO", propOrder = {
    "actorNames",
    "directorNames",
    "genres",
    "identifier",
    "releaseDate",
    "runtime",
    "synopsis",
    "title"
})
public class MovieInformationWTO {

    @XmlElement(nillable = true)
    protected List<String> actorNames;
    @XmlElement(nillable = true)
    protected List<String> directorNames;
    @XmlElement(nillable = true)
    protected List<String> genres;
    protected Long identifier;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar releaseDate;
    protected short runtime;
    protected String synopsis;
    protected String title;

    /**
     * Gets the value of the actorNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actorNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActorNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getActorNames() {
        if (actorNames == null) {
            actorNames = new ArrayList<String>();
        }
        return this.actorNames;
    }

    /**
     * Gets the value of the directorNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the directorNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDirectorNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDirectorNames() {
        if (directorNames == null) {
            directorNames = new ArrayList<String>();
        }
        return this.directorNames;
    }

    /**
     * Gets the value of the genres property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genres property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenres().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getGenres() {
        if (genres == null) {
            genres = new ArrayList<String>();
        }
        return this.genres;
    }

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdentifier(Long value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the releaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReleaseDate(XMLGregorianCalendar value) {
        this.releaseDate = value;
    }

    /**
     * Gets the value of the runtime property.
     * 
     */
    public short getRuntime() {
        return runtime;
    }

    /**
     * Sets the value of the runtime property.
     * 
     */
    public void setRuntime(short value) {
        this.runtime = value;
    }

    /**
     * Gets the value of the synopsis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Sets the value of the synopsis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynopsis(String value) {
        this.synopsis = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
