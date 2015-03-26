
package es.udc.ws.jaxwstutorial.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for incorrectTickerSymbolExceptionInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incorrectTickerSymbolExceptionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="incorrectTickerSymbol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incorrectTickerSymbolExceptionInfo", propOrder = {
    "incorrectTickerSymbol"
})
public class IncorrectTickerSymbolExceptionInfo {

    protected String incorrectTickerSymbol;

    /**
     * Gets the value of the incorrectTickerSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncorrectTickerSymbol() {
        return incorrectTickerSymbol;
    }

    /**
     * Sets the value of the incorrectTickerSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncorrectTickerSymbol(String value) {
        this.incorrectTickerSymbol = value;
    }

}
