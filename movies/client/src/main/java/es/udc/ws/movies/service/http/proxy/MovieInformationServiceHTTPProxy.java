package es.udc.ws.movies.service.http.proxy;

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
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jdom.Element;

public class MovieInformationServiceHTTPProxy implements MovieInformationService {
    
    private final static String ENDPOINT_ADDRESS_PARAMETER =
        "MovieInformationServiceHTTPProxy/endpointAddress";
    
    private static String endpointAddress;
        
    public MovieInformationServiceHTTPProxy() {}
    
    @Override
    public List<MovieInformationTO> findMoviesByReleaseDate(
            Calendar releaseDate) throws ServiceException {
                
        /* Prepare request. */
        int day = releaseDate.get(Calendar.DAY_OF_MONTH);
        int month = releaseDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = releaseDate.get(Calendar.YEAR);
        GetMethod method = new GetMethod(
            getEndpointAddress() + "/findMoviesByReleaseDate" +
            "?day=" + day + "&month=" + month + "&year=" + year);
                     
        try {
            
            /* Send request. */
            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(method);
            
            /* Process reply. */  
            handleHTTPStatusCode(statusCode);
                       
            InputStream in = method.getResponseBodyAsStream();
            ServiceResponse serviceResponse =
                ServiceResponseXMLConversor.toServiceResponse(in);
            
            if (serviceResponse.getContentType() == 
                    ServiceResponse.ContentType.DATA) {
                
                return MovieXMLConversor.toMovieInformationList(
                    serviceResponse.getDataElements());
                
            } else { // ServiceResponse.ContentType.EXCEPTION                
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
            new PostMethod(getEndpointAddress() + "/addMovie");
        
        try {
            
            /* Prepare request. */
            ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();            
            MovieXMLConversor.toXML(movieInformation, xmlOutputStream);
            ByteArrayInputStream xmlInputStream =
                new ByteArrayInputStream(xmlOutputStream.toByteArray());
            
            InputStreamRequestEntity requestEntity = 
                new InputStreamRequestEntity(xmlInputStream, 
                        "text/xml; charset=UTF-8");
            
            /* Send request. */
            HttpClient client = new HttpClient();
            method.setRequestEntity(requestEntity);
            int statusCode = client.executeMethod(method);
            
            /* Process reply. */  
            handleHTTPStatusCode(statusCode);
            
            InputStream in = method.getResponseBodyAsStream();
            ServiceResponse serviceResponse =
                ServiceResponseXMLConversor.toServiceResponse(in);
            
            if (serviceResponse.getContentType() == 
                    ServiceResponse.ContentType.DATA) {
                
                Element identifierElement = 
                        serviceResponse.getDataElements().get(0);
                Long identifier = Long.valueOf(
                        identifierElement.getTextTrim());
                
                return identifier;
                
            } else { // ServiceResponse.ContentType.EXCEPTION
                
                ExceptionInServiceResponse exception = 
                    serviceResponse.getException();
                
                if (exception.getCode() == ExceptionCodes
                        .INCORRECT_MOVIE_INFORMATION) {                    
                    throw new MovieInformationException(
                            exception.getMessage());                    
                } else {
                    throw getUnexpectedServiceException(exception);
                }
            } 
            
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
        
        PostMethod method = 
            new PostMethod(getEndpointAddress() + "/updateMovie");
        
        try {
            
            /* Prepare request. */
            ByteArrayOutputStream xmlOutputStream = 
                    new ByteArrayOutputStream();            
            MovieXMLConversor.toXML(movieInformation, xmlOutputStream);
            ByteArrayInputStream xmlInputStream =
                new ByteArrayInputStream(xmlOutputStream.toByteArray());
            
            InputStreamRequestEntity requestEntity = 
                new InputStreamRequestEntity(xmlInputStream, 
                        "text/xml; charset=UTF-8");
            
            /* Send request. */
            HttpClient client = new HttpClient();
            method.setRequestEntity(requestEntity);
            int statusCode = client.executeMethod(method);
            
            /* Process reply. */  
            handleHTTPStatusCode(statusCode);
            
            InputStream in = method.getResponseBodyAsStream();
            ServiceResponse serviceResponse =
                ServiceResponseXMLConversor.toServiceResponse(in);
            
            if (serviceResponse.getContentType() == 
                    ServiceResponse.ContentType.EXCEPTION) {
                
                ExceptionInServiceResponse exception = 
                    serviceResponse.getException();
                
                if (exception.getCode() == ExceptionCodes.INSTANCE_NOT_FOUND) {
                    
                    throw new InstanceNotFoundException(
                        movieInformation.getIdentifier(), 
                        MovieInformationTO.class.getName());
                    
                } else if (exception.getCode() == 
                        ExceptionCodes.INCORRECT_MOVIE_INFORMATION) {                    
                    throw new MovieInformationException(exception.getMessage());                    
                } else {
                    throw getUnexpectedServiceException(exception);
                }
                
            }
        
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
       
        /* Prepare request.*/
        PostMethod method = 
            new PostMethod(getEndpointAddress() + "/removeMovie");
        method.addParameter("identifier", identifier.toString());
        
        try {
            
            /* send request. */
            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(method);
            
            /* Process reply. */  
            handleHTTPStatusCode(statusCode);
            
            InputStream in = method.getResponseBodyAsStream();            
            ServiceResponse serviceResponse =
                ServiceResponseXMLConversor.toServiceResponse(in);

            if (serviceResponse.getContentType() == 
                    ServiceResponse.ContentType.EXCEPTION) {
                
                ExceptionInServiceResponse exception = 
                    serviceResponse.getException();
                
                if (exception.getCode() == ExceptionCodes.INSTANCE_NOT_FOUND) {
                    
                    throw new InstanceNotFoundException(
                        identifier, MovieInformationTO.class.getName());
                    
                } else {
                    throw getUnexpectedServiceException(exception);                
                }
                
            }
            
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
    
    private void handleHTTPStatusCode(int statusCode) throws ServiceException {
        
        if (statusCode != HttpStatus.SC_OK) {            
            throw new ServiceException("HTTP error; status code = " + 
                    statusCode);
        }
        
    }
    
    private ServiceException getUnexpectedServiceException (
        ExceptionInServiceResponse exception) {
        
        return new ServiceException("Server exception; " +
            "code = " + exception.getCode() + 
            "; message = " + exception.getMessage());
        
    }
    
}
