package es.udc.ws.movies.service.restful.proxy;

import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.MovieInformationService;
import es.udc.ws.movies.service.rest.xml.ExceptionCodes;
import es.udc.ws.movies.service.rest.xml.ExceptionInServiceResponse;
import es.udc.ws.movies.service.rest.xml.ServiceResponse;
import es.udc.ws.movies.service.rest.xml.ServiceResponseXMLConversor;
import es.udc.ws.movies.xml.MovieXMLConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.configuration.MissingConfigurationParameterException;
import es.udc.ws.util.configuration.UnavailableConfigurationParametersException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.exceptions.ServiceException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.*;

public class MovieInformationServiceRESTFulProxy implements
        MovieInformationService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
        "MovieInformationServiceRESTFulProxy/endpointAddress";

    private static String endpointAddress;

    public MovieInformationServiceRESTFulProxy() {}

    @Override
    public List<MovieInformationTO> findMoviesByReleaseDate(
                Calendar releaseDate)  throws ServiceException {
        int day = releaseDate.get(Calendar.DAY_OF_MONTH);
        int month = releaseDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = releaseDate.get(Calendar.YEAR);
        GetMethod method = new GetMethod(getEndpointAddress() + 
                "?year=" + year + "&month=" + month + "&day=" + day);

        try {

            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(method);

            ServiceResponse serviceResponse =
                    handleResponse(statusCode, HttpStatus.SC_OK, null,
                        method.getResponseBodyAsStream());

            if (serviceResponse.getContentType() ==
                    ServiceResponse.ContentType.DATA) {

                return MovieXMLConversor.toMovieInformationList(
                    serviceResponse.getDataElements());

            } else {
                throw getUnexpectedServiceException(
                        serviceResponse.getException());
            }

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e);
        }  finally {
            method.releaseConnection();
        }
    }

    @Override
    public Long addMovie(MovieInformationTO movieInformation)
            throws MovieInformationException, ServiceException {
        PostMethod method =
            new PostMethod(getEndpointAddress());

        try {

            ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
            MovieXMLConversor.toXML(movieInformation, xmlOutputStream);
            ByteArrayInputStream xmlInputStream =
                new ByteArrayInputStream(xmlOutputStream.toByteArray());

            InputStreamRequestEntity requestEntity =
                new InputStreamRequestEntity(xmlInputStream,
                        "application/xml");

            HttpClient client = new HttpClient();
            method.setRequestEntity(requestEntity);
            int statusCode = client.executeMethod(method);

            handleResponse(statusCode, HttpStatus.SC_CREATED,
                    null, method.getResponseBodyAsStream());

            Header[] headers = method.getResponseHeaders();
            for (int i = 0; i < headers.length; i++) {
                Header header = headers[i];
                if("Location".equalsIgnoreCase(header.getName())){
                    String uri = header.getValue();
                    int idx = uri.lastIndexOf('/');
                    return Long.valueOf(uri.substring(idx + 1));
                }
            }

            throw new ServiceException("Unable to find valid Location header with movie identifier");
        } catch (MovieInformationException e) {
            throw e;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public void updateMovie(MovieInformationTO movieInformation)
            throws InstanceNotFoundException, MovieInformationException,
                   ServiceException {
        PutMethod method =
            new PutMethod(getEndpointAddress() + "/" +
                movieInformation.getIdentifier());

        try {

            ByteArrayOutputStream xmlOutputStream =
                    new ByteArrayOutputStream();
            MovieXMLConversor.toXML(movieInformation, xmlOutputStream);
            ByteArrayInputStream xmlInputStream =
                new ByteArrayInputStream(xmlOutputStream.toByteArray());

            InputStreamRequestEntity requestEntity =
                new InputStreamRequestEntity(xmlInputStream,
                        "application/xml");

            HttpClient client = new HttpClient();
            method.setRequestEntity(requestEntity);
            int statusCode = client.executeMethod(method);

            handleResponse(statusCode, HttpStatus.SC_NO_CONTENT,
                    movieInformation.getIdentifier(),
                    method.getResponseBodyAsStream());

        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (MovieInformationException e) {
            throw e;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public void removeMovie(Long identifier)
            throws InstanceNotFoundException, ServiceException {

        DeleteMethod method =
            new DeleteMethod(getEndpointAddress() + "/" + identifier);

        try {

            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(method);

            handleResponse(statusCode, HttpStatus.SC_NO_CONTENT, identifier,
                    method.getResponseBodyAsStream());

        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            method.releaseConnection();
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

    private ServiceResponse handleResponse(int statusCode,
                                           int expectedStatusCode,
                                           Long identifier,
                                           InputStream in)
            throws ServiceException, InstanceNotFoundException,
                    MovieInformationException {

        if(statusCode == HttpStatus.SC_NOT_FOUND) {
            throw new InstanceNotFoundException(
                        identifier, MovieInformationTO.class.getName());
        } else if(statusCode == HttpStatus.SC_BAD_REQUEST) {

            ServiceResponse serviceResponse =
                ServiceResponseXMLConversor.toServiceResponse(in);

            if (serviceResponse.getContentType() ==
                    ServiceResponse.ContentType.EXCEPTION) {

                ExceptionInServiceResponse exception =
                    serviceResponse.getException();

                if (exception.getCode() ==
                        ExceptionCodes.INCORRECT_MOVIE_INFORMATION) {
                    throw new MovieInformationException(exception.getMessage());
                } else {
                    throw getUnexpectedServiceException(exception);
                }
            }
            throw new MovieInformationException("HTTP error; status code = " +
                    statusCode);
        } else if (statusCode != expectedStatusCode) {
            throw new ServiceException("HTTP error; status code = " +
                    statusCode);
        }
        if(HttpStatus.SC_NO_CONTENT == statusCode) {
            return null;
        }
        return ServiceResponseXMLConversor.toServiceResponse(in);
    }

    private ServiceException getUnexpectedServiceException (
        ExceptionInServiceResponse exception) {

        return new ServiceException("Server exception; " +
            "code = " + exception.getCode() +
            "; message = " + exception.getMessage());

    }

}