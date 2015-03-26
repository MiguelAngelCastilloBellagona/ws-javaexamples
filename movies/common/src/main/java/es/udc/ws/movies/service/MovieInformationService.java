package es.udc.ws.movies.service;

import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.exceptions.ServiceException;

import java.util.Calendar;
import java.util.List;

public interface MovieInformationService {

    /**
     * NOTE: by consistency with <code>MovieInformationTO</code>, only the fields 
     * "day", "year", and "month" are taken into account.
     */    
    public List<MovieInformationTO> findMoviesByReleaseDate(
            Calendar releaseDate)
        throws ServiceException;
    
    public Long addMovie(MovieInformationTO movieInformation) 
        throws MovieInformationException, ServiceException;
 
    public void updateMovie(MovieInformationTO movieInformation)
        throws InstanceNotFoundException, MovieInformationException, 
               ServiceException;

    public void removeMovie(Long identifier)
        throws InstanceNotFoundException, ServiceException;
        
}
