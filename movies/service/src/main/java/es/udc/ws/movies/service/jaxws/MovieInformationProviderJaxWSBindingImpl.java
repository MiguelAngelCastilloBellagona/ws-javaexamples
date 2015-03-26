package es.udc.ws.movies.service.jaxws;

import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationFacade;
import es.udc.ws.movies.service.jaxws.wsdlutils.CommonWSDLTypeConversor;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        name = "MovieInformationProvider",
        serviceName = "MovieInformationProviderService",
        targetNamespace = "http://movies.ws.adoo.udc.es/"
)
public class MovieInformationProviderJaxWSBindingImpl {

    private static final Logger logger = Logger.getLogger(
            MovieInformationProviderJaxWSBindingImpl.class.getName());

    @WebMethod(
            operationName = "findMoviesByReleaseDate"
    )
    public List<MovieInformationWTO> findMoviesByReleaseDate(
            Calendar releaseDate) {
        logger.info(MessageFormat.format("Searching movie(s) with date \"{0}\"", 
                releaseDate));
        return CommonWSDLTypeConversor.toWSDL(
                MovieInformationFacade.getInstance()
                .findMoviesByReleaseDate(releaseDate));
    }

    @WebMethod(
            operationName = "addMovie"
    )
    public long addMovie(MovieInformationWTO movieInformation)
            throws MovieException {

        try {
            return MovieInformationFacade.getInstance().addMovie(
                    CommonWSDLTypeConversor.fromWSDL(movieInformation));
        } catch (MovieInformationException e) {
            throw CommonWSDLTypeConversor.toMovieException(e);
        }

    }

    @WebMethod(
            operationName = "updateMovie"
    )
    public void updateMovie(MovieInformationWTO movieInformation)
            throws MovieNotFoundException, MovieException {

        try {
            MovieInformationFacade.getInstance().updateMovie(
                    CommonWSDLTypeConversor.fromWSDL(movieInformation));
        } catch (InstanceNotFoundException e) {
            throw CommonWSDLTypeConversor.toMovieNotFoundException(e);
        } catch (MovieInformationException e) {
            throw CommonWSDLTypeConversor.toMovieException(e);
        }

    }

    @WebMethod(
            operationName = "removeMovie"
    )
    public void removeMovie(Long identifier) throws MovieNotFoundException {

        try {
            MovieInformationFacade.getInstance().removeMovie(identifier);
        } catch (InstanceNotFoundException e) {
            throw CommonWSDLTypeConversor.toMovieNotFoundException(e);
        }

    }

}
