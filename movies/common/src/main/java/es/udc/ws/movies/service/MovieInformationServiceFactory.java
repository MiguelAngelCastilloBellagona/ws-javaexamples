package es.udc.ws.movies.service;

import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.ServiceException;

public class MovieInformationServiceFactory {
    
    private final static String PROXY_CLASS_NAME_PARAMETER =
        "MovieInformationServiceFactory/proxyClassName";

    @SuppressWarnings("rawtypes")
	private static Class proxyClass;
    
    private MovieInformationServiceFactory() {}
    
    public static MovieInformationService getMovieInformationService() 
        throws ServiceException {
        
        try {        
            return (MovieInformationService) getProxyClass().newInstance();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        
    }    

    @SuppressWarnings("rawtypes")
	private static Class getProxyClass() throws ClassNotFoundException {
        
        if (proxyClass == null) {            
            String proxyClassName =
                ConfigurationParametersManager
                    .getParameter(PROXY_CLASS_NAME_PARAMETER);            
            proxyClass = Class.forName(proxyClassName);                                
        }
        
        return proxyClass;
        
    }
    
}
