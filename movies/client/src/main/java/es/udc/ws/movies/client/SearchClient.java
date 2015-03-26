package es.udc.ws.movies.client;

import es.udc.ws.movies.model.DateOperations;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.MovieInformationService;
import es.udc.ws.movies.service.MovieInformationServiceFactory;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

class SearchClient {

    public static void main (String args[]) {

        try {
        
            /* Get release Date. */
            Calendar releaseDate;
            
            try {
                releaseDate = DateOperations.getDate(args[0], args[1], args[2]);
            } catch (Exception e) {                
                System.out.println("Usage: " + SearchClient.class.getName() + 
                    " day month year");
                return;
            }
            
            /* Request movie information. */
            MovieInformationService movieInformationService = 
                MovieInformationServiceFactory.getMovieInformationService();
            List<MovieInformationTO> movieInformationList =
                movieInformationService.findMoviesByReleaseDate(releaseDate);
                
            /* Print movie information. */
            DateFormat dateFormater = DateFormat.getDateInstance();
            String releaseDateString = dateFormater.format(releaseDate.getTime());
            
            System.out.println("------------------ Movies " +  
                releaseDateString + " ------------------");
            
            for (MovieInformationTO m : movieInformationList) {                                
                
                System.out.println("Identifier = " + m.getIdentifier());
                System.out.println("Title = " + m.getTitle());
                System.out.println("Runtime = " + m.getRuntime());
                System.out.println("Director(-s) = " + m.getDirectorNames());
                System.out.println("Actors = " + m.getActorNames());
                System.out.println("Genres = " + m.getGenres());
                System.out.println("Sypnosys = " + m.getSynopsis());
                
                System.out.println("----------------------------------");
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
