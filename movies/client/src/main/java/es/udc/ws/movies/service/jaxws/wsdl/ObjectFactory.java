
package es.udc.ws.movies.service.jaxws.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.ws.movies.service.jaxws.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateMovie_QNAME = new QName("http://movies.ws.adoo.udc.es/", "updateMovie");
    private final static QName _MovieException_QNAME = new QName("http://movies.ws.adoo.udc.es/", "MovieException");
    private final static QName _FindMoviesByReleaseDateResponse_QNAME = new QName("http://movies.ws.adoo.udc.es/", "findMoviesByReleaseDateResponse");
    private final static QName _MovieNotFoundException_QNAME = new QName("http://movies.ws.adoo.udc.es/", "MovieNotFoundException");
    private final static QName _AddMovie_QNAME = new QName("http://movies.ws.adoo.udc.es/", "addMovie");
    private final static QName _RemoveMovieResponse_QNAME = new QName("http://movies.ws.adoo.udc.es/", "removeMovieResponse");
    private final static QName _UpdateMovieResponse_QNAME = new QName("http://movies.ws.adoo.udc.es/", "updateMovieResponse");
    private final static QName _AddMovieResponse_QNAME = new QName("http://movies.ws.adoo.udc.es/", "addMovieResponse");
    private final static QName _RemoveMovie_QNAME = new QName("http://movies.ws.adoo.udc.es/", "removeMovie");
    private final static QName _FindMoviesByReleaseDate_QNAME = new QName("http://movies.ws.adoo.udc.es/", "findMoviesByReleaseDate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.movies.service.jaxws.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddMovie }
     * 
     */
    public AddMovie createAddMovie() {
        return new AddMovie();
    }

    /**
     * Create an instance of {@link MovieNotFoundExceptionInfo }
     * 
     */
    public MovieNotFoundExceptionInfo createMovieNotFoundExceptionInfo() {
        return new MovieNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link AddMovieResponse }
     * 
     */
    public AddMovieResponse createAddMovieResponse() {
        return new AddMovieResponse();
    }

    /**
     * Create an instance of {@link RemoveMovie }
     * 
     */
    public RemoveMovie createRemoveMovie() {
        return new RemoveMovie();
    }

    /**
     * Create an instance of {@link MovieInformationWTO }
     * 
     */
    public MovieInformationWTO createMovieInformationWTO() {
        return new MovieInformationWTO();
    }

    /**
     * Create an instance of {@link FindMoviesByReleaseDate }
     * 
     */
    public FindMoviesByReleaseDate createFindMoviesByReleaseDate() {
        return new FindMoviesByReleaseDate();
    }

    /**
     * Create an instance of {@link UpdateMovie }
     * 
     */
    public UpdateMovie createUpdateMovie() {
        return new UpdateMovie();
    }

    /**
     * Create an instance of {@link MovieExceptionInfo }
     * 
     */
    public MovieExceptionInfo createMovieExceptionInfo() {
        return new MovieExceptionInfo();
    }

    /**
     * Create an instance of {@link RemoveMovieResponse }
     * 
     */
    public RemoveMovieResponse createRemoveMovieResponse() {
        return new RemoveMovieResponse();
    }

    /**
     * Create an instance of {@link FindMoviesByReleaseDateResponse }
     * 
     */
    public FindMoviesByReleaseDateResponse createFindMoviesByReleaseDateResponse() {
        return new FindMoviesByReleaseDateResponse();
    }

    /**
     * Create an instance of {@link UpdateMovieResponse }
     * 
     */
    public UpdateMovieResponse createUpdateMovieResponse() {
        return new UpdateMovieResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMovie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "updateMovie")
    public JAXBElement<UpdateMovie> createUpdateMovie(UpdateMovie value) {
        return new JAXBElement<UpdateMovie>(_UpdateMovie_QNAME, UpdateMovie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MovieExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "MovieException")
    public JAXBElement<MovieExceptionInfo> createMovieException(MovieExceptionInfo value) {
        return new JAXBElement<MovieExceptionInfo>(_MovieException_QNAME, MovieExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindMoviesByReleaseDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "findMoviesByReleaseDateResponse")
    public JAXBElement<FindMoviesByReleaseDateResponse> createFindMoviesByReleaseDateResponse(FindMoviesByReleaseDateResponse value) {
        return new JAXBElement<FindMoviesByReleaseDateResponse>(_FindMoviesByReleaseDateResponse_QNAME, FindMoviesByReleaseDateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MovieNotFoundExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "MovieNotFoundException")
    public JAXBElement<MovieNotFoundExceptionInfo> createMovieNotFoundException(MovieNotFoundExceptionInfo value) {
        return new JAXBElement<MovieNotFoundExceptionInfo>(_MovieNotFoundException_QNAME, MovieNotFoundExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMovie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "addMovie")
    public JAXBElement<AddMovie> createAddMovie(AddMovie value) {
        return new JAXBElement<AddMovie>(_AddMovie_QNAME, AddMovie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "removeMovieResponse")
    public JAXBElement<RemoveMovieResponse> createRemoveMovieResponse(RemoveMovieResponse value) {
        return new JAXBElement<RemoveMovieResponse>(_RemoveMovieResponse_QNAME, RemoveMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "updateMovieResponse")
    public JAXBElement<UpdateMovieResponse> createUpdateMovieResponse(UpdateMovieResponse value) {
        return new JAXBElement<UpdateMovieResponse>(_UpdateMovieResponse_QNAME, UpdateMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "addMovieResponse")
    public JAXBElement<AddMovieResponse> createAddMovieResponse(AddMovieResponse value) {
        return new JAXBElement<AddMovieResponse>(_AddMovieResponse_QNAME, AddMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveMovie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "removeMovie")
    public JAXBElement<RemoveMovie> createRemoveMovie(RemoveMovie value) {
        return new JAXBElement<RemoveMovie>(_RemoveMovie_QNAME, RemoveMovie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindMoviesByReleaseDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://movies.ws.adoo.udc.es/", name = "findMoviesByReleaseDate")
    public JAXBElement<FindMoviesByReleaseDate> createFindMoviesByReleaseDate(FindMoviesByReleaseDate value) {
        return new JAXBElement<FindMoviesByReleaseDate>(_FindMoviesByReleaseDate_QNAME, FindMoviesByReleaseDate.class, null, value);
    }

}
