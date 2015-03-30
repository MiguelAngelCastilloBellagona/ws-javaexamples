package es.udc.ws.movies.service.restful;

import com.sun.jersey.api.Responses;
import es.udc.ws.movies.model.DateOperations;
import es.udc.ws.movies.model.MovieInformationException;
import es.udc.ws.movies.model.MovieInformationFacade;
import es.udc.ws.movies.model.MovieInformationTO;
import es.udc.ws.movies.service.rest.xml.ExceptionCodes;
import es.udc.ws.movies.service.rest.xml.ExceptionInServiceResponse;
import es.udc.ws.movies.service.rest.xml.ServiceResponse;
import es.udc.ws.movies.xml.MovieXMLConversor;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.jdom.Element;

@Path("/movies")
public class MovieResource extends Application {

	@GET
	@Produces("application/xml")
	public ServiceResponse findMovies(@QueryParam("year") String year,
			@QueryParam("month") String month, @QueryParam("day") String day) {
		try {
			Calendar releaseDate = DateOperations.getDate(day, month, year);
			List<MovieInformationTO> movieInformationList = MovieInformationFacade
					.getInstance().findMoviesByReleaseDate(releaseDate);
			return new ServiceResponse(
					MovieXMLConversor.toXML(movieInformationList));
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse(
					new ExceptionInServiceResponse(
							ExceptionCodes.INCORRECT_PARAMETERS, e.getMessage()));
			throw new WebApplicationException(Response
					.status(Responses.CLIENT_ERROR).entity(response)
					.type("application/xml").build());

		}
	}

	@GET
	@Path("/{id}")
	@Produces("application/xml")
	public ServiceResponse findById(@PathParam("id") String id) {
		try {
			MovieInformationTO instance = MovieInformationFacade.getInstance()
					.findMovie(Long.valueOf(id));
			return new ServiceResponse(MovieXMLConversor.toXML(instance));
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse(
					new ExceptionInServiceResponse(
							ExceptionCodes.INSTANCE_NOT_FOUND, e.getMessage()));
			throw new WebApplicationException(Response
					.status(Responses.NOT_FOUND).entity(response)
					.type("application/xml").build());
		}
	}

	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response addMovie(MovieInformationTO movieInformation,
			@Context UriInfo ui) {
		Long identifier;
		try {
			identifier = MovieInformationFacade.getInstance().addMovie(
					movieInformation);
		} catch (MovieInformationException e) {
			ServiceResponse response = new ServiceResponse(
					new ExceptionInServiceResponse(
							ExceptionCodes.INCORRECT_MOVIE_INFORMATION,
							e.getMessage()));
			throw new WebApplicationException(Response
					.status(Responses.CLIENT_ERROR).entity(response)
					.type("application/xml").build());
		}
		Element identifierElement = new Element("identifier",
				MovieXMLConversor.XML_NS);
		identifierElement.setText(identifier.toString());
		ServiceResponse response = new ServiceResponse(identifierElement);
		return Response
				.created(
						URI.create(ui.getRequestUri().toString() + "/"
								+ identifier)).entity(response)
				.type("application/xml").build();
	}

	@PUT
	@Consumes("application/xml")
	@Path("/{id}")
	public void updateMovie(@PathParam("id") String id,
			MovieInformationTO movieInformation) {
		try {
			movieInformation.setIdentifier(Long.valueOf(id));
			MovieInformationFacade.getInstance().updateMovie(movieInformation);
		} catch (InstanceNotFoundException e) {
			ServiceResponse response = new ServiceResponse(
					new ExceptionInServiceResponse(
							ExceptionCodes.INSTANCE_NOT_FOUND, e.getMessage()));
			throw new WebApplicationException(Response
					.status(Responses.NOT_FOUND).entity(response)
					.type("application/xml").build());
		} catch (MovieInformationException e) {
			ServiceResponse response = new ServiceResponse(
					new ExceptionInServiceResponse(
							ExceptionCodes.INCORRECT_MOVIE_INFORMATION,
							e.getMessage()));
			throw new WebApplicationException(Response
					.status(Responses.CLIENT_ERROR).entity(response)
					.type("application/xml").build());
		}
	}

	@DELETE
	@Consumes("application/xml")
	@Path("/{id}")
	public void deleteMovie(@PathParam("id") String id) {

		Long identifier;
		try {
			identifier = Long.valueOf(id);
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse(
					new ExceptionInServiceResponse(
							ExceptionCodes.INCORRECT_PARAMETERS, e.getMessage()));
			throw new WebApplicationException(Response
					.status(Responses.CLIENT_ERROR).entity(response)
					.type("application/xml").build());
		}

		try {
			MovieInformationFacade.getInstance().removeMovie(identifier);
		} catch (InstanceNotFoundException e) {
			ServiceResponse response = new ServiceResponse(
					new ExceptionInServiceResponse(
							ExceptionCodes.INSTANCE_NOT_FOUND, e.getMessage()));
			throw new WebApplicationException(Response
					.status(Responses.NOT_FOUND).entity(response)
					.type("application/xml").build());
		}
	}

}