
package es.udc.ws.movies.service.jaxws;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovieInformationWTO {

    @XmlElement(nillable = true)
    protected List<String> actorNames;
    @XmlElement(nillable = true)
    protected List<String> directorNames;
    @XmlElement(nillable = true)
    protected List<String> genres;
    protected Long identifier;
    protected Calendar releaseDate;
    protected short runtime;
    protected String synopsis;
    protected String title;

    public List<String> getActorNames() {
        if (actorNames == null) {
            actorNames = new ArrayList<String>();
        }
        return this.actorNames;
    }

    public List<String> getDirectorNames() {
        if (directorNames == null) {
            directorNames = new ArrayList<String>();
        }
        return this.directorNames;
    }

    public List<String> getGenres() {
        if (genres == null) {
            genres = new ArrayList<String>();
        }
        return this.genres;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long value) {
        this.identifier = value;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar value) {
        this.releaseDate = value;
    }

    public short getRuntime() {
        return runtime;
    }

    public void setRuntime(short value) {
        this.runtime = value;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String value) {
        this.synopsis = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

}
