package es.udc.ws.movies.service.jaxws.wsdlutils;

import es.udc.ws.movies.model.GenreOperations;
import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.jaxws.MovieException;
import es.udc.ws.movies.service.jaxws.MovieInformationWTO;
import es.udc.ws.movies.service.jaxws.MovieNotFoundException;
import es.udc.ws.movies.service.jaxws.MovieNotFoundExceptionInfo;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.ArrayList;
import java.util.List;

public final class CommonWSDLTypeConversor {

    private CommonWSDLTypeConversor() {}

    public static List<MovieInformationWTO> toWSDL(
            List<MovieInformationTO> info) {

        List<MovieInformationWTO> newInfo = 
                new ArrayList<MovieInformationWTO>();

        for (int i=0; i<info.size(); i++) {
            newInfo.add(toWSDL(info.get(i)));
        }

        return newInfo;

    }

    public static MovieInformationWTO toWSDL(MovieInformationTO info) {

        MovieInformationWTO newInfo = new MovieInformationWTO();
        newInfo.setIdentifier(info.getIdentifier());
        newInfo.setTitle(info.getTitle());
        newInfo.setRuntime(info.getRuntime());
        newInfo.setReleaseDate(info.getReleaseDate());
        newInfo.getDirectorNames().addAll(info.getDirectorNames());
        newInfo.getActorNames().addAll(info.getActorNames());
        newInfo.getGenres().addAll(GenreOperations
                .toListOfString(info.getGenres()));
        newInfo.setSynopsis(info.getSynopsis());

        return newInfo;

    }

    public static List<MovieInformationTO> 
            fromWSDL(List<MovieInformationWTO> info) {
        List<MovieInformationTO> newInfo = new ArrayList<MovieInformationTO>();
        for (int i=0; i<info.size(); i++) {
            newInfo.add(fromWSDL(info.get(i)));
        }
        return newInfo;

    }

    public static MovieInformationTO fromWSDL(MovieInformationWTO info) {
        Long identifier = info.getIdentifier();
        String title = info.getTitle();
        short runtime = info.getRuntime();
        List<String> directorNames = info.getDirectorNames();
        List<String> actorNames = info.getActorNames();
        List<String> genres = info.getGenres();
        String synopsis = info.getSynopsis();
        return new MovieInformationTO(identifier, title, runtime, 
                info.getReleaseDate(),
            directorNames, actorNames, 
                GenreOperations.toListOfGenres(genres), synopsis);
    }

    public static MovieNotFoundException toMovieNotFoundException(
            InstanceNotFoundException e) {
        MovieNotFoundExceptionInfo info = new MovieNotFoundExceptionInfo();
        info.setIdentifier((Long) e.getKey());
        return new MovieNotFoundException(info);
    }

    public static InstanceNotFoundException 
            toInstanceNotFoundException(MovieNotFoundException e) {
        return new InstanceNotFoundException(e.getMessage(),
            MovieInformationTO.class.getName());
    }

    public static MovieException toMovieException(
            MovieInformationException e) {
        return new MovieException(null);
    }

    public static MovieInformationException toMovieInformationException(
            MovieException e) {
        return new MovieInformationException(e.getMessage());

    }

}
