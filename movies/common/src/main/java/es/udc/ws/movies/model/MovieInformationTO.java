package es.udc.ws.movies.model;

import java.util.Calendar;
import java.util.List;
import java.text.DateFormat;

/**
 * Represents information about a movie.
 * <p>
 * <b>WARNING</b>: In <code>releaseDate</code>, the only meaningful fields are:
 * "day", "year" and "month".
 */
public class MovieInformationTO {
    
    public enum Genre {COM, DRA, HOR, ROM, SFI, THR};

    private Long identifier;
    private String title;
    private short runtime;
    private Calendar releaseDate;
    private List<String> directorNames;
    private List<String> actorNames;
    private List<Genre> genres;
    private String synopsis;

    /**
     * @param identifier it can be <code>null</code>
     */
    public MovieInformationTO(Long identifier, String title, short runtime,
        Calendar releaseDate, List<String> directorNames, List<String> actorNames,
        List<Genre> genres, String synopsis) {
        
        this.identifier = identifier;
        this.title = title;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.directorNames = directorNames;
        this.actorNames = actorNames;
        this.genres = genres;
        this.synopsis = synopsis;
    
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }    
    
    public Long getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getRuntime() {
        return runtime;
    }

    public void setRuntime(short runtime) {
        this.runtime = runtime;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getDirectorNames() {
        return directorNames;
    }

    public void setDirectorNames(List<String> directorNames) {
        this.directorNames = directorNames;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    public void setActorNames(List<String> actorNames) {
        this.actorNames = actorNames;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
    @Override
    public boolean equals(Object object) {
        
        if ( (object == null) || !(object instanceof MovieInformationTO) ) {
            return false;
        }
        
        MovieInformationTO theOther = (MovieInformationTO) object;        
        boolean partialComparison = 
            title.equals(theOther.getTitle()) &&
           (runtime == theOther.getRuntime()) &&
           DateOperations.datesAreEqual(releaseDate, theOther.getReleaseDate()) &&
           directorNames.equals(theOther.getDirectorNames()) &&
           actorNames.equals(theOther.getActorNames()) &&
           genres.equals(theOther.getGenres()) &&
           synopsis.equals(theOther.getSynopsis());
        
        if (identifier == null) {
            return (theOther.getIdentifier() == null) && partialComparison;
        } else {
            return identifier.equals(theOther.getIdentifier()) && partialComparison;
        }

    }

    @Override
    public String toString() {
    
        DateFormat dateFormater = DateFormat.getDateInstance();
        String releaseDateString = dateFormater.format(releaseDate.getTime());
    
        return "movieIdentifier = " + identifier + " | " +
               "title = " + title + " | " +
               "runtime = " + runtime + " | " +
               "releaseDate = " + releaseDateString + " | " +
               "directorNames = " + directorNames + " | " +
               "actorNames = " + actorNames + " | " +
               "genres = " + genres + " | " +
               "synopsis = " + synopsis;
    }
    
}
