
package es.udc.ws.jaxwstutorial.service.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getLastTradePrices", namespace = "http://ws.adoo.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLastTradePrices", namespace = "http://ws.adoo.udc.es/")
public class GetLastTradePrices {

    @XmlElement(name = "arg0", namespace = "")
    private List<String> arg0;

    /**
     * 
     * @return
     *     returns List<String>
     */
    public List<String> getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(List<String> arg0) {
        this.arg0 = arg0;
    }

}
