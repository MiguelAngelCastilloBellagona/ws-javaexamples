package es.udc.ws.movies.service.jaxws.proxy.wsdlutils;

import es.udc.ws.movies.model.GenreOperations;
import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.jaxws.wsdl.MovieException;
import es.udc.ws.movies.service.jaxws.wsdl.MovieInformationWTO;
import es.udc.ws.movies.service.jaxws.wsdl.MovieNotFoundException;
import es.udc.ws.movies.service.jaxws.wsdl.MovieNotFoundExceptionInfo;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * @author <a href="mailto:jlosada@denodo.com">Jose Losada Perez</a>
 * @version $Revision$ $Date$
 */
public class CommonWSDLTypeConversor {

    private CommonWSDLTypeConversor() {}

    public final static List<MovieInformationWTO> toWSDL(List<MovieInformationTO> info)
            throws DatatypeConfigurationException {

        List<MovieInformationWTO> newInfo = new ArrayList<MovieInformationWTO>();

        for (int i=0; i<info.size(); i++) {
            newInfo.add(toWSDL(info.get(i)));
        }

        return newInfo;

    }

    public final static MovieInformationWTO toWSDL(MovieInformationTO info)
            throws DatatypeConfigurationException {

        MovieInformationWTO newInfo = new MovieInformationWTO();
        newInfo.setIdentifier(info.getIdentifier());
        newInfo.setTitle(info.getTitle());
        newInfo.setRuntime(info.getRuntime());
        newInfo.setReleaseDate(DatatypeFactory.newInstance()
                .newXMLGregorianCalendar((GregorianCalendar) info.getReleaseDate()));
        newInfo.getDirectorNames().addAll(info.getDirectorNames());
        newInfo.getActorNames().addAll(info.getActorNames());
        newInfo.getGenres().addAll(GenreOperations.toListOfString(info.getGenres()));
        newInfo.setSynopsis(info.getSynopsis());

        return newInfo;

    }

    public final static List<MovieInformationTO> fromWSDL(List<MovieInformationWTO> info) {
        List<MovieInformationTO> newInfo = new ArrayList<MovieInformationTO>();
        for (int i=0; i<info.size(); i++) {
            newInfo.add(fromWSDL(info.get(i)));
        }
        return newInfo;

    }

    public final static MovieInformationTO fromWSDL(MovieInformationWTO info) {
        Long identifier = info.getIdentifier();
        String title = info.getTitle();
        short runtime = info.getRuntime();
        Calendar releaseDate = info.getReleaseDate().toGregorianCalendar();
        List<String> directorNames = info.getDirectorNames();
        List<String> actorNames = info.getActorNames();
        List<String> genres = info.getGenres();
        String synopsis = info.getSynopsis();
        return new MovieInformationTO(identifier, title, runtime, releaseDate,
            directorNames, actorNames, GenreOperations.toListOfGenres(genres), synopsis);
    }

    public final static MovieNotFoundException toMovieNotFoundException(InstanceNotFoundException e) {
        MovieNotFoundExceptionInfo info = new MovieNotFoundExceptionInfo();
        info.setIdentifier((Long) e.getKey());
        return new MovieNotFoundException(e.getMessage(), info);
    }

    public final static InstanceNotFoundException toInstanceNotFoundException(MovieNotFoundException e) {
        return new InstanceNotFoundException(e.getMessage(),
            MovieInformationTO.class.getName());
    }

    public final static MovieException toMovieException(MovieInformationException e) {
        return new MovieException(e.getMessage(), null);
    }

    public final static MovieInformationException toMovieInformationException(MovieException e) {
        return new MovieInformationException(e.getMessage());

    }


}
