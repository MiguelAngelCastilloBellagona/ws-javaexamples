package es.udc.ws.movies.service.restful;

import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.xml.MovieXMLConversor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

@Consumes("application/xml")
@Provider
public class MovieInformationTOReader implements
		MessageBodyReader<MovieInformationTO> {

	@Override
	public boolean isReadable(Class<?> type, Type type1, Annotation[] antns,
			MediaType mt) {
		return type == MovieInformationTO.class;
	}

	@Override
	public MovieInformationTO readFrom(Class<MovieInformationTO> type,
			Type type1, Annotation[] antns, MediaType mt,
			MultivaluedMap<String, String> mm, InputStream in)
			throws IOException, WebApplicationException {
		return MovieXMLConversor.toMovieInformation(in);
	}

}
