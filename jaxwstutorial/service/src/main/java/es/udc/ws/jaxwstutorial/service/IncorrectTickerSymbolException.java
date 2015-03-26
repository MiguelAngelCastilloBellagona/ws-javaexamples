package es.udc.ws.jaxwstutorial.service;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="IncorrectTickerSymbolException",
    targetNamespace="http://ws.adoo.udc.es/"
)
public class IncorrectTickerSymbolException extends Exception {

    private IncorrectTickerSymbolExceptionInfo exceptionInfo;

    public IncorrectTickerSymbolException(IncorrectTickerSymbolExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }



    public IncorrectTickerSymbolExceptionInfo getFaultInfo() {
        return exceptionInfo;
    }
    
}
