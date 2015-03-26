package es.udc.ws.movies.model;

import java.util.ArrayList;
import java.util.List;

public class GenreOperations {
    
    private GenreOperations() {}
    
    public final static List<MovieInformationTO.Genre> toListOfGenres(
        List<String> stringValues) {
        
        List<MovieInformationTO.Genre> genres = 
            new ArrayList<MovieInformationTO.Genre>();
        
        for (String v : stringValues) {
            genres.add(MovieInformationTO.Genre.valueOf(v));
        }
        
        return genres;
        
    }
    
    public final static List<String> toListOfString(
        List<MovieInformationTO.Genre> genres) {
        
        List<String> stringValues = new ArrayList<String>();
        
        for (MovieInformationTO.Genre g : genres) {
            stringValues.add(g.toString());
        }
        
        return stringValues;
        
    }

}
