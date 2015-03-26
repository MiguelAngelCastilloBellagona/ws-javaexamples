
package es.udc.ws.jaxwstutorial.service.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.jaxwstutorial.service.TradePrice;

@XmlRootElement(name = "getLastTradePricesResponse", namespace = "http://ws.adoo.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLastTradePricesResponse", namespace = "http://ws.adoo.udc.es/")
public class GetLastTradePricesResponse {

    @XmlElement(name = "return", namespace = "")
    private List<TradePrice> _return;

    /**
     * 
     * @return
     *     returns List<TradePrice>
     */
    public List<TradePrice> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<TradePrice> _return) {
        this._return = _return;
    }

}
