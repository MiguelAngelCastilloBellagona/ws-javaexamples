package es.udc.ws.movies.client;

import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.MovieInformationService;
import es.udc.ws.movies.service.MovieInformationServiceFactory;
import es.udc.ws.movies.xml.MovieXMLConversor;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.exceptions.ParsingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AdminClient {
    
    private enum Action {ADD, UPDATE, REMOVE, ERROR};
    
    public static void main(String[] args) {
        
        try {            
            
            /* Parse parameters. */
            Action action = parseArguments(args); 
            
            if (action == Action.ERROR) {
                System.out.println("Usage: " + AdminClient.class.getName() + 
                    " (-a | -u | -r) arg1...");
            }
            
            /* Do work.*/
            MovieInformationService movieInformationService = 
                MovieInformationServiceFactory.getMovieInformationService();
            
            switch (action) {
                case ADD: 
                    storeMovies(movieInformationService, args, action); 
                    break;
                case UPDATE: 
                    storeMovies(movieInformationService, args, action); 
                    break;                
                case REMOVE: 
                    removeMovies(movieInformationService, args); 
                    break;
			default:
				break;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private static Action parseArguments(String[] args) {
        
        if (args.length < 2) {
            return Action.ERROR;
        }
        
        if (args[0].equals("-a")) {
            return Action.ADD;
        } else if (args[0].equals("-u")) {
            return Action.UPDATE;
        } else if (args[0].equals("-r")) {
            return Action.REMOVE;
        } else {
            return Action.ERROR;
        }
        
    }
    
    private static void storeMovies (MovieInformationService movieInformationService,
        String[] args, Action action) {
        
        /* Process files. */
        for (int i=1; i<args.length; i++) {
            
            FileInputStream in = null;
        
            try {
                
                System.out.print("File: " + args[i] + " -> ");
                
                /* Parse file. */
                in = new FileInputStream(args[i]);
                MovieInformationTO movieInformation = MovieXMLConversor.toMovieInformation(in);
                
                if (action == Action.ADD) {
                    
                    /* Add movie information. */
                    Long identifier = movieInformationService.addMovie(movieInformation);
                    System.out.println("Movie successfully added (identifier: " + identifier + ')');
                    
                } else {
                    
                    /* Update movie information. */
                    movieInformationService.updateMovie(movieInformation);
                    System.out.println("Movie sucessfully updated");
                    
                }
                
            } catch (FileNotFoundException e) {                
                System.out.println("File not found");                
            } catch (ParsingException e) {                
                System.out.println("Parsing error: " + e.getMessage());                
            } catch (InstanceNotFoundException e) {                
                System.out.println("Movie does not exist");                                
            } catch (MovieInformationException e) {                
                System.out.println("Incorrect movie information: " + e.getMessage());
            } catch (Exception e) {
                
                System.out.println("Internal error");
                e.printStackTrace();
                
            } finally {
                
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
            }
        
        }
        
    }
    
    private static void removeMovies (MovieInformationService movieInformationService,
        String[] args) {
        
        /* Process identifiers. */
        for (int i=1; i<args.length; i++) {
                   
            try {
                
                System.out.print("Identifier: " + args[i] + " -> ");
                
                /* Remove movie. */
                Long identifier = Long.valueOf(args[i]);
                movieInformationService.removeMovie(identifier);
                System.out.println("Movie sucessfully removed");
                
            } catch (NumberFormatException e) {
                System.out.println("Incorrect format identifier");
            } catch (InstanceNotFoundException e) {                
                System.out.println("Movie does not exist");                
            } catch (Exception e) {
                
                System.out.println("Internal error");
                e.printStackTrace();
                
            }
        
        }
        
    }

}
