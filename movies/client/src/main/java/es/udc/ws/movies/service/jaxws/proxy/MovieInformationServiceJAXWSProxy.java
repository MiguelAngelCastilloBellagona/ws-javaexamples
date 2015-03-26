package es.udc.ws.movies.service.jaxws.proxy;

import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.MovieInformationService;
import es.udc.ws.movies.service.jaxws.wsdl.MovieException;
import es.udc.ws.movies.service.jaxws.wsdl.MovieInformationProvider;
import es.udc.ws.movies.service.jaxws.wsdl.MovieInformationProviderService;
import es.udc.ws.movies.service.jaxws.wsdl.MovieNotFoundException;
import es.udc.ws.movies.service.jaxws.proxy.wsdlutils.CommonWSDLTypeConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.configuration.MissingConfigurationParameterException;
import es.udc.ws.util.configuration.UnavailableConfigurationParametersException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.exceptions.ServiceException;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MovieInformationServiceJAXWSProxy implements MovieInformationService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "MovieInformationServiceJAXWSProxy/endpointAddress";

    private static String endpointAddress;

    private MovieInformationProviderService movieInformationProviderService =
            new MovieInformationProviderService();

    private MovieInformationProvider movieInformationProvider =
            movieInformationProviderService
                    .getMovieInformationProviderPort();

    public MovieInformationServiceJAXWSProxy() throws ServiceException {

        try {

            ((BindingProvider) movieInformationProvider)
                    .getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                    getEndpointAddress());

        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<MovieInformationTO> findMoviesByReleaseDate(
            Calendar releaseDate) throws ServiceException {

        try {
            XMLGregorianCalendar xCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar((GregorianCalendar) releaseDate);
            return CommonWSDLTypeConversor.fromWSDL(
                    movieInformationProvider.findMoviesByReleaseDate(xCalendar));

        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Long addMovie(MovieInformationTO movieInformation)
            throws MovieInformationException, ServiceException {

        try {

            return movieInformationProvider.addMovie(
                    CommonWSDLTypeConversor.toWSDL(movieInformation));

        } catch (MovieException e) {
            throw CommonWSDLTypeConversor.toMovieInformationException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void updateMovie(MovieInformationTO movieInformation)
            throws InstanceNotFoundException, MovieInformationException, 
                   ServiceException {

        try {

            movieInformationProvider.updateMovie(
                    CommonWSDLTypeConversor.toWSDL(movieInformation));

        } catch (MovieNotFoundException e) {
            throw CommonWSDLTypeConversor.toInstanceNotFoundException(e);
        } catch (MovieException e) {
            throw CommonWSDLTypeConversor.toMovieInformationException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void removeMovie(Long identifier)
            throws InstanceNotFoundException, ServiceException {

        try {
            movieInformationProvider.removeMovie(identifier);
        } catch (MovieNotFoundException e) {
            throw CommonWSDLTypeConversor.toInstanceNotFoundException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    private static String getEndpointAddress()
            throws MissingConfigurationParameterException,
            UnavailableConfigurationParametersException {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                    ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;

    }


}
